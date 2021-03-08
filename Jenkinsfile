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
                sh "mvn package -Dmaven.test.skip=true"
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

        stage('Stop previous containers'){
                    steps {
                        sh 'docker ps -f name=dockerContainer -q | xargs --no-run-if-empty docker container stop'
                   					sh 'docker container ls -a -fname=dockerContainer -q | xargs -r docker container rm'
                    }
                }

        stage('Docker deploy'){
            steps {
                sh 'docker run -d -p 8080:8080 --rm --name dockerContainer skrynnyk/demo-cloud:latest'
            }
        }
    }
    post {
           always {
                junit '**/target/surefire-reports/*.xml'
                 }
					}
}