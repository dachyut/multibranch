class MyClass {
    Script script;

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

        File file = new File(archiveName)
        def lines = file.text
        script.echo("File : ${archiveName}")
        //return lines
        //def content = readFile(archiveName).trim()
        //echo 'value archived: '
    }
}

return new MyClass(script:this)