pipeline{
    agent any

    tools {
        maven 'Mymaven'
    }
    stages {
        stage('Build') {
            steps {
                bat "mvn clean install -DskipTests"
            }
        }
        stage('Test') {
            steps {
                script{
                    catchError(buildResult: 'UNSTABLE',stageResult: 'FAILURE'){
                        bat "mvn clean test -Pparallel"
                    }
                }

            }
        }
    }

}