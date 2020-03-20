#!groovy
import java.lang.String

class BuildTimer {
    Script script
    String jobScopes = ''
    String buildNumber = ''
    String jobName = ''    

    def printJobScopes() {
        // Given a slash-delimited string, such as Jenkins env.JOB_NAME,
        // reformat to comma-delimited string for CSV output
        script.println "In printJobScopes()"  
        script.stash includes: "buildProperties", name: 'properties'          
    }

    def recordHeaders() {
        script.println "In recordHeaders()"

    }

    // void finalize() { 
    //     println "Destroyed...." 
    // } 
    
}

return new BuildTimer(script:this)
