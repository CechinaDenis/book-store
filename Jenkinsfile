pipeline {
    agent any

     environment {
         DOCKER_IMAGE = "al0card/book-app"
         DOCKER_LATEST_TAG = "latest"
         dockerImageVersion = ''
         dockerImageLatest = ''
         registryCredential = 'dockerhub_id'
     }

    tools {
        maven "maven_3.9.8"
    }

    stages {

//         stage('prebuild cleanup') {
//             steps {
//                 cleanWs()
//             }
//         }
        stage('build') {
            steps {
                sh "mvn clean verify"
            }
            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        stage('build docker image') {
            steps {
                script {
                    dockerImageVersion = docker.build DOCKER_IMAGE + ":$BUILD_NUMBER"
                    dockerImageLatest = docker.build DOCKER_IMAGE + ":$DOCKER_LATEST_TAG"
                }
            }
        }
        stage('deploy docker image') {
            steps {
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImageVersion.push()
                        dockerImageLatest.push()
                    }
                }
            }
        }
        stage('cleaning up') {
            steps {
                sh "docker rmi $DOCKER_IMAGE:$BUILD_NUMBER"
                sh "docker rmi $DOCKER_IMAGE:$DOCKER_LATEST_TAG"
            }
        }
    }

    post {
        failure {
//         TODO: sent email to the responsible
            echo 'Pipeline failed!'
        }
    }
}
