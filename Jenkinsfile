pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Checkout...'
                checkout([
                    $class: 'GitSCM', 
                    branches: [[name: '*/main']], 
                    userRemoteConfigs: [[
                        url: 'https://github.com/pjb3779/2-2-'
                    ]]
                ])
            }
        }

        stage('Build Frontend') { 
            agent {
                docker {
                    image 'node:latest' // 使用本地的 Node.js 镜像
                    args '-v /var/run/docker.sock:/var/run/docker.sock'
                    reuseNode true
                }
            } 
            steps {
                echo 'Frontend-building...'
                dir('frontend') { 
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('Build Backend') {
            agent {
                docker {
                    image 'maven:latest' // 使用本地的 Maven 镜像
                    args '-v /var/run/docker.sock:/var/run/docker.sock'
                    reuseNode true
                }
            }
            steps {
                echo 'backend-building...'
                dir('backend') {
                    sh 'mvn clean test package'
                }
            }
        }

        stage('Test') {
            steps {
                echo 'Generating test report...'
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Integration Test') {
            steps {
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    echo 'Integration testing...'
                    sh '#!/bin/bash -il newman run ProjectReader.postman_collection.json -r cli,html --reporter-html-export report/Testreport.html'
                }
            }
        }

        stage('Email Notification on Failure') {
            steps {
                script {
                    if (currentBuild.result == 'FAILURE') {
                        emailext(
                            subject: 'Integration Test Failure in Build ${env.BUILD_NUMBER}',
                            body: 'The integration test failed in build ${env.BUILD_NUMBER}. Please check the test results.',
                            recipientProviders: [
                                [$class: 'CulpritsRecipientProvider'],
                                [$class: 'RequesterRecipientProvider']
                            ],
                            to: 'pjb3779@naver.com' // 替换为你希望接收通知的邮箱地址
                        )
                    }
                }
            }
        }

        stage('Integration Test Result') {
            steps {
                publishHTML([
                    allowMissing: false, // 如果文件丢失，构建失败
                    alwaysLinkToLastBuild: true, 
                    keepAll: true, 
                    reportDir: "${env.WORKSPACE}/report", // 动态路径
                    reportFiles: 'Testreport.html', 
                    reportName: 'Integration Test Report', 
                    reportTitles: '集成测试'
                ])
            }
        }

        stage('Build Docker Images') {  
            steps {
                echo 'Building Docker images...'
                script {
                    docker.build('pjb3779/myfront-app:latest', './frontend')
                    docker.build('pjb3779/myback-app:latest', './backend')
                }
            }
        }

        stage('Push Docker Images') {   
            steps {
                echo 'Pushing Docker images...'
                script {
                    docker.withRegistry('', 'docker') { 
                        docker.image('pjb3779/myfront-app:latest').push('latest')
                        docker.image('pjb3779/myback-app:latest').push('latest')
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') { 
            steps {
                echo 'Deploying to Kubernetes...'
                script {
                    sh 'kubectl apply -f k8s/mysql.yaml'
                    sh 'kubectl apply -f k8s/backend.yaml'
                    sh 'kubectl apply -f k8s/frontend.yaml'
                }
            }
        }
    }

    post {
        always {
            echo 'Finish!!!'
            cleanWs()
        }
    }
}
