#!groovy
import java.lang.String


class MyClass {
    Script script;

    def exec() {
        script.println "Inside test6 exec method"
        final String buildLog = 'build.log, **/build.log'
        
        script.recordIssues(tools: [script.recordIssues.msBuild(pattern: buildLog)])
    }
}

return new MyClass(script:this)