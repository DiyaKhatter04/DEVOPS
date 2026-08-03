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
	
	dir('frontend') {
            sh 'docker build -t quantity-frontend:v1 .'
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
                export GOOGLE_CLIENT_ID=$GOOGLE_CLIENT_ID
                export GOOGLE_CLIENT_SECRET=$GOOGLE_CLIENT_SECRET

                docker compose down
                docker compose pull
                docker compose up -d
            '''
        }
    }
}

    }
}
