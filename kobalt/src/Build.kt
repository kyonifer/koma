import com.beust.kobalt.*
import com.beust.kobalt.plugin.packaging.*

val p = project {

    name = "golem"
    group = "golem"
    artifactId = name
    version = "0.5"

    var kotVersion = "1.0.1"

    sourceDirectories {
        path("src/main")
    }

    sourceDirectoriesTest {
        path("src/test")
    }

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib:$kotVersion")

        compile("org.ejml:all:0.27")
        compile("org.jblas:jblas:1.2.3")
        compile("com.beust:jcommander:1.48")
        compile("com.googlecode.matrix-toolkits-java:mtj:1.0.4")
        // Fix issue with pom-only artifacts in MTJ
        exclude("com.github.fommil.netlib:all:pom:1.1.2")

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
