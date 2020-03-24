#!groovy
import java.lang.String

class ClassA {
    Script script
    
    def exec() {
        // Given a slash-delimited string, such as Jenkins env.JOB_NAME,
        // reformat to comma-delimited string for CSV output
        script.println "In exec() method...."

        script.node {
            ws(
                script.println "Inside ws.."    
            )
            script.println "Inside node.."
        }
    }
}

return new ClassA(script:this)
