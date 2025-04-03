plugins {
    id("java")
}

group = "org.eimerarchive"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:5.3.1") {
        exclude(module="opus-java")
    }
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}


tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "com.eimerarchive.Main"
        )
    }
}