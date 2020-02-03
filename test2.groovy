#!groovy
import java.lang.String

//def out = new Binding()

class Demo{
	String branchOrCommit='Default'	
	String buildType='FAST'

    Script script;    
    //def out = new Binding()	

    // def run() {
	// 	script.echo 'Groovy world!'
	// 	//buildResult = build job: 'job3'
	// }

    // def Demo()  // Have to pass the out variable to the class
    // {
    //     script.echo("Hello-AAAAAA")
    //     out.println ("Inside class OutoutClass")
    // }

	def execprint() {
        script.echo("Hello-BBBBBBBBB")
        script.echo ("Inside class exec")
		//def buildit = load 'test1.groovy'			
		GroovyShell shell = new GroovyShell()
	    def tools = shell.parse(new File('test1.groovy'))
	    tools.exec("code","slow")
    }
}

println("Outside class Demo")
return new Demo(script:this)