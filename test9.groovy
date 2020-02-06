import hudson.plugins.copyartifact.BuildSelector
import java.lang.Object
import hudson.model.*
import hudson.*

def exec() {
    println "In test9.groovy"
    def job1_props = build 'job1'
    def j1EnvVariables = job1_props.getBuildVariables();
    print "${j1EnvVariables}" 
}

return this