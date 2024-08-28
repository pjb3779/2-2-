pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS_ID = 'dockerhub' // Docker Registry 凭据 ID
        // DOCKER_IMAGE_TAG = "${env.BRANCH_NAME}-${env.BUILD_NUMBER}"
        DOCKER_USER_ID = 'pjb3779'
        KUBE_CONFIG = credentials('kubernetes') // Kubernetes 配置凭据 ID
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    // Git 버퍼 크기 증가
                    sh 'git config --global http.postBuffer 524288000'
                }
                // 检出代码
                checkout scm
            }
        }

        stage('Build Frontend') {
            steps {
                script {
                    // 构建前端 Docker 镜像
                    docker.build("${DOCKER_USER_ID}/frontend:latest", "./frontend")
                }
            }
        }

        stage('Build Backend') {
            steps {
                script {
                    // 构建后端 Docker 镜像
                    docker.build("${DOCKER_USER_ID}/backend:latest", "./backend")
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    // 登录 Docker Registry
                    docker.withRegistry('https://index.docker.io/v2/', DOCKER_CREDENTIALS_ID) {
                        // 推送前端镜像
                        docker.image("${DOCKER_USER_ID}/frontend:latest").push()
                        // 推送后端镜像
                        docker.image("${DOCKER_USER_ID}/backend:latest").push()
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // 部署到 Kubernetes
                    withKubeConfig([credentialsId: KUBE_CONFIG]) {
                        bat 'kubectl apply -f k8s/mysql.yaml'
                        bat 'kubectl apply -f k8s/backend.yaml'
                        bat 'kubectl apply -f k8s/frontend.yaml'
                    }
                }
            }
        }

        // stage('Integration Test') {
        //     steps {
        //         script {
        //             // 运行集成测试
        //             // 可以根据需要修改下面的测试命令
        //             bat "npm run test:integration"
        //         }
        //     }
        // }
    }

    post {
        always {
            // script {
            //     bat "docker rmi ${DOCKER_USER_ID}/frontend:latest"
            //     bat "docker rmi ${DOCKER_USER_ID}/backend:latest"
            // }
            // echo 'Deleting Docker images due to failure.'
            // 清理工作区
            cleanWs()
            echo 'end'
        }
    }
}
