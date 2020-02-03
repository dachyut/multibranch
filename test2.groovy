#!groovy
import java.lang.String

class Demo {
	String branchOrCommit='Default'	
	String buildType='FAST'
	
	def execprint() {
		//def buildit = load 'test1.groovy'			
		GroovyShell shell = new GroovyShell()
	    def tools = shell.parse(new File('./test1.groovy'))
	    tools.exec("code","slow")
    }
}

println("Outside class Demo")
return new Demo()