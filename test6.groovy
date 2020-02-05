name = "PipelineJob2"
def archiveName = 'build.properties'
try {
    step($class: 'hudson.plugins.copyartifact.CopyArtifact',  name, selector: lastSuccessful(), filter: archiveName, fingerprintArtifacts: true)
} catch (none) {
    echo 'No artifact to copy from ' + name + ' with name ' + archiveName
    writeFile file: archiveName, text: '3'
}

def content = readFile(archiveName).trim()
echo 'value archived: ' + content