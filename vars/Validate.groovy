//Validar el tipo de rama a ejecutar (feature, develop o release)
def getBranchName() {
                    //     def scmVars = checkout scm
                    // String branch = scmVars.GIT_BRANCH
    println('getBranchName => '+env.GIT_BRANCH);
    return env.GIT_BRANCH

}

def isBranchName(String branchName) {
                    //     def scmVars = checkout scm
                    // String branch = scmVars.GIT_BRANCH
    println('isBranchName => '+env.GIT_BRANCH);

    if (env.GIT_BRANCH == env.GIT_BRANCH) {
        return true;
    }
    return false;

}

//Validar formato de nombre de rama release según patrón
//release-v{major}-{minor}-{patch}
def validateReleaseNameFormat() {

}