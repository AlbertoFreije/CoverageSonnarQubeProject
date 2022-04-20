@Library('pipeline-library')_

import com.cleverbuilder.SonarVars
import com.cleverbuilder.GlobalVars

pipeline {
    agent any
    tools { 
        maven GlobalVars.maven
        jdk GlobalVars.foo2
    }
    stages {
        stage('sonar and maven'){
			parallel{
				
                stage("scan"){
                    environment {
                        scannerHome = tool SonarVars.foo
                    }
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
        stage("deploy-Tomcat"){
                    steps{
                        script{
                            tomcatDeploy 'war-deployer', 'calculadora'
                        }
                    }       
        }
    }
}