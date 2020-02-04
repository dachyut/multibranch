class MyClass {
    def myField = 'foo'
    def myMethod(myArg='bar') { println "$myField $myArg" }
}

test = new MyClass()
test.metaClass.methods.each { method ->
    println (method.name)
}