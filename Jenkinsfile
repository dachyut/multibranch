final String BuildPropertiesFile = 'build.properties'
	
final String lsbCommitId

String targetBranch = 'branch-5' 

final String buildNumber = 'Last Successful Build'

//def String buildno = copyArtifacts(projectName: 'sourceproject')

properties([
  parameters([
    [$class: 'BuildSelectorParameter', defaultSelector: lastSuccessful(), description: 'Last Successful build', name: 'LAST_SUCCESSFUL_BUILD'],
	booleanParam (name: 'RUN_BUILD', defaultValue: true,
                description: "Run build."),
	booleanParam (name: 'DEPLOY_VAULT_AND_RUN_REGRESSION_TEST', defaultValue: true,
                description: "Run test."),
	string (name: 'BUILD_NUMBER_TO_USE', defaultValue: 'Last Successful build',
                description: 'Build number to use if Build stage is skipped. Default uses Last successful build')   
  ])
])

node {       

	println "In node. BuildNumber: ${buildNumber}"
	printLastSuccessfulBuild()
	println "*********************"
	
	if(params.RUN_BUILD) {
		stage ('Build') {		
			cleanWs()		
			git branch: 'branch-5', url: 'https://github.com/dachyut/multibranch-1'
			
			Random random = new Random()
			ranStr = "RandomStr-" + random.nextInt(10000)
			bat "echo BRANCH=${env.BRANCH_NAME} > build.properties"	
			bat "echo RandomString=$ranStr >> build.properties"
			bat "echo BuildNumber=${env.BUILD_NUMBER} >> build.properties"
			
			archiveArtifacts artifacts: 'build.properties', fingerprint: true			
			
			
			println "Build stage completed"		
		}	
	}
	else {
		println "***********************"			
		getCIBuildNew('branch-5',BuildPropertiesFile,params.BUILD_NUMBER_TO_USE)
		
		//copyArtifacts filter: "${BuildPropertiesFile}", fingerprintArtifacts: true, flatten: //true, projectName: 'branch-5', selector: buildParameter('LAST_SUCCESSFUL_BUILD') 
		
		//println params.LAST_SUCCESSFUL_BUILD
		println "***********************"
		archiveArtifacts artifacts: 'build.properties', fingerprint: true	
	}
		
	if(params.DEPLOY_VAULT_AND_RUN_REGRESSION_TEST) {   	
		stage ('Test') {
			
			//getCIBuild("branch-5",BuildPropertiesFile)
			//error ("Kill this stage")
			println "Test stage completed"
		}
	}
	
	stage ('Misc') {
		def changedFiles = getChangedFiles("origin/${targetBranch}", 'HEAD')
		def length = changedFiles.size()
		println "Changes length: ${length}"
		
		for (int ii = 0; ii < changedFiles.size(); ii++) {
			println changedFiles[ii]
		}
		println "***********"
	}
}


Boolean printLastSuccessfulBuild() {
	println "In Function. BuildNumber: ${buildNumber}"
}

Boolean getCIBuild(targetBranch,BuildPropertiesFile) {
    final String commitKey = 'COMMIT'
    final String artifactKey = 'DCPROTECT_MAC_INSTALLER'
    final String targetCIJob =  '//MultiBranchPipelien2/' + targetBranch

    try {
        step([$class: 'CopyArtifact',
			filter: "${BuildPropertiesFile}",
            fingerprintArtifacts: true,
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

Boolean getCIBuildNew(targetBranch,BuildPropertiesFile,buildToUse) {
    final String commitKey = 'COMMIT'
    final String artifactKey = 'DCPROTECT_MAC_INSTALLER'
    final String targetCIJob =  '//MultiBranchPipelien2/' + targetBranch	
	
    try {
		if (buildToUse == "Last Successful build") {
			step([$class: 'CopyArtifact',
			filter: "${BuildPropertiesFile}",
            fingerprintArtifacts: true,
            flatten: true,
            selector: lastSuccessful(),
            projectName: targetCIJob])
		}
        else {
			step([$class: 'CopyArtifact',
			filter: "${BuildPropertiesFile}",
            fingerprintArtifacts: true,
            flatten: true,
            selector: specific(buildToUse),
            projectName: targetCIJob])
		}
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
