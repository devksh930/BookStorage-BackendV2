pipeline {
   agent any
   environment {
        registry = 'ksh930/spring-boot-bookstorage'
        registryCredential = 'dockerhub-ksh930'
    }

    stages {
        stage('Checkout repository') {
            steps {
                checkout scm
            }
        }

        stage('Environment') {
            parallel {
                stage('chmod') {
                    steps {
                        sh 'chmod 755 ./gradlew'
                    }
                }
                stage('display') {
                    steps {
                        sh 'ls -la'
                    }
                }
            }
        }

        stage('gradlew Build Test') {
            stedps {
                sh './gradlew clean build'
            }
        }

        stage('Docker build') {
            steps {
                sh 'docker build -t $registry:latest .'
            }
        }

        stage('Docker push') {
            steps {
                withDockerRegistry([credentialsId: registryCredential, url: '']) {
                    sh 'docker push $registry:latest'
                }
            }
        }
        stage('Docker image Clean') {
            steps {
                sh 'docker rmi ksh930/spring-boot-bookstorage'
            }
        }
    }
}
