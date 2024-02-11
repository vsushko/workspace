Install gradle via brew:
```
brew install gradle
```
List all tasks:
```
gradle tasks --all
```
Execute task:
```
gradle taskName
gradle tN
```

### Gradle phases:
- **Initialization**: In this phase, Gradle determines which projects are going to be included in the build process. It creates a Project instance for each project that's going to be built.
- **Configuration**: During this phase, Gradle interprets the build scripts of all the projects that were determined in the initialization phase. It configures the Project instances created in the initialization phase.
- **Execution**: In this phase, Gradle executes the tasks that were configured in the configuration phase. The tasks are executed in the order that they were declared in the build script.

The `--daemon` flag enables the Gradle Daemon, a background process that improves build speed by reusing computations and keeping build data in memory.
```
gradle build --daemon
```
The `--no-daemon` flag instructs Gradle to execute the build without using the Gradle Daemon, a background process for caching build data.
```
gradle build --no-daemon
```
Gradle project interface:
```
https://docs.gradle.org/current/javadoc/org/gradle/api/Project.html
```
Gradle project properties:
```
https://docs.gradle.org/current/userguide/project_properties.html
```
Executes the 'clean' task for the 'web' module, removing all build artifacts in a Gradle project:
```
gradle :web:clean
```
Show depencencies:
```
gradle web:dependencies
```
