pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "al0card/book-store"
        dockerImage = ''
        registryCredential = 'dockerhub_id'
    }

    tools {
        maven "maven_3.9.8"
    }

    stages {
        stage('prebuild cleanup') {
            steps {
                cleanWs()
            }
        }
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
                sh "dockerImage = docker.build $DOCKER_IMAGE:$BUILD_NUMBER"
            }
        }
        stage('deploy docker image') {
            steps {
                sh "docker.withRegistry('', registryCredential) { dockerImage.push() }"
            }
        }
        stage('cleaning up') {
            steps {
                sh "docker rmi $registry:$BUILD_NUMBER"
            }
        }

        post {
            failure {
//                 TODO: sent email to the responsible
                echo 'Pipeline failed!'
            }
        }
    }
}

//     stages {
//         stage('checkout') {
//             steps {
//                 cleanWs()
//                 sh "git --version"
//                 git branch: 'main', url: 'https://github.com/CechinaDenis/book-store.git'
//             }
//         }