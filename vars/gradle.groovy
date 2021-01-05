
def call(String stagename = 'all') {

    if (stagename == 'build' || stagename == 'test' || stagename == 'all') {
        stage('build & test') {
            env.JENKINS_STAGE = env.STAGE_NAME
            sh './gradlew clean build'
        }
    }

    if (stagename == 'sonar' || stagename == 'all') {
        stage('sonar') {
            env.JENKINS_STAGE = env.STAGE_NAME
            def scannerHome = tool 'sonar-scanner'
            withSonarQubeEnv('sonar-server') { // If you have configured more than one global server connection, you can specify its name
                sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-gradle -Dsonar.java.binaries=build"
            }
        }
    }
    if (stagename == 'run' || stagename == 'all') {
        stage('run') {
            env.JENKINS_STAGE = env.STAGE_NAME
            sh 'bash gradlew bootRun &'
            sleep 20
        }
    }
    if (stagename == 'rest' || stagename == 'all') {
        stage('rest') {
            env.JENKINS_STAGE = env.STAGE_NAME
            sh "curl -X GET 'http://localhost:8085/rest/mscovid/test?msg=testing'"
        }
    }
    if (stagename == 'nexus' || stagename == 'all') {
        stage('nexus') {
            env.JENKINS_STAGE = env.STAGE_NAME
            nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-repo',
                            packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar',
                            filePath: 'build/libs/DevOpsUsach2020-0.0.1.jar']],
                            mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'DevOpsUsach2020', version: '1.0.0']]]
        }
    }
}

return this
