package golem.util.logging

import org.slf4j.Logger

fun Logger.debugAsYaml(methodName: String,
                       vararg nameValuePairs: Pair<*, *>) {

    if (this.isDebugEnabled())
        this.debug(nameValuePairs
                           .map { pair ->
                               "${pair.first.toString().prependIndent("    ")}: |\n" +
                               "${pair.second.toString().replaceIndent("        ")}"
                           }
                           .joinToString(separator = "\n",
                                         prefix = "$methodName: %\n"))
}
