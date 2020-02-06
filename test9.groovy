import hudson.plugins.copyartifact.BuildSelector
import java.lang.Object
import hudson.model.*
import hudson.*

def exec() {
    println "In test9.groovy"
    println getBuilds()
}

return this