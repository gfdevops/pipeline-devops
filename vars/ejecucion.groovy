def call(){

pipeline {
    agent any

    parameters { 
        choice(name: 'HERRAMIENTA', choices: ['gradle', 'maven'], description: '') 
        }

    stages {
        stage('Pipeline') {
            steps {
                script {
                    echo 'EJECUTANDO => '+params.HERRAMIENTA

                    env.JENKINS_STAGE = ''

                    // def ejecucion

                    if (params.buildtool == 'gradle') {
                        gradle.call()
                    }else {
                        maven.call()
                    }

                    // if (params.HERRAMIENTA == 'gradle') {
                    //     ejecucion = load 'gradle.groovy'
                    // }else  {
                    //     ejecucion = load 'maven.groovy'
                    // }
                    // ejecucion.call()
                }
            }
        }
    }

    post {
        success {
            echo 'ENVIANDO MENSAJE SLACK '+"Build Success: [Gerardo Felmer][${env.JOB_NAME}]["+params.HERRAMIENTA+"] Ejecución Exitosa"
            slackSend color: "good", message: "Build Success: [Gerardo Felmer][${env.JOB_NAME}]["+params.HERRAMIENTA+"] Ejecución Exitosa", 
            teamDomain: 'devops-usach-2020', tokenCredentialId: 'slack-credentials'
        }
        failure {
            echo 'ENVIANDO MENSAJE SLACK '+"Build Success: [Gerardo Felmer][${env.JOB_NAME}]["+params.HERRAMIENTA+"] Ejecución Fallida en stage [${env.JENKINS_STAGE}]"
            slackSend color: "danger", message: "Build Success: [Gerardo Felmer][${env.JOB_NAME}]["+params.HERRAMIENTA+"] Ejecución Fallida en stage [${env.JENKINS_STAGE}]", 
            teamDomain: 'devops-usach-2020', tokenCredentialId: 'slack-credentials'
        }
    }
}

}

return this;