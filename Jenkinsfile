pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
            }
        }

        stage('Test') {
            when {
                branch 'test'
            }
            steps {
                sh './gradlew test'
            }
        }
    }

    post {
        always {
            script {
                if (env.BRANCH_NAME == 'test') {
                    junit allowEmptyResults: true, testResults: '**/build/test-results/test/*.xml'
                }
            }
        }
    }
}
