pipeline {
    agent { label 'EC2' }
    stages {
        stage('Compile and Clean') {
            steps {

                sh "mvn clean compile"
            }
        }

        stage('test') {
            steps {
            				sh "mvn test"
                  }
            }

        stage('deploy') {
            steps {
                sh "mvn package"
            }
        }

        stage('Build Docker image'){
            steps {

                sh 'docker build -t  skrynnyk/demo-cloud:latest .'
            }
        }

        stage('Docker Login'){

            steps {
                 withCredentials([string(credentialsId: 'DockerId', variable: 'Dockerpwd')]) {
                    sh "docker login -u skrynnyk -p ${Dockerpwd}"
                }
            }
        }

        stage('Docker Push'){
            steps {
                sh 'docker push skrynnyk/demo-cloud:latest'
            }
        }

        stage('Docker deploy'){
            steps {
                sh 'docker run -itd -p 8080:8080 skrynnyk/demo-cloud:latest'
            }
        }
    }
}