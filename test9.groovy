import hudson.plugins.copyartifact.BuildSelector
import java.lang.Object
import hudson.model.*
import hudson.*

def exec() {
    println "In test9.groovy"
    def job1_props = build 'job1'
    def j1EnvVariables = job1_props.getBuildVariables();
    println "${j1EnvVariables["BUILD_ID"]}" 
    println "${j1EnvVariables["BUILD_ID"].getClass()}" 
    def n = specific("9")
    println n
    println n.getClass()

    def l = lastSuccessful()
    println l
    println n.getClass()

    step([$class: 'CopyArtifact',
        filter: "*",
        fingerprintArtifacts: true,
        flatten: true,
        selector: specific("6"),
        projectName: "PipelineJob2"])
}

return this