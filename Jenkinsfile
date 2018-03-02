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
    }
}
