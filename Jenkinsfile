// Jenkinsfile

pipeline {
  // Assign to docker agent(s) label, could also be 'any'
  agent {
    label 'docker'
  }

  stages {
    stage('Docker node test') {
      agent {
        docker {
          // Set both label and image
          label 'docker'
          image 'node:7-alpine'
          args '--name docker-node' // list any args
        }
      }
      steps {
        // Steps run in node:7-alpine docker container on docker agent
        sh 'node --version'
      }
    }

    stage('Docker maven test') {
      agent {
        docker {
          // Set both label and image
          label 'docker'
          image 'maven:3-alpine'
        }
      }
      steps {
        // Steps run in maven:3-alpine docker container on docker agent
        sh 'mvn --version'
      }
    }
  }
}