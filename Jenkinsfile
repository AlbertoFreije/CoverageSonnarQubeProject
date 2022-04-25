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

                            dependencyCheck {

                                    scan= '**/*.war'
    
                                    cve {
                                        urlModified = "<<here goes the link to new schema version>>"
                                    }
                            }
                            
                            //dependencyCheck additionalArguments: 'cveUrlModified="http://mirror-url/nist/nvdcve-1.1-modified.json.gz"' , 'scan= **/*.war --format HTML', odcInstallation: '7.1.0'
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