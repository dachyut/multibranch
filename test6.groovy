#!groovy
//import java.lang.String
//import io.jenkins.plugins.analysis.core.*
//import io.jenkins.plugins.analysis.core.steps.*
import io.jenkins.plugins.analysis.core.model.*

class absClass extends Tool { 
    String id
    // def absClass() {
    //     super()
    // }
    public void setId(String name) {
        id = name
    }

    public String getId() {
        script.println("ID:------ ${id}")
        return id
    }
}

class MyClass {
    Script script;
    
    absClass ob = new absClass()
    
    def exec() {
        script.println "Inside test6 exec method"
        final String buildLog = 'build.log, **/build.log'

        ob.id = "123"
        ob.setID("1234")
        script.println(ob.getID())
            
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