#!groovy
import java.lang.String


node() {
        stage('run') {
			cleanWs()		
			git branch: 'branch-6', url: 'https://github.com/dachyut/multibranch-1'
            println "Loading groovy file"            
			def buildit = load 'testempty.groovy'
			
            println "Setting parameters"            
			buildit.branchOrCommit='CEB-5555'
			buildit.buildType='Nightly'            
			buildit.pushArtifactsToAzure=false
						
			println "Calling Class.Method"
			def result = buildit.execprint()
			print "$result"            
        }
}
