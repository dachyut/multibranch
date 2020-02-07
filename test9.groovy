import hudson.plugins.copyartifact.BuildSelector
import java.lang.Object
import hudson.model.*
import hudson.*

def exec() {
    println "In test9.groovy"
    final String buildLog = 'd:\\Build.log'
    //warnings parserConfigurations: [[parserName: 'MSBuild', pattern: buildLog]] //- deprecated
    recordIssues(tools: [msBuild(pattern: buildLog)])
}

return this