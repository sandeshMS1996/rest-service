pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "m3"
    }

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/sandeshMS1996/rest-service.git'

                // Run Maven on a Unix agent.
                sh "mvn clean install -DskipTests"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    archiveArtifacts 'target/*.jar'
                // target - /var/lib/jenkins/workspace/test-pipeline/target
                //sh 'scp -i /home/ec2-user/ProdServer.pem target/*.jar ec2-user@ec2-3-137-201-53.us-east-2.compute.amazonaws.com:/home/ec2-user'
                sh 'pwd'
                sh 'who'
                sh 'scp -v -o StrictHostKeyChecking=no -i /var/lib/jenkins/secrets/server.pem target/*.jar ubuntu@ec2-13-59-12-83.us-east-2.compute.amazonaws.com:/home/ubuntu'

                sh 'ssh -i /var/lib/jenkins/secrets/server.pem ubuntu@ec2-13-59-12-83.us-east-2.compute.amazonaws.com ./start.sh'
            }
            }

        }
    }
}
