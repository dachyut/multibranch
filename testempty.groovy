#!groovy
import java.lang.String
import org.codehaus.groovy.runtime.InvokerHelper

// import hudson.model.*
// import hudson.AbortException
// import hudson.console.HyperlinkNote
// import java.util.concurrent.CancellationException

	

// Get the out variable
//def out = getBinding().out
//import hudson.model.*

//System.out = getBinding().out;
//def out = getBinding().out;


class testempty extends Script {                                  
    String branchOrCommit='Default-branchOrCommit'
    String buildType='Default-buildType'
    String buildScope='Default-buildScope'    
    Boolean pushArtifactsToAzure=true
    Boolean isNotarized=true	

	// Demo(out)  // Have to pass the out variable to the class
    // {
    //     out.println ("Inside class")
    // }

	//this is working; class retrun shoud be like this <return new Demo(script:this)>
	//Script script;

	def run() {
		println 'Groovy world!'
	}

	def runnew() {
		InvokerHelper.runScript(testempty)
		buildResult = build job: 'job3'
	}

    def a() {
        script.echo("Hello-AAAAAA")
		println ("Hello-CCCCCCC")
		//final String buildSubJob = 'job1'
        //build job: 'job1', parameters: [string(name: 'name', value: 'jen-job1'), booleanParam(name: 'build', value: true)]
		def buildResult = build job: 'job3'
		script.echo("Hello-BBBBBB")
		script.echo("$buildResult")		
		return buildResult
    }

	def execprint() {
		println "HELLO"
		File file = new File("out.txt")
		file.write "Parameter details\n"
		file << "branchOrCommit: $branchOrCommit\n"
		file << "buildType: $buildType\n"
		file << "buildScope: $buildScope\n"
		file << "pushArtifactsToAzure: $pushArtifactsToAzure\n"
		file << "isNotarized: $isNotarized\n"
		def lines = file.text
		return lines
	}
        
    def exec() {        
        final String buildSubJob = 'job1'
        //def buildResult = build job: buildSubJob,
        //    propagate: false
		// Start another job
		def job = Hudson.instance.getJob(buildSubJob)
		def anotherBuild
		System.out.println "$job"
		def runs = job.getBuilds()		
		def currentBuild = runs[0]
		println "$currentBuild"
		println "Inside method"
		
		try {
			def params = [
      			new StringParameterValue('name', 'my-name'),
				new BooleanParameterValue('build', false)
    		]
			def future = job.scheduleBuild2(0, new Cause.UpstreamCause(currentBuild), new ParametersAction(params))
			//println "Waiting for the completion of " + HyperlinkNote.encodeTo('/' + job.url, job.fullDisplayName)
			anotherBuild = future.get()
		} //catch (CancellationException x) {
			catch (Exception e) {
			//throw new AbortException("${job.fullDisplayName} aborted.")
			throw(e)
		}
		// println HyperlinkNote.encodeTo('/' + anotherBuild.url, anotherBuild.fullDisplayName) + " completed. Result was " + anotherBuild.result
		// println anotherBuild.result
        println "** Completed ***"
    }
	
	def call() {
    // Any valid steps can be called from this code, just like in other
    // Scripted Pipeline
    println "Hello Achyut."
	final String buildSubJob = 'job3'
	def buildResult = build job: buildSubJob,
		propagate: false
}
	public String toString() {
		println "Inside tostring method"
	}

}

// Demo d = new Demo()
// d.branch = "jhf"
// d.build = "dsfsdfsdf"
// d.exec()
println "outside class"
return new testempty()
//return this