pipeline {
    agent any
    tools { 
        maven 'Maven 3.3.9' 
        jdk 'jdk8' 
    }
    environment {
        PROJECT_ROOT = "CoverageSonnarQubeProject"
    }
    stages {
        
        stage('sonar and maven'){
			parallel{
				
                stage("scan"){
                    environment {
                        scannerHome = tool 'sonar-scanner'
                    }
                    steps {
                        withSonarQubeEnv('sonarqube') {
                                 sh "${scannerHome}/bin/sonar-scanner \
                                  -Dsonar.projectKey=gs-maven \
                                  -Dsonar.sources=. \
                                  -Dsonar.css.node=. \
                                  -Dsonar.host.url=http://192.168.56.10:9000 \
                                  -Dsonar.login=992f76e8559c7d4b133a40ded7d396cc4d1ad003"
                        }
                    

                    timeout(time: 10, unit: 'MINUTES') {
                        waitForQualityGate abortPipeline: qualityGateValidation(waitForQualityGate())
                    }
                    }
                }
				
				stage('Maven Build'){
					steps{
					
						sh "mvn clean package"
					}
				
				}
			
			}
		
		}	

        stage("deploy-Tomcat"){
                    steps{
                        script{
                                deploy adapters: [tomcat9(credentialsId: 'war-deployer', path: '', url: 'http://192.168.56.10:8081')], contextPath: 'calculadora', war: '**/*.war'
                        }
                    }       
        }
    }
}