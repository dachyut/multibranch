#!groovy
import java.lang.String

class Demo {                                  
    String branch = 'develop'
    String build = 'FAST'
    String commit = "new"
    
    def exec() {        
        final String buildSubJob = 'job3'
        def buildResult = build job: buildSubJob,
            propagate: false

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
return new Demo()