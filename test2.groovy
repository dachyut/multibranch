#!groovy
import java.lang.String

class Demo {
	String branchOrCommit='Default'	
	String buildType='FAST'
	
	def execprint() {
		def buildit = load 'test1.groovy'			
			buildit.exec("code","slow")
    }
}

println("Outside class Demo")
return new Demo()