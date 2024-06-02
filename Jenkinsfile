
pipeline {
    agent any

        stage('Build and Test') {
            steps { 
                sh './mvnw clean package'
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