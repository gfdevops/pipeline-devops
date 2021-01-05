def call(String stagename = '') {
    if (stagename == 'build' || stagename == 'all') {
        stage('build') {
            env.JENKINS_STAGE = env.STAGE_NAME
            sh 'mvn clean compile -e'
        }
    }
    if (stagename == 'test' || stagename == 'all') {
        stage('test') {
            env.JENKINS_STAGE = env.STAGE_NAME
            sh 'mvn clean test -e'
        }
    }
    if (stagename == 'jar' || stagename == 'all') {
        stage('jar') {
            env.JENKINS_STAGE = env.STAGE_NAME
            sh 'mvn clean package -e'
        }
    }
    if (stagename == 'sonarqube' || stagename == 'all') {
        stage('sonarqube') {
            env.JENKINS_STAGE = env.STAGE_NAME
            withSonarQubeEnv(installationName: 'sonar-server') {
                sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
            }
        }
    }
    if (stagename == 'upload_nexus' || stagename == 'all') {
        stage('upload_nexus') {
            env.JENKINS_STAGE = env.STAGE_NAME
            nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-repo', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: 'build/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.1']]]
        }
    }
}

return this