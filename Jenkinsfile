#!groovy
import java.lang.String


node() {
        stage('run') {
			cleanWs()		
			git branch: 'branch-6', url: 'https://github.com/dachyut/multibranch-1'
            println "Running run stage"            
			def buildit = load 'testempty.groovy'
            println "Calling func"
            def n = new buildit()
			n.execprint()
            //def m = buildit.execprint(name: "master")
			//print "$m"
            
        }
}
