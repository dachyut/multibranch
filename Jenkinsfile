#!groovy
import java.lang.String

properties([
    parameters([
            string (name: 'BUILD_LABEL', defaultValue: 'CEB-NewBuild',
                description: "Builds will run on this Jenkins label."),
            string (name: 'TEST_PLATFORM', defaultValue: 'CEB-Client-Automation-Win10-UI',
                description: 'Tests will run in parallel across this comma-delimited list of Jenkins labels.'),
            string (name: 'CLIENT_DOWNLOAD_LOCATION', defaultValue: '',
                description: 'Specify the CLIENT_DOWNLOAD_LOCATION path in feature acceptance suite file if you skip the client build'),            
    ])
])


node() {

		// stage('artifacts') {
		// 	Random random = new Random()
		// 	ranStr = "RandomStr-" + random.nextInt(10000)
		// 	bat "echo BRANCH=${env.BRANCH_NAME} > build.properties"	
		// 	bat "echo RandomString=$ranStr >> build.properties"
		// 	bat "echo BuildNumber=${env.BUILD_NUMBER} >> build.properties"
			
		// 	//	archiveArtifacts artifacts: 'build.properties', fingerprint: true
		// }

        stage('run') {
			//cleanWs()	
			// println "Files before..."
			// sh "ls -la"	
			git branch: 'branch-6', url: 'https://github.com/dachyut/multibranch-1'
            println "Loading groovy Class file"          

			def deployit = load 'test12.groovy'	
			println(deployit.getClass())
			println "Setting class varibales"
			deployit.jobName = 'mycode/mycommit'			                  
			deployit.printJobScopes()
			
			def deployit1 = load 'test12.groovy'			
			println "Setting class varibales"
			deployit1.jobName = 'mycode/mycommit'			                  
			deployit1.printJobScopes()

			//runAutomationStages('mycode', 'vm1', 'partner', params)			
			
			// def deployit = load 'test10.groovy'			
			// println "Setting class varibales"
			// String lockedPrivateCloudVaultVms = 'VM1, VM2'
			// deployit.branchOrCommit = 'mycode'
			// deployit.lockedResources = lockedPrivateCloudVaultVms                    
			// deployit.exec()

			// def buildit = load 'test9.groovy'				
			// println "Executing method inside class"
			// buildit.exec()	
			
			// def buildit = load 'test8.groovy'			
			// println "Setting class varibales"
			// buildit.branchOrCommit = 'myCode'
			// buildit.pushArtifactsToAzure = false									
			// println "Executing method inside class"
			// buildit.exec()
		
			// def buildit = load 'test7.groovy'				
			// println "Executing method inside class"
			// buildit.exec()	

			// Daniel hash map code
			//def buildit = load 'test4.groovy'
			
			//buildit.myMapExec(branchOrCommit: 'Wow!')
			//buildit.myMapExec(buildScope: 'Super!')
			//buildit.myMapExec(isNotarized: false)
			//buildit.myMapExec(buildScope: 'YOWZAA', pushArtifactsToAzure: false, buildType: 'Yeeeee-HAW!')
			//buildit.myMapExec(notReallyAKey: 77)   // Validate keys

			//* Working Code .........
			// def buildit = load 'test2.groovy'			
			// println "Setting class varibales"
			// buildit.branchOrCommit = 'myCode'
			// buildit.pushArtifactsToAzure = false
									
			// println "Executing method inside class"
			// buildit.exec()			
			//Working Code */

			//* Copyartifacts Code ......... WORKING CODE
			// def buildit = load 'test6.groovy'				
			// println "Executing method inside class"
			// buildit.exec()			
			// Code */

			//def out = new Binding()
			//def buildit = load 'build1.groovy'			
			//buildit.exec(manager.listener.logger)

			//Run sub-job
			//def buildit = load 'testempty.groovy'			
			//buildit.exec()

			//println "calling demo.a()"
			//def result = buildit.runnew()

			//This  works.....
			//def buildit = load 'buildnew.groovy'			
			//buildit.exec("myCode", "FAST", "CI", "CEB-1234", false, false, "skipComponents", "path")

			//This doesn't work
			//def buildit = load 'build.groovy'
			
            // Passing required parameters to class object
            // buildit.branchOrCommit='CEB-5555'
            // buildit.buildType='Nightly'            
            // buildit.pushArtifactsToAzure=false
                        
            // Calling Class.Method
            //def result = buildit.execprint()
            //println "$result"      

			println "----End -------"
			//sh "ls -la"	      
        }
}

def runAutomationStages (branchOrCommit, lockedResources, partnerName, params) {
    stage('Smoke Test') {
		println "Loading automation.groovy"
		def autoit = load 'test11.groovy'
		println "Setting class varibales"
		
		autoit.branchOrCommit = branchOrCommit
		
		autoit.exec()
	}
}