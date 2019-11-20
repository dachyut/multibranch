final String BuildPropertiesFile = 'build.properties'
	
final String lsbCommitId

properties([
    parameters([
            booleanParam (name: 'RUN_BUILD', defaultValue: true,
                description: 'If true, run Build stage.'),
            booleanParam (name: 'DEPLOY_VAULT_AND_RUN_REGRESSION_TEST', defaultValue: true,
                description: 'If true, run Deploy Valut and Smoke, Regression tests.')
    ]),
    disableConcurrentBuilds()
])



node {       
	
	if(params.RUN_BUILD) {
		stage ('Build') {		
			cleanWs()		
			git branch: 'branch-4', url: 'https://github.com/dachyut/multibranch-1'
			
			bat "echo BRANCH=${env.BRANCH_NAME} > build.properties"		
			archiveArtifacts artifacts: 'build.properties', fingerprint: true
			println "Build stage completed"		
		}	
	}
		
	if(params.DEPLOY_VAULT_AND_RUN_REGRESSION_TEST) {   	
		stage ('Test') {
			
			getCIBuild("branch-5")
			//error ("Kill this stage")
			println "Test stage completed"
		}
	}
}

Boolean getCIBuild(targetBranch) {
    final String commitKey = 'COMMIT'
    final String artifactKey = 'DCPROTECT_MAC_INSTALLER'
    final String targetCIJob =  '//MultiBranchPipelien2/' + targetBranch

    try {
        step([$class: 'CopyArtifact',
			filter: "${buildPropertiesFile}",
            fingerprintArtifacts: true,
			filter: "pom.xml",
            flatten: true,
            selector: lastSuccessful(),
            projectName: targetCIJob])
    } catch (Exception e) {
        println "Could not find last successful build properties for job:  ${targetCIJob}"
        println e
        return false
    }    
    return true
}

def getCommitHash (branchName) {
    sh "git rev-parse ${branchName} >.git/commit_id"
    String myCommit = readFile('.git/commit_id').replaceAll('\\W', '')
    return myCommit
}

@NonCPS
def getLastSuccessfulCommitId(build) {
	def lastSuccBuild
    def changeSets
    def lastSuccCommitId   
    currBuild = build
	println "====> Current build: ${currBuild}"
	println currBuild.getClass()
    while (currBuild?.getPreviousBuild()?.result !=null) {
        currBuild = currBuild.getPreviousBuild()
        if(currBuild.result == 'SUCCESS' && (currBuild.changeSets).size() > 0) {
            lastSuccBuild = currBuild
			println "Last successful Build_Current number: ${lastSuccBuild.number} Build Result: ${lastSuccBuild.result}"
            break
        }
    }
    
	println "Looking for successful build in Target branch ${env.CHANGE_TARGET}"
	//def buildNumber = Jenkins.instance.getItem(env.CHANGE_TARGET)
	def build_job = build job: "${env.CHANGE_TARGET}"
	println build_job.getClass()
	//build_job_number = build_job.getNumber()
	//println build_job_number
	/***currBuild = env.CHANGE_TARGET
	while (currBuild?.getPreviousBuild()?.result !=null) {
		currBuild = currBuild.getPreviousBuild()
		if(currBuild.result == 'SUCCESS' && (currBuild.changeSets).size() > 0) {
			lastSuccBuild = currBuild
			println "Last successful Build_Target number: ${lastSuccBuild.number} Build Result: ${lastSuccBuild.result}"
			break
		}
	}****/
}

def getChangedFiles (firstCommit, secondCommit) {
    final String changedFilesList = './changed_files.txt'
    def myStatus = sh(returnStatus: true, script: "git diff --name-only ${firstCommit} ${secondCommit} > ${changedFilesList}")
    if (myStatus != 0) {
        println "Git failed getting the list of changed files."
        return null
    }
    sh "wc -l ${changedFilesList}"
    sh "cat ${changedFilesList}"
    return readFile(changedFilesList).split("\n")
}

Boolean isOnlyAutomation(changedFiles) {

    // If you can't determine, then the safest approach is to say no
    if (!changedFiles) {
        println "Warning: No list of changed files."
		println "###### Non-Automation changes, Build Product #######"
        return false
    }

    for (int ii = 0; ii < changedFiles.size(); ii++) {
		println "11111Found changed file <${changedFiles[ii]}>."
        if (false == changedFiles[ii].startsWith("src")) {
            println "Found diffs with non-automation file <${changedFiles[ii]}>."
			println "###### Non-Automation changes, Build Product #######"
            return false
        }
    }

    for (int ii = 0; ii < changedFiles.size(); ii++) {
		println "22222Found changed file <${changedFiles[ii]}>."
        if (changedFiles[ii].toLowerCase().contains('Jenkinsfile')) {
            println "Found diffs with build script <${changedFiles[ii]}>."
			println "###### Non-Automation changes, Build Product #######"
            return false
        }
    }
	println "###### Automation changes, Copy from LSB #######"
    return true
}
//
//commit1
//commits in branch11-1
//
//
//
//
//
//
//
//
