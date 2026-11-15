# Sterling example: Spring Boot 4

A small Spring Boot 4 service built with [Sterling](https://github.com/balakmran/sterling),
the quality gate for Java 21+ Gradle builds. It exists to show what adopting
Sterling takes on a service that looks like yours:

- Java 25, Spring Boot 4.1, Gradle 9
- Lombok (`@Value`, `@Builder`) and a MapStruct mapper
- an API interface generated from an OpenAPI spec into `build/generated`
- `@NullMarked` packages, one using Spring's `Assert.notNull`
- a `test` suite and an `integrationTest` JVM test suite with `@SpringBootTest`

Sterling's part of [`build.gradle.kts`](build.gradle.kts) is one line, the
plugin, and [`gradle.properties`](gradle.properties) picks a profile. There is
no coverage exclude and no tool configuration: Sterling leaves out the
generated classes and the application class, counts the integration suite,
and formats nothing under `build/generated`.

```bash
./gradlew build                          # the gate, at STANDARD
./gradlew build -Psterling.profile=strict
./gradlew stlInfo                        # what Sterling resolved
```

```
Sterling 0.2.0
Profile:              STANDARD
Gate wired to check:  yes
Daemon JVM:           25
Java toolchain:       25
Formatting:           on   (google-java-format 2.0.0 via Spotless 8.11.0)
Error Prone:          on   (2.52.0; warnings are not errors)
NullAway:             on   (0.14.3; JSpecify 1.0.1; @NullMarked code only)
SpotBugs:             on   (4.10.5; effort default; medium confidence and up)
Coverage:             on   (JaCoCo 0.8.16; instruction >= 80%, branch >= 70%; integrationTest, test; Spring Boot main class excluded)
```

CI runs the build under `STANDARD` and `STRICT`. The service has no database,
so it needs only a JDK 25.

Licensed under the [Apache License 2.0](LICENSE).
