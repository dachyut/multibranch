import org.codehaus.groovy.runtime.InvokerHelper
class Main extends Script {                     
    def run() {                                 
        println 'Groovy world!'                 
	//run1()
    }

    def runScript() {                                 
        println 'Groovy world!!!'                 
	//run1()
    }

    def run1() {
	println "In run1.."
    }	
    static void main(String[] args) {           
        InvokerHelper.runScript(Main, args)     

    }
}

return new Main()