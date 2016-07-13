import com.beust.kobalt.*
import com.beust.kobalt.api.Project
import com.beust.kobalt.plugin.packaging.*

val kotVersion = "1.0.3"
val golemVersion = "0.5"

// Profile configuration variables (set on command line)
val testBackends = false

val core = project(*getCoreDeps()) {
    if (testBackends) {println("hi")}
        else {println("bye")}
    name = "golem-core"
    group = "golem"
    artifactId = name
    version = golemVersion
    directory = name

    sourceDirectories {
        path("src")
    }

    sourceDirectoriesTest {
        path("test")
    }

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib:$kotVersion")
        compile("com.beust:jcommander:1.48")
        compile("org.slf4j:slf4j-api:1.7.21")
        compile("org.knowm.xchart:xchart:2.6.0")
    }

    dependenciesTest {
        compile("junit:junit:4.12")
        compile("org.jetbrains.kotlin:kotlin-test:$kotVersion")

    }

    assemble {
        jar {
        }
    }
}

val logging = project {
    name = "golem-logging"
    group = "golem"
    artifactId = name
    version = golemVersion
    directory = name

    sourceDirectories {
        path("src")
    }

    sourceDirectoriesTest {
    }

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib:$kotVersion")
        compile("org.slf4j:slf4j-api:1.7.21")
        compile("ch.qos.logback:logback-classic:1.1.7")
    }

    dependenciesTest {
        compile("junit:junit:4.12")
        compile("org.jetbrains.kotlin:kotlin-test:$kotVersion")
    }

    assemble {
        jar {
        }
    }
}

val backend_mtj = project(core) {
    name = "golem-backend-mtj"
    group = "golem"
    artifactId = name
    version = golemVersion
    directory = name

    sourceDirectories {
        path("src")
    }
    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib:$kotVersion")
        compile("com.googlecode.matrix-toolkits-java:mtj:1.0.4")
        // Fix issue with pom-only artifacts in MTJ
        exclude("com.github.fommil.netlib:all:pom:1.1.2")
    }
}

val backend_ejml = project(core) {
    name = "golem-backend-ejml"
    group = "golem"
    artifactId = name
    version = golemVersion
    directory = name

    sourceDirectories {
        path("src")
    }
    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib:$kotVersion")
        compile("org.ejml:all:0.27")
    }
}

val backend_jblas = project(core) {
    name = "golem-backend-jblas"
    group = "golem"
    artifactId = name
    version = golemVersion
    directory = name

    sourceDirectories {
        path("src")
    }
    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib:$kotVersion")
        compile("org.jblas:jblas:1.2.3")
    }
}
private fun getCoreDeps():Array<Project> {
    // Needed due to https://github.com/cbeust/kobalt/issues/270
    if (testBackends) {
        println("yes")
        return arrayOf(backend_jblas, backend_ejml, backend_mtj)
    }
    else {
        println("no")
        return emptyArray()
    }
}