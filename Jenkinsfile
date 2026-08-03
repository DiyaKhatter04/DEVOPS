pipeline {

    agent any

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                url: 'https://github.com/DiyaKhatter04/DEVOPS.git'
            }
        }

        stage('Build Application') {
    steps {

        dir('auth-service') {
            sh 'mvn clean package -DskipTests'
        }

        dir('quantity-service') {
            sh 'mvn clean package -DskipTests'
        }

    }
}
     stage('Docker Build') {
    steps {

        dir('auth-service') {
            sh 'docker build -t auth-service:v1 .'
        }

        dir('quantity-service') {
            sh 'docker build -t quantity-service:v1 .'
        }

    }
}

    }
}
