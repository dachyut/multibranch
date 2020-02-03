#!groovy
import java.lang.String

//def out = new Binding()

class Demo{
	String branchOrCommit='Default'	
	String buildType='FAST'

    Script script;    
    
	def exec() {    
        script.echo ("Inside class exec")
		final String buildSubJob = 'job1'
        // Start another job
        def job = Hudson.instance.getJob(buildSubJob)
        def buildResult	
        def job	
        def runs = job.getBuilds()		
        def currentBuild = runs[0]
        try {
            def params = [
                new StringParameterValue('name', 'from jenkins'),
                new BooleanParameterValue('build', false)
            ]
            def runjob = job.scheduleBuild2(0, new Cause.UpstreamCause(currentBuild), new ParametersAction(params))
            //println "Waiting for the completion of " + HyperlinkNote.encodeTo('/' + job.url, job.fullDisplayName)
            job = runjob.get()                
        } //catch (CancellationException x) {
        catch (Exception e) {
            //throw new AbortException("${job.fullDisplayName} aborted.")
            throw(e)
        }        
        buildResult = job.result
        script.echo("Job status: ${buildResult}")
    }
}

println("Outside class Demo")
return new Demo(script:this)