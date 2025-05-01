# Jenkins Shared Library

A reusable pipeline code that can be shared across multiple Jenkins pipelines.

## Getting Started

To start using this Shared Library in your Jenkins pipelines:

1. Add it as a _Global Shared Library_ in your Jenkins instance:
   - Go to **Manage Jenkins** > **Configure System**.
   - Scroll down to **Global Trusted Pipeline Libraries**.
   - Add a new library and configure:
     - **Name**: `jenkins-shared-library`
     - **Default version**: `main` (or a specific branch/tag)
     - **Repository URL**: `<URL to this repository>`

3. Once added, you can reference this library in your Jenkinsfiles.

## Library Structure
- `vars/`: Contains Groovy files defining custom pipeline steps. Each `.groovy` file corresponds to a step callable in your Jenkinsfile.
- `src/`: Contains helper classes and methods in a structured Java-like package format.
- `resources/`: Stores non-code resources such as templates or configuration files.

## Usage

### Import the library

To use this library in your Jenkins pipeline:

```groovy
@Library('jenkins-shared-library') _
```

Place the above line at the top of your `Jenkinsfile` to import the library.

### Defining Your Pipeline

Here’s an example `Jenkinsfile` using this library:

```groovy
@Library('jenkins-shared-library') _

pipeline {
    agent any
    stages {
        stage('Example') {
            steps {
                exampleStep()
            }
        }
    }
}
```

The `exampleStep()` function is defined in `vars/exampleStep.groovy`.
