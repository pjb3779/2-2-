pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS_ID = 'dockerhub' // Docker Registry 凭据 ID
        // DOCKER_IMAGE_TAG = "${env.BRANCH_NAME}-${env.BUILD_NUMBER}"
        DOCKER_USER_ID = '2244509212'
    }

    stages {
        stage('Checkout') {
            steps {
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
                echo 'in deploy'
                script {
                    withKubeConfig([credentialsId: 'kubeconfig']) {
                        echo 'in config'
                        bat 'kubectl version'
                        // bat 'kubectl create configmap mysql-initdb-config --from-file=docreading.sql -n myapp'
                        bat 'kubectl apply -f k8s/mysql.yaml'
                        bat 'kubectl apply -f k8s/backend.yaml'
                        bat 'kubectl apply -f k8s/frontend.yaml'
                    }
                }
            }
        }

        stage('Integration Test') {
            steps {
                script {
                    bat 'newman run postman_script/postman_collection.json --reporters cli,html --reporter-html-export postman_report.html'
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'postman_report.html', allowEmptyArchive: true
            publishHTML([allowMissing: false, alwaysLinkToLastBuild: true, keepAll: true, reportDir: '', reportFiles: 'postman_report.html', reportName: 'Postman HTML Report'])
            cleanWs()
            echo 'end'
        }
    }
}
