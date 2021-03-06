# spring-rest-docs-kotlin-sample
Spring REST Docs の Kotlin Build Sample


# 前提
-   JDK
    - [OpenJDK (11.0.x)](http://openjdk.java.net/)
-   Kotlin
    - [Kotlin (1.3.72)](https://kotlinlang.org/)
-   Build Tool
    - [Gradle (6.6.1)](https://gradle.org/)
-   Framework
    - [Spring Boot (2.3.4.RELEASE)](https://spring.io/projects/spring-boot)
    - [Spring REST Docs (2.0.5.RELEASE)](https://spring.io/projects/spring-restdocs)
-   IDE
    -   [Eclipse (2020‑09)](http://www.eclipse.org/home/index.php) + [Spring Tools](https://marketplace.eclipse.org/content/spring-tool-suite-sts-eclipse)


# 利用方法
Gradleにて `asciidoctor` の `task` を実行することで、 `build/asciidoc/html5` 配下にAPIドキュメントのhtmlファイルを生成する。
  ```shell
  ./gradlew clean asciidoctor
  ```

