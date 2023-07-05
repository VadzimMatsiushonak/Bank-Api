/* Requires the Docker Pipeline plugin */
pipeline {
    agent { docker { image 'gradle:8.2-jdk11-jammy' } }
    stages {
        stage('build') {
            steps {
                sh 'gradle build'
            }
        }
    }
}