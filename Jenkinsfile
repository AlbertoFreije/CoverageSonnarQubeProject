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

                            script{

                                def scannerHome = tool 'SONARQUBE';
                                withSonarQubeEnv("SonarQube") {
                                    sh "${tool("SONARQUBE")}/bin/sonar-scanner \
                                        -Dsonar.projectKey=gs-maven \
                                        -Dsonar.sources=. \
                                        -Dsonar.css.node=. \
                                        -Dsonar.host.url=http://192.168.56.10:9000 \
                                        -Dsonar.login=992f76e8559c7d4b133a40ded7d396cc4d1ad003"
                                       sh 'sleep 10'
                                }
                            }

                            //     timeout(time: 5, unit: 'MINUTES') {
                            //         script{
                            //             sh 'sleep 100'
        
                            //             withCredentials([string(credentialsId: 'sonarqube', variable: 'SECRET')]) { 
                            //                     def qg = waitForQualityGate();
                            //                     if (qg.status != 'OK') {
                            //                             error "Pipeline aborted due to quality gate failure: ${qg.status}"
                            //                     }
                            //             }
                                        
                            //         }
                            // }
					
					}
				
				}
				
				stage('Maven Build'){
					steps{
					
						sh "mvn clean package"
                        sh "mv target/*.war target/Calculadora.war"
					}
				
				}
                stage("deploy-Tomcat"){
                    steps{
                        script{
                                deploy adapters: [tomcat9(credentialsId: 'war-deployer', path: '', url: 'http://192.168.56.10:8081')], contextPath: 'hello', war: '**/*.war'
                        }
                    }       
                }
			
			}
		
		}	
    }
}