pipeline {
    agent any

    stages {
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
