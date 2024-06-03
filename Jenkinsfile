pipeline {
    agent any

    stages {
        stage('checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/CechinaDenis/book-store.git'
            }
        }
        stage('build') {
            steps{
                sh 'chmod +x ./mvnw'
                sh "./mvnw clean package"
            }
        }
    }
    post {
        failure {
            // Actions that should run only if the pipeline fails
            echo 'Pipeline failed!'
        }
    }
}
