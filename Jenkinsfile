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
                            
                            dependencyCheck additionalArguments: ''' 
                                -o "./" 
                                -s "./"
                                -f "ALL" 
                                --prettyPrint''', odcInstallation: '7.1.0'
                                
                            dependencyCheckPublisher pattern: 'dependency-check-report.xml'

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