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

    stage('prebuild cleanup') {
        cleanWs()
    }
    stage('build') {
        steps {
            sh "mvn clean package"
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
                dockerImage = docker.build DOCKER_IMAGE + ":$BUILD_NUMBER"
            }
        }
    }
    stage('deploy docker image') {
        steps {
            script {
                docker.withRegistry('', registryCredential) {
                    dockerImage.push()
                }
            }
        }
    }
    stage('cleaning up') {
        steps {
            sh "docker rmi $registry:$BUILD_NUMBER"
        }
    }

    post {
        failure {
//             TODO: sent email to the responsible
            echo 'Pipeline failed!'
        }
    }
}
