//import hudson.model.*

// Get the out variable
def out = new Binding()

class OutputClass
{
    def out = new Binding()
    OutputClass(out)  // Have to pass the out variable to the class
    {
        out.println ("Inside class OutoutClass")
    }

    def exec(out)  // Have to pass the out variable to the class
    {
        out.println ("Inside class exec")
    }

}

out.println("Outside class")
output = new OutputClass(out)
return output