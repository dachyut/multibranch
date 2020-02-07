#!groovy
import java.lang.String


class MyClass {
    Script script;

    def exec() {
        script.println "Inside test6 exec method"
        final String buildLog = 'build.log, **/build.log'
        
        //recordIssues(tools: [msBuild(pattern: buildLog)])

        script.recordIssues(
            tools: msbuild,
            pattern: build.log
        ) //.msBuild(pattern: buildLog)
        //script.recordIssues(tools: msbld)
    }
}

return new MyClass(script:this)