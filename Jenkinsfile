@Library('pipeline-library')_

import com.test.GlobalVars

pipeline {
    agent any

    tools { 
        maven GlobalVars.maven
        jdk GlobalVars.jdk
    }
    stages {
        stage('sonar and maven'){
			parallel{
				
                stage("scan"){
                    steps { 
                        sonarQube()
                    }
                }
				stage('Maven Build'){
					steps{
                        mavenBuild()
					}
				
				}
			}
		}
        stage("OWASP"){
                    steps{
                        script{
                            
                            dependencyCheck additionalArguments: 'scan=" export _JAVA_OPTIONS="-Dcom.sun.security.enableAIAcaIssuers=true" && ./gradlew dependencyCheckAnalyze path to scan" --format HTML', odcInstallation: '7.1.0'
                        }
                    }       
        }	
        stage("deploy-Tomcat"){
                    steps{
                        script{
                            tomcatDeploy 'war-deployer', 'calculadora'
                        }
                    }       
        }
    }
}