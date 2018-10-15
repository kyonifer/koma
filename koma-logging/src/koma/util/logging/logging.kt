/**
 * Logging helper functions that work correctly when koma-based code is called from Python or MATLAB.
 *
 * One goal of koma is to be able to write scientific code that is consumable from common scientific
 * environments. Unfortunately, MATLAB uses slf4j internally which causes issues with a straight usage
 * of the slf4j-api. In particular, the slf4j classloader will pick up the StaticLoggerBinder that MATLAB
 * exports, making the slf4j static methods inoperable. We get around this by detecting if we are in a
 * MATLAB context and building a logback context manually. This requires us to statically link against
 * logback (or use a ridiculous amount of reflection, which was not done).
 *
 * In this namespace we provide a set of logging functions which operate normally
 * (i.e. use slf4j-api and allow for a pluggable backend logger) when run on the JVM normally but force
 * using logback manually when they detect we are running embedded inside MATLAB. You may therefore
 * replace your calls to slf4j with the functions found here and see no difference in ordinary usage, but
 * be robust to MATLAB operation in the future.
 *
 * In addition to MATLAB support, this namespace contains extension functions to conveniently log messages
 * from any object. If you are in a method definition and wish to create a log message, you may simply
 * write this.log(Level.DEBUG) {"My Message"}, where the closure is code that builds a string and will only
 * be invoked if the chosen level is currently enabled. There is also a this.setLogLevel(level) method which
 * chooses the output level for a given class, however this will only work with logback chosen (as slf4j
 * doesnt contain this functionality in its API).
 */
@file:JvmName("Logging")

package koma.util.logging

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.util.ContextInitializer
import ch.qos.logback.classic.util.ContextSelectorStaticBinder
import ch.qos.logback.core.CoreConstants
import ch.qos.logback.core.joran.spi.JoranException
import ch.qos.logback.core.status.StatusUtil
import ch.qos.logback.core.util.StatusPrinter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import org.slf4j.helpers.Util

/**
 * A MATLAB-friendly wrapper around slf4j using kotlin extension functions to automatically get a reference to a logger
 * and closures for lazy evaluation of the builder if logging is disabled (not currently supported directly in slf4j).
 * Outputs at the chosen [level] the string returned by [builder]
 */
fun Any.log(level: Level = Level.DEBUG, builder: () -> String) {
    val logger = getLogger(this)
    when (level) {
        Level.DEBUG -> {
            if (logger.isDebugEnabled)
                logger.debug(builder())
        }
        Level.ERROR -> {
            if (logger.isErrorEnabled)
                logger.error(builder())
        }
        Level.TRACE -> {
            if (logger.isTraceEnabled)
                logger.trace(builder())
        }
        Level.INFO  -> {
            if (logger.isInfoEnabled)
                logger.info(builder())
        }
        Level.WARN  -> {
            if (logger.isWarnEnabled)
                logger.warn(builder())
        }
    }
}

/**
 * Support ordinary object printing. Note potential performance loss when logging is off vs a closure
 */
fun Any.log(level: Level = Level.DEBUG, message: Any) = this.log(level, { message.toString() })

/**
 * Dumps a set of variables out as a YAML formatted debug message.
 */
fun asYaml(methodName: String,
           vararg nameValuePairs: Pair<*, *>): String {
    return nameValuePairs
            .map { pair ->
                "${pair.first.toString().prependIndent("    ")}: |\n" +
                pair.second.toString().replaceIndent("        ")
            }
            .joinToString(separator = "\n",
                          prefix = "$methodName: %\n")
}


/**
 * Whether we are in a MATLAB context or not (i.e. MATLAB classes are on the classpath)
 */
val isMatlab: Boolean = fun(): Boolean {
    try {
        Class.forName("com.mathworks.jmi.Matlab")
    } catch (e: ClassNotFoundException) {
        return false
    } catch (e: NoClassDefFoundError) {
        return false
    }
    return true
}()

/**
 * MATLAB friendly replacement for LoggerFactory.getLogger. Calls logback manually if we are being
 * called from within a MATLAB context, as if we didn't slf4j will break and find MATLABs StaticLoggerBinder
 * instance. If we aren't in a MATLAB context, call LoggerFactory.getLogger as usual.
 */
fun getLogger(instance: Any): Logger {
    if (!isMatlab)
        return LoggerFactory.getLogger(instance.javaClass)
    return ManualLogbackGenerator.getLoggerFactory().getLogger(instance.javaClass)
}

/**
 * MATLAB friendly replacement for LoggerFactory.getLogger. Calls logback manually if we are being
 * called from within a MATLAB context, as if we didn't slf4j will break and find MATLABs StaticLoggerBinder
 * instance. If we aren't in a MATLAB context, call LoggerFactory.getLogger as usual.
 */
fun getLogger(name: String): Logger {
    if (!isMatlab)
        return LoggerFactory.getLogger(name)
    return ManualLogbackGenerator.getLoggerFactory().getLogger(name)
}

/**
 * Set the log level for the logger associated with the fully qualified classname contained in [name].
 */
fun setLogLevel(name: String, level: Level) {
    catchWrongLoggerBackend {
        val logger = getLogger(name) as ch.qos.logback.classic.Logger
        logger.level = mapLevels(level)
    }
}

/**
 * Set the log level for the logger associated with the [instance] class.
 */
fun setLogLevel(instance: Any, level: Level) {
    catchWrongLoggerBackend {
        val logger = getLogger(instance) as ch.qos.logback.classic.Logger
        logger.level = mapLevels(level)
    }
}

/**
 * Set the log level for the logger associated with the [instance] class.
 */
fun setLogLevel(instance: Any, level: String) {
    catchWrongLoggerBackend {
        val logger = getLogger(instance) as ch.qos.logback.classic.Logger
        logger.level = mapLevels(level)
    }
}

/**
 * Set the log level for the logger associated with the fully qualified classname contained in [name].
 */
fun setLogLevel(name: String, level: String) {
    catchWrongLoggerBackend {
        val logger = getLogger(name) as ch.qos.logback.classic.Logger
        logger.level = mapLevels(level)
    }
}

/**
 * Manually generates a logback context, bypassing the StaticLoggerBinder since MATLAB hijacks this.
 */
private object ManualLogbackGenerator {
    var initialized = false
    var context = ch.qos.logback.classic.LoggerContext()
    val KEY = Object()
    val contextSelectorBinder = ContextSelectorStaticBinder.getSingleton()

    init {
        context.name = CoreConstants.DEFAULT_CONTEXT_NAME
        try {
            try {
                ContextInitializer(context).autoConfig()
            } catch(je: JoranException) {
                Util.report("Failed to auto configure default logger context", je)
            }
            context.getLogger(this.javaClass)
            if (!StatusUtil.contextHasStatusListener(context)) {
                StatusPrinter.printInCaseOfErrorsOrWarnings(context)
            }
            contextSelectorBinder.init(context, KEY)
            initialized = true
        } catch (t: Throwable) {
            Util.report("Failed to instantiate [${LoggerContext::class.java}]", t)
        }
    }

    @JvmStatic
    fun getLoggerFactory(): LoggerContext {

        if (!initialized) {
            return context
        }
        if (contextSelectorBinder.contextSelector == null) {
            throw IllegalStateException("contextSelector cannot be null.")
        }
        return contextSelectorBinder.contextSelector.loggerContext
    }
}

/**
 * Map slf4j levels to logback levels. Since slf4j doesnt have OFF, use the String
 * overload of this function to disable a level.
 */
private fun mapLevels(slfLevel: Level): ch.qos.logback.classic.Level {
    return when (slfLevel) {
        Level.ERROR -> ch.qos.logback.classic.Level.ERROR
        Level.DEBUG -> ch.qos.logback.classic.Level.DEBUG
        Level.INFO  -> ch.qos.logback.classic.Level.INFO
        Level.TRACE -> ch.qos.logback.classic.Level.TRACE
        Level.WARN  -> ch.qos.logback.classic.Level.WARN
    }
}

/**
 * Map strings to logback levels.
 */
private fun mapLevels(stringLevel: String): ch.qos.logback.classic.Level? {
    val levelUpper = stringLevel.toUpperCase()
    return when (levelUpper) {
        "ERROR" -> ch.qos.logback.classic.Level.ERROR
        "DEBUG" -> ch.qos.logback.classic.Level.DEBUG
        "INFO"  -> ch.qos.logback.classic.Level.INFO
        "TRACE" -> ch.qos.logback.classic.Level.TRACE
        "WARN"  -> ch.qos.logback.classic.Level.WARN
        "OFF"   -> ch.qos.logback.classic.Level.OFF
        else    -> {
            getLogger("mapLevels").error("Unknown log level $stringLevel")
            return null
        }
    }
}

private fun catchWrongLoggerBackend(f: () -> Unit) {
    val MSG = "Cannot set log level! setLogLevel only works when logback is being used."
    val METHNAME = "setLogLevel"
    try {
        f()
    } catch(e: ClassNotFoundException) {
        getLogger(METHNAME).log(Level.ERROR) { MSG }
    } catch(e: NoClassDefFoundError) {
        getLogger(METHNAME).log(Level.ERROR) { MSG }
    }
}