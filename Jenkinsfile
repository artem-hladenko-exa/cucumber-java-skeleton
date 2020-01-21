pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh './gradlew clean compile'
            }
        }
        stage('Test') {
            steps {
                echo './gradlew -Dcucumber.options="..." test'
            }
        }
    }
}