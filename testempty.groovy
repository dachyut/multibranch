#!groovy
import java.lang.String
import hudson.model.*
import hudson.AbortException
import hudson.console.HyperlinkNote
import java.util.concurrent.CancellationException
  

class Demo {                                  
    String name = 'develop'
    
    def exec() {        
        final String buildSubJob = 'job3'
        //def buildResult = build job: buildSubJob,
        //    propagate: false
		// Start another job
		def job = Hudson.instance.getJob('job3')
		def anotherBuild
		// get current thread / Executor
		def thr = Thread.currentThread()
		// get current build
		def build = thr?.executable

		try {
			
			def future = job.scheduleBuild2(0, new Cause.UpstreamCause(build), new ParametersAction(params))
			println "Waiting for the completion of " + HyperlinkNote.encodeTo('/' + job.url, job.fullDisplayName)
			anotherBuild = future.get()
		} catch (CancellationException x) {
			throw new AbortException("${job.fullDisplayName} aborted.")
		}
		println HyperlinkNote.encodeTo('/' + anotherBuild.url, anotherBuild.fullDisplayName) + " completed. Result was " + anotherBuild.result
 
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
}

// Demo d = new Demo()
// d.branch = "jhf"
// d.build = "dsfsdfsdf"
// d.exec()

return new Demo()
//def obj = new Demo()
//obj.exec()