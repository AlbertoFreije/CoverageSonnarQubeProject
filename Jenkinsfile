pipeline {
    agent any
    tools { 
        maven 'Maven 3.3.9' 
        jdk 'jdk8' 
    }
    environment {
        PROJECT_ROOT = "CoverageProject"
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
                                  -Dsonar.host.url=http://192.168.56.10:9000 \
                                  -Dsonar.login=992f76e8559c7d4b133a40ded7d396cc4d1ad003 \
                                  -Dsonar.sources=/src/main \
                                  -Dsonar.language=java \
                                  -Dsonar.java.binaries=./${PROJECT_ROOT}/target/classes \
                                  -Dsonar.java.test.binaries=${PROJECT_ROOT}/src/test/java \
                                  -Dsonar.junit.reportPaths=./${PROJECT_ROOT}/target/surefire-reports \
                                  -Dsonar.coverage.jacoco.xmlReportPaths=./${PROJECT_ROOT}/target/site/jacoco/jacoco.xml \
                                  -Dsonar.java.coveragePlugin=jacoco"
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