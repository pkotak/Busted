pipeline {
   agent {
       docker {
           image 'maven:3-alpine'
           args '-v /root/.m2:/root/.m2'
       }
   }

   stages {
       stage('Build') {
           steps {
               echo "Building"
               sh 'mvn -f phaseC/cs5500-spring2018-team212 compile'
               sh 'mvn -f phaseC/cs5500-spring2018-team212 package'
           }
       }
       stage('Test'){
           steps {
               echo "Testing"
               sh 'mvn -f phaseC/cs5500-spring2018-team212 test'
           }
       }
	   stage('SonarQube') {
           steps {
               withSonarQubeEnv('SonarQube') {
                  sh 'mvn -f phaseC/cs5500-spring2018-team212 clean install'
                  sh 'mvn -f phaseC/cs5500-spring2018-team212 sonar:sonar'
               }
           }
       }
	   stage('Quality') {
          steps {
            sh 'sleep 30'
            timeout(time: 10, unit: 'SECONDS') {
               retry(5) {
               script {
				  def qg = waitForQualityGate()
                  if (qg.status != 'OK') {
                error "Pipeline aborted due to quality gate failure: ${qg.status}"
             }
            }
           }
          }
         }
        }
       
       stage('Deploy') {
            when { branch 'master' }
            steps {
                checkout scm
                echo 'Deploying...'
                withCredentials([file(credentialsId: 'PEM_FILE_ID', variable: 'PEM_PATH')]) {
                    sh 'chmod +x deploy.sh; bash deploy.sh'
                }
               }
              }
             }
}
