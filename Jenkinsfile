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

                        withCredentials([string(credentialsId: 'sonarqube', variable: 'SECRET')]) { 
                            withSonarQubeEnv('SonarQube') {
							    sh "mvn sonar:sonar"
					    }
                            
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