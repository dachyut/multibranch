#!groovy
import java.lang.String
import hudson.model.*
import hudson.AbortException
import hudson.console.HyperlinkNote
import java.util.concurrent.CancellationException
  

class Demo {                                  
    String name = 'develop'
	
	// Get the out variable
	def out = getBinding().out;
    
    def exec() {        
        final String buildSubJob = 'job3'
        //def buildResult = build job: buildSubJob,
        //    propagate: false
		// Start another job
		def job = Hudson.instance.getJob('job3')
		def anotherBuild
		out.println "$job"
		def runs = job.getBuilds()		
		def currentBuild = runs[0]
		out.println "$currentBuild"
		
		try {
			
			def future = job.scheduleBuild2(0, new Cause.UpstreamCause(currentBuild))
			out.println "Waiting for the completion of " + HyperlinkNote.encodeTo('/' + job.url, job.fullDisplayName)
			anotherBuild = future.get()
		} catch (CancellationException x) {
			throw new AbortException("${job.fullDisplayName} aborted.")
		}
		println HyperlinkNote.encodeTo('/' + anotherBuild.url, anotherBuild.fullDisplayName) + " completed. Result was " + anotherBuild.result
		println anotherBuild.result
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
return new Demo()
//def obj = new Demo()
//obj.exec()