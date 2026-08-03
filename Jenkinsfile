pipeline {
    agent any

    environment {
        AUTH_IMAGE = "diyak644/auth-service:v1"
        QUANTITY_IMAGE = "diyak644/quantity-service:v1"
        FRONTEND_IMAGE = "diyak644/quantity-frontend:v2"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    credentialsId: 'github-token',
                    url: 'https://github.com/DiyaKhatter04/DEVOPS.git'
            }
        }

        stage('Build Auth Service') {
            steps {
                dir('auth-service') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Quantity Service') {
            steps {
                dir('quantity-service') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Frontend') {
            steps {
                dir('frontend') {
                    sh '''
                        npm install
                        npm run build
                    '''
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                sh '''
                    docker build -t $AUTH_IMAGE ./auth-service
                    docker build -t $QUANTITY_IMAGE ./quantity-service
                    docker build -t $FRONTEND_IMAGE ./frontend
                '''
            }
        }

        stage('Push Docker Images') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockercreds',
                        usernameVariable: 'DOCKER_USERNAME',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )
                ]) {
                    sh '''
                        echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin

                        docker push $AUTH_IMAGE
                        docker push $QUANTITY_IMAGE
                        docker push $FRONTEND_IMAGE

                        docker logout
                    '''
                }
            }
        }

       stage('Deploy') {
    steps {
        withCredentials([
            string(credentialsId: 'GoogleclientID', variable: 'GOOGLE_CLIENT_ID'),
            string(credentialsId: 'googlesecretkey', variable: 'GOOGLE_CLIENT_SECRET')
        ]) {
            sh '''
            cd /home/ubuntu/DEVOPS

            export GOOGLE_CLIENT_ID=$GOOGLE_CLIENT_ID
            export GOOGLE_CLIENT_SECRET=$GOOGLE_CLIENT_SECRET

            docker compose down
            docker compose pull
            docker compose up -d
            '''
        }
    }
}
    post {
        success {
            echo 'CI/CD Pipeline Executed Successfully!'
        }

        failure {
            echo 'Pipeline Failed!'
        }
    }
}              
