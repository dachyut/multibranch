class A{
    def greet(name){ 
        println "In ClassA - greet()"
        return "greet from A: $name!" }
}

class B{
    def greet(name){ 
        println "In ClassB - greet()"
        return "greet from B: $name!" }
}

// this method just to have nice access to create class by name
Object getProperty(String name){
    return this.getClass().getClassLoader().loadClass(name).newInstance();
} 

return this