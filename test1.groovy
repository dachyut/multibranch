#!groovy
import java.lang.String

/***********************
 Jenkins pipeline stage to build the product
 ************************/
def exec(String branchOrCommit='code',
         String buildType='FAST') {

    println "inside exec"
    final String buildSubJob = 'job1'
    final String failedBuildArtifacts = 'Build.log, Failure.txt, **/Failure*.txt'
    final String buildArtifacts = 'Build.log, Failure.txt, build.properties, build_times.csv, git_commits.log, **/TEST*.xml, **/ui/coverage/cobertura-coverage.xml, **/junit-results.xml, **/TestResults/*.trx'
    final String buildLog = 'Build.log'

    def buildResult = build job: buildSubJob,
            propagate: false,
            parameters: [
                [$class: 'StringParameterValue', name: 'name', value: buildLabel],
                [$class: 'BooleanParameterValue', name: 'build', value: isNotarized]                
            ]
    println "Result: ${buildResult.result}"
}
return this
