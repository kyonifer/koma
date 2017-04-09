import com.beust.kobalt.*
import com.beust.kobalt.api.Project
import com.beust.kobalt.plugin.packaging.*
import com.beust.kobalt.plugin.publish.bintray

val kotVersion = "1.1.1"
val golemVersion = "0.9"
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

    sourceDirectories {
        path("srcjvm")
    }

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib:$kotVersion")
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

val backend_purekt = project(core) {
    makeSubProject("golem-backend-purekt")

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib:$kotVersion")
    }
}

// Tests can't be in core or one backend because they test all of them
// and kobalt doesnt support cyclic dependencies.
val backend_tests = project(core, 
                            backend_ejml, 
                            backend_jblas, 
                            backend_mtj, 
                            backend_purekt) {
    name = "golem-tests"
    group = groupName
    artifactId = name
    version = golemVersion
    directory = name
    
    
    
    dependenciesTest {
        compile("org.jetbrains.kotlin:kotlin-stdlib:$kotVersion")
        compile("junit:junit:4.12")
        compile("org.jetbrains.kotlin:kotlin-test:$kotVersion")
        compile("org.jblas:jblas:1.2.3")
        compile("org.ejml:all:0.27")
        compile("com.googlecode.matrix-toolkits-java:mtj:1.0.4")
    }
    sourceDirectoriesTest {
        path("test")
    }
    test {
        // Some natives are broken on some systems, and our goal isn't to test netlib's backends
        jvmArgs("-Dcom.github.fommil.netlib.BLAS=com.github.fommil.netlib.F2jBLAS",
                "-Dcom.github.fommil.netlib.LAPACK=com.github.fommil.netlib.F2jLAPACK",
                "-Dcom.github.fommil.netlib.ARPACK=com.github.fommil.netlib.F2jARPACK")
        include("**Tests.class")
    }

}
