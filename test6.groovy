class MyClass {

    def exec() {
        ////echo "Inside exec method"
        def name = "branch-6"
        def archiveName = 'build.properties'
        try {
            step($class: 'hudson.plugins.copyartifact.CopyArtifact', projectName: name, selector: lastSuccessful(), filter: 'build.properties',fingerprintArtifacts: true)
            step(archiveArtifacts(artifacts: archiveName, fingerprint: true))
        } catch (none) {
            ////echo 'No artifact to copy from ' + name 
            //writeFile file: archiveName, text: '3'
        }

        def content = readFile(archiveName).trim()
        //echo 'value archived: '
    }
}

return new MyClass()