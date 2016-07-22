import com.beust.kobalt.*
import com.beust.kobalt.api.Project
import com.beust.kobalt.plugin.packaging.*
import com.beust.kobalt.plugin.publish.bintray

val kotVersion = "1.0.3"
val golemVersion = "0.6"
val groupName = "golem"

fun Project.makeSubProject(projName: String) {
    name = projName
    group = groupName
    artifactId = name
    version = golemVersion
    directory=name
    sourceDirectories {
        path("src")
    }
    assemble {
        mavenJars{}
        jar {}
    }
    bintray {
        publish=true
    }
}


val core = project {
    makeSubProject("golem-core")

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib:$kotVersion")
        compile("com.beust:jcommander:1.48")
        compile("org.slf4j:slf4j-api:1.7.21")
        compile("org.knowm.xchart:xchart:3.1.0")
    }
}

val logging = project {
    makeSubProject("golem-logging")

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib:$kotVersion")
        compile("org.slf4j:slf4j-api:1.7.21")
        compile("ch.qos.logback:logback-classic:1.1.7")
    }
}

val backend_mtj = project(core) {
    makeSubProject("golem-backend-mtj")

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib:$kotVersion")
        compile("com.googlecode.matrix-toolkits-java:mtj:1.0.4")
        // Fix issue with pom-only artifacts in MTJ
        exclude("com.github.fommil.netlib:all:pom:1.1.2")
    }
}

val backend_ejml = project(core) {
    makeSubProject("golem-backend-ejml")

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib:$kotVersion")
        compile("org.ejml:all:0.27")
    }
}

val backend_jblas = project(core) {
    makeSubProject("golem-backend-jblas")

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib:$kotVersion")
        compile("org.jblas:jblas:1.2.3")
    }
}

// Tests can't be in core or one backend because they test all of them
// and kobalt doesnt support cyclic dependencies.
val backend_tests = project(core, backend_ejml, backend_jblas, backend_mtj) {
    name = "golem-tests"
    group = groupName
    artifactId = name
    version = golemVersion
    directory = name

    dependenciesTest {
        compile("org.jetbrains.kotlin:kotlin-stdlib:$kotVersion")
        compile("junit:junit:4.12")
        compile("org.jetbrains.kotlin:kotlin-test:$kotVersion")
    }
    sourceDirectoriesTest {
        path("test")
    }
    test {include("**Tests.class")}

}
