#!groovy
import java.lang.String
import java.lang.Object
import com.github.javaparser.*
import io.jenkins.plugins.analysis.core.model.*

class MyClass {
    Script script;
    String branchOrCommit='Default'	
    String buildType='FAST'
    String buildScope='CI'
    String buildLabel='Agent'
    Boolean pushArtifactsToAzure=true
    Boolean isNotarized=false
    String skipComponents='Default'
    String clientDownloadLocation='Default'
    String buildBrandIndex='0'
    Boolean checkmarxScan=false
    Boolean whitesourceScan=false

    def exec() {
        script.println("Inside class exec() method")
		final String buildSubJob = 'EndpointMidmarket/Shared/Midmarket-New-Build_Hermes'
        final String failedBuildArtifacts = 'test.txt'
        final String buildArtifacts = 'test.txt'
        final String buildLog = 'Build.log'
        
        // Start another job
        def buildResult = script.build job: buildSubJob,
                propagate: false,
                parameters: [
                    [$class: 'StringParameterValue', name: 'BUILD_TYPE', value: buildType],
                    [$class: 'StringParameterValue', name: 'TFSBUILDTYPE', value: 'NightlyLite'],  //no longer used, but not yet removed
                    [$class: 'StringParameterValue', name: 'BUILD_BRAND_INDEX', value: buildBrandIndex],
                    [$class: 'StringParameterValue', name: 'BRANCH', value: branchOrCommit],
                    [$class: 'StringParameterValue', name: 'BUILD_LEVEL', value: buildScope],
                    [$class: 'LabelParameterValue', name: 'BUILD_LABEL', label: buildLabel],
                    [$class: 'BooleanParameterValue', name: 'PUSH_ARTIFACTS_TO_AZURE', value: pushArtifactsToAzure],
                    [$class: 'BooleanParameterValue', name: 'IS_NOTARIZED', value: isNotarized],
                    [$class: 'StringParameterValue', name: 'SKIP_COMPONENTS', value: skipComponents],
                    [$class: 'StringParameterValue', name: 'CLIENT_DOWNLOAD_LOCATION', value: clientDownloadLocation],
                    [$class: 'BooleanParameterValue', name: 'CHECKMARX_FULL_SCAN', value: checkmarxScan],
                    [$class: 'BooleanParameterValue', name: 'WHITESOURCE_SCAN', value: whitesourceScan]
                ]     

        script.println("Sub-Job status: ${buildResult.result}")
        //def bSelector = buildResult.number     
        //script.println("Build ID: ${bSelector.getClass()}")   

        // script.copyArtifacts(
        //     projectName: buildSubJob,
        //     filter: failedBuildArtifacts,
        //     fingerprintArtifacts: true,
        //     selector : specificBuild,
        //     flatten: true
        // )

        def specificBuild = script.specific(buildResult.id)
        if (buildResult.result != 'SUCCESS') {
            // Try to grab the logs if the build failed
            script.copyArtifacts(
                filter              : failedBuildArtifacts,
                fingerprintArtifacts: false,
                flatten             : true,
                optional            : true,     
                selector            : specificBuild,           
                projectName         : buildSubJob
            )
            
            script.archiveArtifacts(
                artifacts: failedBuildArtifacts,
                fingerprint: true
            )
            script.error('Build sub-job failed')
        }
        script.copyArtifacts(        
            filter              : buildArtifacts,
            fingerprintArtifacts: true,
            flatten             : true,
            selector            : specificBuild,
            projectName         : buildSubJob
        )
        script.archiveArtifacts(artifacts: buildArtifacts, fingerprint: true)
        script.warnings parserConfigurations: [parserName: 'MSBuild', pattern: buildLog]



        //script.warningsParsers(parserName: 'MSBuild', pattern: buildLog)
        //io.jenkins.plugins.analysis.core.model.Tool.

        //def res1 = script.MSTest(testResultsFile:"**/*.trx", keepLongStdio: true)
        //script.println(res1)
        
        //script.recordIssues(tools: t)
        //script.warnings(parserName: 'MSBuild', pattern: buildLog)

    } //exec

} //class

return new MyClass(script:this)