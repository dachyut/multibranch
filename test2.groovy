#!groovy
import java.lang.String

class Demo{
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

    Script script;    
    
	def exec() {    
        script.echo ("Inside class exec() method")
		final String buildSubJob = 'EndpointMidmarket/Shared/Midmarket-New-Build_Hermes'
        final String failedBuildArtifacts = 'Build.log, Failure.txt, **/Failure*.txt'
        final String buildArtifacts = 'Build.log, Failure.txt, build.properties, build_times.csv, git_commits.log, **/TEST*.xml, **/ui/coverage/cobertura-coverage.xml, **/junit-results.xml, **/TestResults/*.trx'
        final String buildLog = 'Build.log'
        
        // Start another job
        // def job = Hudson.instance.getJob(buildSubJob)  -- depricated
        def job = Jenkins.instance.getItemByFullName(buildSubJob)
        def runs = job.getBuilds()		
        def currentBuild = runs[0]
        def buildResult     
        try {
            def params = [
                new StringParameterValue('BUILD_TYPE', buildType),
                new StringParameterValue('BUILD_BRAND_INDEX', buildBrandIndex),
                new StringParameterValue('BRANCH', branchOrCommit),
                new StringParameterValue('BUILD_LEVEL', buildScope),
                new StringParameterValue('BUILD_LABEL', buildLabel),
                new StringParameterValue('SKIP_COMPONENTS', skipComponents),
                new StringParameterValue('CLIENT_DOWNLOAD_LOCATION', clientDownloadLocation),
                new BooleanParameterValue('PUSH_ARTIFACTS_TO_AZURE', pushArtifactsToAzure),
                new BooleanParameterValue('IS_NOTARIZED', isNotarized),                
                new BooleanParameterValue('CHECKMARX_FULL_SCAN', checkmarxScan),
                new BooleanParameterValue('WHITESOURCE_SCAN', whitesourceScan)
            ]
            def runjob = job.scheduleBuild2(0, new Cause.UpstreamCause(currentBuild), new ParametersAction(params))
            buildResult = runjob.get()                
        } catch (Exception e) {
            throw(e)
        }     

        script.echo("Sub-Job status: ${buildResult.result}")
        def bSelector = buildResult.getId()
        
        if (buildResult.result != 'SUCCESS') {
            // Try to grab the logs if the build failed
            step([$class              : 'CopyArtifact',
                filter              : failedBuildArtifacts,
                fingerprintArtifacts: false,
                flatten             : true,
                optional            : true,
                selector            : 19,
                projectName         : buildSubJob])
            archiveArtifacts artifacts: failedBuildArtifacts, fingerprint: true
            error('Build sub-job failed')
        }

        step([$class          : 'CopyArtifact',
          filter              : buildArtifacts,
          fingerprintArtifacts: true,
          flatten             : true,
          selector            : specific(bSelector),
          projectName         : buildSubJob])
    }
}
println("Outside class Demo()")
return new Demo(script:this)