//Validar el tipo de rama a ejecutar (feature, develop o release)
def getBranchName() {
    // def scmVars = checkout scm
    // String branch = scmVars.GIT_BRANCH
    return env.GIT_BRANCH

}

def isBranchName(String branchName) {
    if (env.GIT_BRANCH == env.GIT_BRANCH) {
        return true;
    }
    return false;

}

//Validar formato de nombre de rama release según patrón
//release-v{major}-{minor}-{patch}
def validateReleaseNameFormat(String releaseName) {
    //regex pattern
    // ^(release-v[0-9]+)\-([0-9]+)\-([0-9]+)?$
    //para formato release-v1-0-0
    
    if (releaseName =~ /^(release-v[0-9]+)\-([0-9]+)\-([0-9]+)?$/) { // false
        return true;
    } 

    return false;
}