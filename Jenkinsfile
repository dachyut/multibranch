final String BuildPropertiesFile = 'build.properties'
	
final String lsbCommitId


node {       
	
	stage ('Build') {		
		cleanWs()
		
		git branch: 'branch-1', url: 'https://github.com/dachyut/multibranch-1'
		
		echo "******** Environemental variables ********"
		echo "BRANCH_NAME: ${env.BRANCH_NAME}"
		echo "CHANGE_TARGET: ${env.CHANGE_TARGET}"
		echo "CHANGE_BRANCH: ${env.CHANGE_BRANCH}"
		echo "JOB_BASE_NAME: ${env.JOB_BASE_NAME}"		
		println "${JENKINS_URL}"		
					
		sh "git rev-parse HEAD >./commit_id"
		String myCommit = readFile('./commit_id').replaceAll('\\W', '')
		println myCommit
		
		Random random = new Random()
		ranStr = "RandomStr-" + random.nextInt(10000)
		
		echo "Random number: $ranStr"
		
		bat "echo BRANCH=${env.BRANCH_NAME} > build.properties"
		bat "echo COMMIT=$myCommit >> build.properties"		
		bat "echo DCPROTECT_MAC_INSTALLER=http://artifacts.carb.lab/EndpointMidmarket/Shared/Midmarket-New-Build_Hermes/PR >> build.properties"
		bat "echo RandomString=$ranStr >> build.properties"
		bat "echo BuildNumber=${env.BUILD_NUMBER} >> build.properties"		
		
		archiveArtifacts artifacts: 'build.properties', fingerprint: true
		
		
		
		println "****************************************************"		
	
		println "1>>>>>>>>>>>>>>>>>> Checking LSB in current tree - PR"
		skipBuild = getCIBuild(env.BRANCH_NAME,BuildPropertiesFile,env.CHANGE_BRANCH)
		println "==============>${env.BRANCH_NAME} build: ${skipBuild}" //skipBuild=False - requires product build
		println "--------${env.BRANCH_NAME} prop file:"
		sh "cat ${BuildPropertiesFile}"				
		if(!skipBuild) {
			println ">>>>>>Either it is first PR build or No LSB commit or no LSB artifacts or there are non-automation changes"
			println "2>>>>>>>>>>>>>>>>>> Checking LSB in parent tree - CI"
			skipBuild = getCIBuild(env.CHANGE_BRANCH,BuildPropertiesFile,'HEAD')
			println "==============>${env.CHANGE_BRANCH} build: ${skipBuild}"
			println "--------${env.CHANGE_BRANCH} prop file:"
			sh "cat ${BuildPropertiesFile}"				
			if(!skipBuild) {
				println ">>>>>>Build requires product build"
			}
		}
		
		if (skipBuild) {
			println ">>>>>>Build does not requires product build -Automation changes only"
			println "Using lastSuccessfulBuild"
			archiveArtifacts artifacts:  BuildPropertiesFile, fingerprint: true
		}
		
		/*if (!skipBuild) {
			println "2>>>>>>>>>>>>>>>>>> Checking LSB in parent tree - CI"
			skipBuild = getCIBuild(env.CHANGE_BRANCH,BuildPropertiesFile,'HEAD')
			println "==============>${env.CHANGE_BRANCH} build: ${skipBuild}"
			println "--------${env.CHANGE_BRANCH} prop file:"
			sh "cat ${BuildPropertiesFile}"				
			if(!skipBuild) {
				println ">>>>>>Build requires product build"
			}
			else {
					println ">>>>>>Build does not requires product build" 
			}	
		}*/
			
		println "****************************************************"
	}			
}

Boolean getCIBuild(targetBranch, buildPropertiesFile,sourceBranch) {
    final String commitKey = 'COMMIT'
    final String artifactKey = 'DCPROTECT_MAC_INSTALLER'
    final String targetCIJob =  '//MultiBranchPipelien2/' + targetBranch

    try {
        step([$class: 'CopyArtifact',
            filter: "${buildPropertiesFile}",
            fingerprintArtifacts: true,
            flatten: true,
            selector: lastSuccessful(),
            projectName: targetCIJob])
    } catch (Exception e) {
        println "Could not find last successful build properties for job:  ${targetCIJob}"
        println e
        return false
    }

    def buildProps = readProperties file:buildPropertiesFile
	
	println ">>>>>>>>>>> LSB commit ID:- ${buildProps[commitKey]}"
	lsbCommitId = buildProps[commitKey]
	
    if (!buildProps.containsKey(commitKey)) {
        println "Could not find what commit was used in the last successful CI build of target ${targetCIJob}."
        return false
    }

    // Verify existence of the artifacts download location
    if (!buildProps.containsKey(artifactKey)) {
        println "Could not find the artifacts location for the last successful CI build of target ${targetCIJob}."
        return false
    }
    String artifactsCmd = 'curl --head --fail ' + buildProps[artifactKey]
    def myStatus = sh(returnStatus: true, script: artifactsCmd)
    if (myStatus != 0) {
        println "Artifacts location does not exist or is unreachable for the last successful CI build of target ${targetCIJob}: ${buildProps[artifactKey]}"
        return false
    }

	currentCommit = getCommitHash('HEAD')
	lsbCommit = buildProps[commitKey]
	println "*****************************"
	println "LSB commit id:- ${lsbCommit}"  //<--- this gives LSB commit id
	//println "getCommitHash[origin/${targetBranch}] = ${getCommitHash("origin/${targetBranch}")}"
	//println "getCommitHash[origin/${sourceBranch}] = ${getCommitHash("origin/${sourceBranch}")}"
	println "Current commit:- ${currentCommit}"
	println "*****************************"

    //if (buildProps[commitKey] == getCommitHash("origin/${targetBranch}")) {
	
	  if (lsbCommit == currentCommit) {
        println "The last successful CI build ${targetCIJob} is up to date."
    } else {
        //String[] changedFiles = getChangedFiles(buildProps[commitKey], "origin/${targetBranch}")
		String[] changedFiles = getChangedFiles(lsbCommit, currentCommit)
        if (!isOnlyAutomation(changedFiles)) {
            println "Target branch ${targetBranch} has non-automation commits not included in the last successful CI build ${targetCIJob}."
            return false
        }
    }
	
	println "**********"
    //sh "mv ${buildPropertiesFile} ${env.WORKSPACE}"
	println "**********"
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
