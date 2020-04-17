#!groovy
import java.lang.String

def exec() {
	println("inside exec")
    withCredentials([string(credentialsId: 'jira_passws', variable: 'AZURE_SERVICE_PRINCIPAL_KEY')]) {
            println "Hello !!!!!!!!!!!!!!!!!"
            println "${AZURE_SERVICE_PRINCIPAL_KEY}"
        }
}
return this
