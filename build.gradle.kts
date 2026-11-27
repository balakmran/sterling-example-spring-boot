plugins {
    java
    id("org.springframework.boot") version "4.1.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openapi.generator") version "7.27.0"
    id("dev.balakmran.sterling") version "0.3.0"
}

group = "dev.balakmran"
version = "0.0.1-SNAPSHOT"

java {
    toolchain { languageVersion = JavaLanguageVersion.of(25) }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.mapstruct:mapstruct:1.6.3")
    compileOnly("org.projectlombok:lombok:1.18.48")
    annotationProcessor("org.projectlombok:lombok:1.18.48")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    testCompileOnly("org.projectlombok:lombok:1.18.48")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.48")
}

testing {
    suites {
        named<JvmTestSuite>("test") {
            useJUnitJupiter()
            dependencies {
                implementation("org.springframework.boot:spring-boot-starter-webmvc-test")
            }
        }
        register<JvmTestSuite>("integrationTest") {
            useJUnitJupiter()
            dependencies {
                implementation(project())
                implementation("org.springframework.boot:spring-boot-starter-webmvc-test")
            }
        }
    }
}

// Generates the API interface and its models from the spec into build/generated.
openApiGenerate {
    generatorName.set("spring")
    inputSpec .set(layout.projectDirectory.file("src/main/resources/openapi/books.yaml").asFile.path)
    outputDir.set(layout.buildDirectory.dir("generated/openapi").get().asFile.path)
    apiPackage.set("dev.balakmran.example.api")
    modelPackage.set("dev.balakmran.example.api.model")
    configOptions.set(
        mapOf(
            "interfaceOnly" to "true",
            "useSpringBoot3" to "true",
            "useTags" to "true",
            "documentationProvider" to "none",
            "annotationLibrary" to "none",
            "openApiNullable" to "false",
        ))
}

sourceSets.main { java.srcDir(layout.buildDirectory.dir("generated/openapi/src/main/java")) }

tasks.compileJava { dependsOn(tasks.openApiGenerate) }
