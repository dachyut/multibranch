#!groovy
import java.lang.String

class Demo{
	// String branchOrCommit='Default'	
	// String buildType='FAST'
    // String buildScope='CI'
    // String buildLabel='CEB-NewBuild'
    // Boolean pushArtifactsToAzure=true
    // Boolean isNotarized=false
    // String skipComponents=''
    // String clientDownloadLocation='Default'
    // String buildBrandIndex='0'
    // Boolean checkmarxScan=false
    // Boolean whitesourceScan=false

    // Script script;    
    
	// def exec() {    
    //     script.echo ("Inside class exec() method")
	// 	final String buildSubJob = 'job1'
    //     // Start another job
    //     def job = Hudson.instance.getJob(buildSubJob)
    //     def runs = job.getBuilds()		
    //     def currentBuild = runs[0]
    //     def buildResult	
    //     def getjob	        
    //     try {
    //         def params = [
    //             new StringParameterValue('name', branchOrCommit),
    //             new BooleanParameterValue('build', pushArtifactsToAzure)
    //         ]
    //         def runjob = job.scheduleBuild2(0, new Cause.UpstreamCause(currentBuild), new ParametersAction(params))
    //         getjob = runjob.get()                
    //     } catch (Exception e) {
    //         throw(e)
    //     }     
    //     buildResult = getjob.result
    //     script.echo("Sub-Job status: ${buildResult}")
    // }
}

// println("Outside class Demo()")
return new Demo(script:this)