def call(){

pipeline {
    agent any

    parameters { 
        choice(name: 'HERRAMIENTA', choices: ['gradle', 'maven'], description: '') 
        string(name: 'stage', defaultValue: '', description: '' )
        }

    stages {
        stage('Pipeline') {
            steps {
                script {

                    env.JENKINS_STAGE = ''
                    env.ERROR_MESSAGE =''

                    if (params.HERRAMIENTA == 'gradle') {
                        //se definen los stages validos para gradle
                        def valid_stages_gradle = ["build","test","sonar","run","rest","nexus"]
                        //se separan los stages por punto y coma
                        def stagesLowercase = params.stage.tokenize(";").collect{ it.toLowerCase() }
                        //se pasan los stages ingresados a minusculas
                        //def stagesLowercase = stagesList.collect{ it.toLowerCase() }

                        for (String item : stagesLowercase) {
                            if (!valid_stages_gradle.contains(item)) {
                                env.ERROR_MESSAGE = "El stage ${item} no es valido para proyecto gradle"
                                error(env.ERROR_MESSAGE)
                            }
                        }

                        //si no se le pasa stages, se corren todos
                        if (stagesLowercase.size() == 0) {
                            gradle.call('all')
                        //si solo se pasa 1, ese debe correr
                        } else if (stagesLowercase.size() == 1) {
                            gradle.call(stagesLowercase.get(0))          
                        //si se le pasa varios, se ejecutan secuencial y en orden coherente
                        } else {
                            //si build está en la lista todo OK hasta RUN
                            //si build no está en la lista problemas !
                            if (stagesLowercase.contains("build") || stagesLowercase.contains("test")) {
                                //si NO contiene RUN, pero si rest o nexus
                                if (!stagesLowercase.cointains("run") && 
                                    ( stagesLowercase.cointains("rest") || stagesLowercase.cointains("nexus") )) {
                                    env.ERROR_MESSAGE = "Es necesario ejecutar el stage run si se quiere correr rest o nexus"
                                    error(env.ERROR_MESSAGE)
                                }
                                //se ejecutan en orden, se toman los validos, se chequea que existan y se ejecutan
                                for (String item : valid_stages_gradle) {

                                    if (item.contains(stagesLowercase)) {
                                        gradle.call(item)
                                    }
                                    //item.contains(stagesLowercase) ? gradle.call(item) : continue
                                }
                            }else {
                                env.ERROR_MESSAGE = "Es necesario ejecutar el stage build & test"
                                error(env.ERROR_MESSAGE)
                            }
                        }
                    }

                    if (params.HERRAMIENTA == 'maven') {
                        // if (params.stage == '') {
                        //         maven.call()
                        // }else {
                        //     def valid_stages_maven = ["build","test","jar","sonarqube","upload_nexus"]
                        //     def stagesList = params.stage.tokenize(";")
                        //     def stagesLowercase = stagesList.collect{ it.toLowerCase() }

                        //     if (valid_stages_maven.containsAll(stage)) {
                                
                        //     }else {
                        //         println("No existe uno de los stage ingresados en el proyecto maven")
                        //     }

                        //     //Build
                        //     //Test
                        //     //Jar
                        //     //SonarQube
                        //     //Upload Nexus

                        // }
                    }

                    // if (params.buildtool == 'gradle') {
                    //     gradle.call()
                    // }else {
                    //     maven.call()
                    // }

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
            script {
                if (env.JENKINS_STAGE == '[]') {
                    echo 'ENVIANDO MENSAJE SLACK '+"Build Failure: [Gerardo Felmer][${env.JOB_NAME}]["+params.HERRAMIENTA+"] Ejecución Fallida [${env.ERROR_MESSAGE}]"
                    slackSend color: "danger", message: "Build Failure: [Gerardo Felmer][${env.JOB_NAME}]["+params.HERRAMIENTA+"] Ejecución Fallida [${env.ERROR_MESSAGE}]", 
                    teamDomain: 'devops-usach-2020', tokenCredentialId: 'slack-credentials'
                }else {
                    echo 'ENVIANDO MENSAJE SLACK '+"Build Failure: [Gerardo Felmer][${env.JOB_NAME}]["+params.HERRAMIENTA+"] Ejecución Fallida en stage [${env.JENKINS_STAGE}]"
                    slackSend color: "danger", message: "Build Failure: [Gerardo Felmer][${env.JOB_NAME}]["+params.HERRAMIENTA+"] Ejecución Fallida en stage [${env.JENKINS_STAGE}]", 
                    teamDomain: 'devops-usach-2020', tokenCredentialId: 'slack-credentials'
                }
            }
        }
    }
}

}

return this;