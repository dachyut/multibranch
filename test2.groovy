#!groovy
import java.lang.String

def out = new Binding()

class Demo extends Script {
	String branchOrCommit='Default'	
	String buildType='FAST'
    
    //def out = new Binding()	
    Demo(out)  // Have to pass the out variable to the class
    {
        script.echo("Hello-AAAAAA")
        out.println ("Inside class OutoutClass")
    }

	def execprint(out) {
        script.echo("Hello-BBBBBBBBB")
        out.println ("Inside class exec")
		//def buildit = load 'test1.groovy'			
		GroovyShell shell = new GroovyShell()
	    def tools = shell.parse(new File('test1.groovy'))
	    tools.exec("code","slow")
    }
}

println("Outside class Demo")
return new Demo()