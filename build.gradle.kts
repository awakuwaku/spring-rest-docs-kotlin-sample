import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.3.4.RELEASE"
  id("io.spring.dependency-management") version "1.0.10.RELEASE"
  kotlin("jvm") version "1.3.72"
  kotlin("plugin.spring") version "1.3.72"
  id("org.asciidoctor.convert") version "1.6.1"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
  // Kotlin
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  // Util
  implementation("com.jayway.jsonpath:json-path:2.4.0")
  // Spring Boot
  implementation("org.springframework.boot:spring-boot-starter-web")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  implementation("org.springframework.boot:spring-boot-starter-aop")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  // Spring Boot Test
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.security:spring-security-test")
  // Spring REST Docs
  testImplementation("org.springframework.restdocs:spring-restdocs-core:2.0.5.RELEASE")
  testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:2.0.5.RELEASE")
}

//////////////////////////////////////
// JUnit Test Target
//////////////////////////////////////
tasks.withType<Test> {
  useJUnitPlatform()
  exclude("**/*$*")
}

//////////////////////////////////////
// Kotlin Compile
//////////////////////////////////////
tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "11"
  }
}

//////////////////////////////////////
// Asciidoctor
//////////////////////////////////////
val snippetsDir by extra { file("build/generated-snippets") }
tasks {
  test {
    outputs.dir(snippetsDir)
  }

  asciidoctor {
    inputs.dir(snippetsDir)
    sourceDir("src/main/asciidoc")
    dependsOn(test)
  }
}