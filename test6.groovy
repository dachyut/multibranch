#!groovy
import java.lang.String


class MyClass {
    Script script;

    def exec() {
        ////echo "Inside exec method"
        def name = "PipelineJob2"
        def archiveName = 'build.properties'
        //try {
        script.echo("CopyArtifacts.........")
        //step($class: 'hudson.plugins.copyartifact.CopyArtifact', projectName: name,  filter: 'build.properties',fingerprintArtifacts: true, target: 'd:\\artifacts')
        script.copyArtifacts(
            projectName: name,
            filter: 'build.properties',
            fingerprintArtifacts: true,
            target: 'd:\\artifacts',
            flatten: true
        )
            //script.echo("Archive artifacts")
            //step(archiveArtifacts(artifacts: archiveName, fingerprint: true))
        //} catch (none) {
            ////echo 'No artifact to copy from ' + name 
            //writeFile file: archiveName, text: '3'
        //}
        script.println("Hellooooooooooo")
        script.echo("Listing files......")
        // def rootFiles = new File("test").listRoots() 
        // rootFiles.each { file -> script.echo(file.absolutePath) }

        // script.echo("File contents.......")
        // File file = new File(archiveName)
        // def lines = file.text
        // script.echo("File : ${archiveName}")        
    }
}

return new MyClass(script:this)