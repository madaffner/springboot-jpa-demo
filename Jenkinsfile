pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: '7a1300c2-fe7a-4557-a175-308528658d10', url: 'git@github.com:madaffner/springboot-jpa-demo.git']])
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -DskipTests clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
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