plugins {
    id("java")
}

group = "com.dethemium.minecraft"
version = "1.18"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    compileOnly(group = "com.destroystokyo.paper", name = "paper-api", version = "1.12.2-R0.1-SNAPSHOT")
}

defaultTasks("clean", "build")

tasks {
    processResources {
        val placeholders = mapOf(
            "name" to project.name,
            "group" to project.group,
            "version" to project.version
        )
        filesMatching("plugin.yml") {
            expand(placeholders)
        }
    }

    jar {
        archiveFileName.set("${project.name}.jar")
    }
}
