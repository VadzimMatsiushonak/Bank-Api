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

docker run -d \
  --name jenkins \
  --user root \
  -p 8888:8080 \
  -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v $(which docker):/usr/bin/docker \
  jenkins/jenkins:lts-jdk11

docker run -d --name jenkins --user root -p 8080:8080 -p 50000:50000 -v jenkins_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock jenkins/jenkins:lts-jdk11

docker network create jenkins

docker run  --name jenkins --privileged -v /var/run/docker.sock:/var/run/docker.sock -d -p 8888:8080 -p 50000:50000 jenkins/jenkins:lts-jdk11



docker run \
  --name jenkins-docker \
  --rm \
  --detach \
  --privileged \
  --network jenkins \
  --network-alias docker \
  --env DOCKER_TLS_CERTDIR=/certs \
  --volume jenkins-docker-certs:/certs/client \
  --volume jenkins-data:/var/jenkins_home \
  --publish 2376:2376 \
  docker:dind \
  --storage-driver overlay2