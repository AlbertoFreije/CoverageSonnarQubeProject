pipeline {
    agent any
    tools { 
        maven 'Maven 3.3.9' 
        jdk 'jdk8' 
    }
    stages {
        
        stage('sonar and maven'){
			parallel{
				stage('Sonar Report'){
					steps{
                        
						withSonarQubeEnv('SonarQube') {
							sh "${tool("SONARQUBE")}/bin/sonar-scanner \
                        -Dsonar.projectKey=gs-maven \
                        -Dsonar.sources=. \
                        -Dsonar.css.node=. \
                        -Dsonar.host.url=http://192.168.56.10:9000 \
                        -Dsonar.login=992f76e8559c7d4b133a40ded7d396cc4d1ad003"
							
					    }
						
						timeout(time: 1, unit: 'HOURS') {
								script{
									def qg = waitForQualityGate()
									if (qg.status != 'OK') {
										error "Pipeline aborted due to quality gate failure: ${qg.status}"
									}
								}
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
    }
}