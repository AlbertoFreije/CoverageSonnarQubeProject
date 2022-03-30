pipeline {
    agent any
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
                    timeout(1){
                        def qg = waitForQualityGate("SonarQube")
                        if(qg.status != 'OK'){
                            error "No se pudo pasar la verificación del umbral de calidad del código de Sonarqube, ¡modifíquelo a tiempo! error: $ {qg.status}"
                        }
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
    }
    
}