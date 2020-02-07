#!groovy
import java.lang.String


class MyClass {
    Script script;

    def exec() {
        script.println "Inside test6 exec method"
        final String buildLog = 'build.log, **/build.log'
        
        def msbld = script.msBuild(pattern: buildLog)
        script.recordIssues(tools: msbld)
    }
}

return new MyClass(script:this)