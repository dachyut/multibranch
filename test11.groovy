#!groovy

class MyClass {
    Script script;       
    
    def exec() {
        script.println "Inside test6 exec method"
    }
}

return new MyClass(script:this)