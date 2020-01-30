#!groovy
import java.lang.String
import hudson.model.*
import hudson.AbortException
import hudson.console.HyperlinkNote
import java.util.concurrent.CancellationException
 

class Demo {                                  
    String branch = 'develop'
    String build = 'FAST'
    String commit = "new"
    
    def exec() {        
        final String buildSubJob = 'job3'
        //def buildResult = build job: buildSubJob,
        //    propagate: false
		def job = hudson.model.Hudson.instance.getJob("job3")
def params = new StringParameterValue('PARAMTEST', "somestring")  
def paramsAction = new ParametersAction(params) 
def cause = new hudson.model.Cause.UpstreamCause(currentBuild)
def causeAction = new hudson.model.CauseAction(cause) 
hudson.model.Hudson.instance.queue.schedule(job, 0, causeAction, paramsAction) 
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

//return new Demo()
def obj = new Demo()
obj.exec()