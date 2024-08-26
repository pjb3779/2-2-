pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Checkout...'
                checkout([
                    $class: 'GitSCM', 
                    branches: [[name: '*/master']], 
                    userRemoteConfigs: [[
                        url: 'https://codehub.devcloud.cn-north-4.huaweicloud.com/4d17921d5d5c4cd4a3856321726056b2/termsrc.git',
                        credentialsId: 'huawei'
                    ]]
                ])
                sh 'ls -la' // 检查项目根目录下的文件
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
        
        stage('check backend again') {
            steps {
                dir('backend'){
                    sh 'pwd' // 打印当前路径
                    sh 'ls -la' // 列出当前目录的文件，确认是否有 pom.xml
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
                    sh 'pwd' // 打印当前路径
                    sh 'ls -la' // 列出当前目录的文件，确认是否有 pom.xml
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

        stage('Build Docker Images') {  
            steps {
                echo 'Building Docker images...'
                script {
                    docker.build('2244509212/workforterm-frontend:latest', './frontend')
                    docker.build('2244509212/workforterm-backend:latest', './backend')
                }
            }
        }

        stage('Push Docker Images') {   
            steps {
                echo 'Pushing Docker images...'
                script {
                    docker.withRegistry('', 'my_dockerhub_credentials') { 
                        docker.image('2244509212/workforterm-frontend:latest').push('latest')
                        docker.image('2244509212/workforterm-backend:latest').push('latest')
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
