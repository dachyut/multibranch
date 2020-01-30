#!groovy

import java.lang.String
import testempty.groovy

node() {
        stage('run') {
			cleanWs()		
			git branch: 'branch-6', url: 'https://github.com/dachyut/multibranch-1'
            println "Running run stage"            
        }
}
