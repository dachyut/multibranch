#!groovy
import java.lang.String

class MyClass {
    Script script;       
    
    def exec() {
        script.println "Inside test15 exec method"
        String AZURE_SERVICE_PRINCIPAL_KEY = 'sss'
        script.withCredentials([script.string(credentialsId: 'jira_passws', variable: 'AZURE_SERVICE_PRINCIPAL_KEY')]) {
            script.println "Hello !!!!!!!!!!!!!!!!!"
            
            script.println "${AZURE_SERVICE_PRINCIPAL_KEY}"
        }
    }
}

return new MyClass(script:this)