@Library('pipeline-library')_

import com.test.GlobalVars

pipeline {
    agent any

    environment {
       POM_GROUP_ID = readMavenPom().getGroupId()
    }

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
        stage("deploy-Tomcat"){
                    steps{
                        script{
                            tomcatDeploy 'war-deployer', 'calculadora'
                        }
                    }       
        }
    }
}