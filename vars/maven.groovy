def call(String stagename = '') {
    if (stagename == 'build' || stagename == 'all') {
        stage('build') {
            env.JENKINS_STAGE = env.STAGE_NAME
            echo 'STAGE BUILD'
            // sh 'mvn clean compile -e'
        }
    }
    if (stagename == 'test' || stagename == 'all') {
        stage('test') {
            env.JENKINS_STAGE = env.STAGE_NAME
            echo 'STAGE TEST'
            // sh 'mvn clean test -e'
        }
    }
    if (stagename == 'jar' || stagename == 'all') {
        stage('jar') {
            env.JENKINS_STAGE = env.STAGE_NAME
            echo 'STAGE JAR'
            // sh 'mvn clean package -e'
        }
    }
    if (stagename == 'sonarqube' || stagename == 'all') {
        stage('sonarqube') {
            env.JENKINS_STAGE = env.STAGE_NAME
            echo 'STAGE SONARQUBE'
            // withSonarQubeEnv(installationName: 'sonar-server') {
            //     sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
            // }
        }
    }
    if (stagename == 'nexusci' || stagename == 'all') {
        stage('nexusci') {
            env.JENKINS_STAGE = env.STAGE_NAME
             echo 'STAGE NEXUS CI'

            // nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-repo', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: 'build/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.1']]]
        }
    }

    if (stagename == 'downloadnexus' || stagename == 'all') {
        stage('downloadnexus') {
            env.JENKINS_STAGE = env.STAGE_NAME
             echo 'STAGE DOWNLOAD NEXUS'

            // nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-repo', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: 'build/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.1']]]
        }
    }

    if (stagename == 'rundownloadedjar' || stagename == 'all') {
        stage('rundownloadedjar') {
            env.JENKINS_STAGE = env.STAGE_NAME
             echo 'STAGE RUNDOWNLOAD JAR'

            // nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-repo', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: 'build/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.1']]]
        }
    }    

    if (stagename == 'nexuscd' || stagename == 'all') {
        stage('nexuscd') {
            env.JENKINS_STAGE = env.STAGE_NAME
            echo 'STAGE NEXUS CD'
        }
    }
}

return this