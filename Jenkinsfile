#!groovy
import java.lang.String


node() {
        stage('run') {
			cleanWs()		
			git branch: 'branch-6', url: 'https://github.com/dachyut/multibranch-1'
            println "Loading groovy file"            

			//Run sub-job
			def buildit = load 'testempty.groovy'			
			//buildit.exec()

			println "calling demo.a()"
			def result = buildit.a()

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
            println "$result"            
        }
}
