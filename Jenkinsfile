/* Requires the Docker Pipeline plugin */
pipeline {
    agent { docker { image 'gradle:7.5.1-jdk11-jammy' } }
    stages {
        stage('build') {
            steps {
                sh 'gradle build'
            }
        }
    }
}