#!groovy
import java.lang.String


node() {
        stage('run') {
			cleanWs()		
			git branch: 'branch-6', url: 'https://github.com/dachyut/multibranch-1'
            println "Loading groovy Class file"          
			
			// Daniel hash map code
			def buildit = load 'test4.groovy'
			
			//buildit.myMapExec(branchOrCommit: 'Wow!')
			//buildit.myMapExec(buildScope: 'Super!')
			//buildit.myMapExec(isNotarized: false)
			buildit.myMapExec(buildScope: 'YOWZAA', pushArtifactsToAzure: false, buildType: 'Yeeeee-HAW!')
			//buildit.myMapExec(notReallyAKey: 77)   // Validate keys

			/* Working Code .........
			// def buildit = load 'test2.groovy'			
			// println "Setting class varibales"
			// buildit.branchOrCommit = 'myCode'
			// buildit.pushArtifactsToAzure = false
			// println "Executing method inside class"
			// buildit.exec()
			//buildit.exec("myCode", "params.BUILD_TYPE", "buildLevel", "params.BUILD_LABEL", true, false, "skipComponents", "clientDownloadLocation")  
			//Working Code */


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
        }
}
