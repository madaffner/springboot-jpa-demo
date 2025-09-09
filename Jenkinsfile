pipeline {
    agent any

    environment {
        MYSQL_DB_CREDS = credentials('itzbund_demo_jdbc')
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: '7a1300c2-fe7a-4557-a175-308528658d10', url: 'git@github.com:madaffner/springboot-jpa-demo.git']])
            }
        }
        stage('Build') {
            steps {
                script {
                    bat 'mvn -DskipTests clean install'
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    bat 'mvn test'
                }
            }
            post {
                always {
                    junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
                }
            }
        }
        stage('Analysis') {
            steps {
                recordIssues sourceCodeRetention: 'LAST_BUILD', tools: [java(reportEncoding: 'UTF-8')]
            }
        }
    }
}