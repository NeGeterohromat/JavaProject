plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.apache.commons","commons-csv","1.8")
    implementation("com.vk.api:sdk:1.0.9")
    implementation("org.apache.logging.log4j","log4j-slf4j-impl","2.11.2")
    implementation("org.apache.logging.log4j","log4j-api","2.11.2")
    implementation("org.apache.logging.log4j","log4j-core","2.11.2")
}

tasks.test {
    useJUnitPlatform()
}