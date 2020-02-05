class MyClass {

    def exec() {
        println "Inside exec method"
        def name = "PipelineJob2"
        def archiveName = 'build.properties'
        try {
            step($class: 'hudson.plugins.copyartifact.CopyArtifact',  name, selector: lastSuccessful(), filter: *, fingerprintArtifacts: true)
        } catch (none) {
            echo 'No artifact to copy from ' + name + ' with name ' + archiveName
            writeFile file: archiveName, text: '3'
        }

        def content = readFile(archiveName).trim()
        echo 'value archived: ' + content
    }
}

return new MyClass()