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
							    sh "mvn sonar:sonar -Dsonar.login=992f76e8559c7d4b133a40ded7d396cc4d1ad003"
                                sh 'sleep 30'
					        }

						
						timeout(time: 1, unit: 'HOURS') {
								script{
                                    withCredentials([string(credentialsId: 'sonarqube', variable: 'SECRET')]) { 
                                            def qg = waitForQualityGate()
									        if (qg.status != 'OK') {
										            error "Pipeline aborted due to quality gate failure: ${qg.status}"
									        }
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