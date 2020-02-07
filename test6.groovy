#!groovy
import java.lang.String
import io.jenkins.plugins.analysis.core.*
import io.jenkins.plugins.analysis.core.steps.*
import io.jenkins.plugins.analysis.core.model.*

class MyClass extends Tools {
    Script script;
    String id

    def MyClass() {
        super()
    }

    public void setId(String name) {
        id = name
    }

    public void getId() {
        script.println("ID:------ ${id}")
    }

    def exec() {
        script.println "Inside test6 exec method"
        final String buildLog = 'build.log, **/build.log'
        
        def t = setId("125")

        s = t.getID()
        script.println "${s}"

        //recordIssues(tools: [msBuild(pattern: buildLog)])

        //def t = steps.RecordIssuesStep.setTools('msbuild')
        //script.println "${t}"
        //script.recordIssues (tool : "msbuild")
        //script.recordIssues(enabledForFailure: true, aggregatingResults: true,
        //    tools: t)
        //.msBuild(pattern: buildLog)
        //script.recordIssues(tools: msbld)
    }
}

return new MyClass(script:this)