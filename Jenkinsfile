pipeline {
    agent any
    environment { 
        CRED = credentials('sonarqube') 
    }
    tools { 
        maven 'Maven 3.3.9' 
        jdk 'jdk8' 
    }
    stages {
          stage("Code Quality Check via SonarQube") {
            steps {
                script{
                    def scannerHome = tool 'sonarqube';
                    withSonarQubeEnv("SonarQube") {
                        sh "${tool("sonarqube")}/bin/sonar-scanner \
                        -Dsonar.projectKey=gs-maven \
                        -Dsonar.sources=. \
                        -Dsonar.css.node=. \
                        -Dsonar.host.url=http://192.168.56.10:9000 \
                        -Dsonar.login=992f76e8559c7d4b133a40ded7d396cc4d1ad003"
                    }
                }
            }
          }
          stage ('Build') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true install' 
                sh 'mvn test -e'
            } 
          }
          stage("Quality Gate") {
            steps {
                script {
                        while(true){
                            sh "sleep 2"
                            def url="http://192.168.56.10:80/job/${env.JOB_NAME.replaceAll('/','/job/')}/lastBuild/consoleText";
                            def sonarId = sh script: "wget -qO- --content-on-error --no-proxy --auth-no-challenge --http-user=${CRED_USR} --http-password=${CRED_PSW} '${url}'  | grep 'More about the report processing' | head -n1 ",returnStdout:true
                            sonarId = sonarId.substring(sonarId.indexOf("=")+1)
                            echo "sonarId ${sonarId}"
                            def sonarUrl = "http://192.168.56.10:80/sonarqube-webhook/api/ce/task?id=${sonarId}"
                            def sonarStatus = sh script: "wget -qO- '${sonarUrl}' --no-proxy --content-on-error | jq -r '.task' | jq -r '.status' ",returnStdout:true
                            echo "Sonar status ... ${sonarStatus}"
                            if(sonarStatus.trim() == "SUCCESS"){
                                echo "BREAK";
                                break;
                            }
                            if(sonarStatus.trim() == "FAILED "){
                                echo "FAILED"
                                currentBuild.result = 'FAILED'
                                break;
                            }
                        }
                    }
                }
            }
          
    }
    
}