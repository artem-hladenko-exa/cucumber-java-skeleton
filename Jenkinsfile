pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Test') {
            steps {
                echo './gradlew -Dcucumber.options="..." test'
            }
        }
    }
}