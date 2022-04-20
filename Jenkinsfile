@Library('pipeline-library')_

import com.test.SonarVars
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
                    environment {
                        scannerHome = tool SonarVars.foo
                    }
                    steps { 
                        sonarQube()
                    }
                }
                stage('Maven Build'){
                     mavenBuild()
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