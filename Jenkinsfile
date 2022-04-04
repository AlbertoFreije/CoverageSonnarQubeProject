pipeline {
    agent any
    environment { 
        CRED = credentials('sonarqube') 
    }
    tools { 
        maven 'Maven 3.3.9' 
        jdk 'jdk8' 
    }
    stages {
          stage("Code Quality Check via SonarQube") {
            steps {
                script{
                    def scannerHome = tool 'sonarqube';
                    withSonarQubeEnv("SonarQube") {
                        sh "${tool("sonarqube")}/bin/sonar-scanner \
                        -Dsonar.projectKey=gs-maven \
                        -Dsonar.sources=. \
                        -Dsonar.css.node=. \
                        -Dsonar.host.url=http://192.168.56.10:9000 \
                        -Dsonar.login=992f76e8559c7d4b133a40ded7d396cc4d1ad003"
                    }
                    def qg = waitForQualityGate()
                }
                sh 'sleep 10'
            }
          }
          stage("Quality Gate"){
              steps{
                  script{
                      withCredentials([string(credentialsId: 'sonarqube', variable: 'SECRET')]) { 
                        withSonarQubeEnv("SonarQube") {
                            timeout(time: 15, unit: 'MINUTES') {
                            retry(3){
                                def qg = waitForQualityGate()
                                if (qg.status != 'OK') {
                                    error "Pipeline aborted due to quality gate failure: ${qg.status}"
                                }
                            }
                            
                        }

                        }
                    }
                                
                }
            }
          
        }
          stage ('Build') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true install' 
                sh 'mvn test -e'
            } 
          }
    
    }
}