#!groovy

class BuildTimer {
    Script script;
    String jobScopes = ''
    String buildNumber = ''
    String jobName = ''    

    def printJobScopes() {
        // Given a slash-delimited string, such as Jenkins env.JOB_NAME,
        // reformat to comma-delimited string for CSV output
        String jobScopes = jobName.split('/').join(', ')
        println "\t${jobScopes}"
        this.jobScopes = jobScopes
    }

    def static recordHeaders() {
        Script.environment.sh "echo \"Hello ....."
    }

    void finalize() { 
        println "Destroyed...." 
    } 
    
}

return new BuildTimer(script:this)