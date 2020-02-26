#!groovy
import java.lang.String

class DeployClass {
    Script script
    String branchOrCommit='Default'	
    String lockedResources=''
    String partnerName = 'Carbonite Test Partner'
    String systemAdminEmail = 'vaultdynamic01@carboniteinc.com'
    String systemAdminPassword = ''

    /***********************
    Jenkins pipeline stage to Deploy a Vault
    ************************/
    def exec() {

        script.println('In Deploy.exec()')
        String [] deployList = lockedResources.replace(' ','').split(',')
        script.println(deployList)
        // create a map of test sub jobs to run in parallel
        // Jenkins sandbox does not like some looping constructs to stick to a for loop
        // def stepsForParallel = [:]
        // for (String deploy in deployList) {
        //     script.println "Deploying vault:  ${deploy}"
        //     String myDeploy = deploy
        //     // Closure will late evaluate the loop variable so all steps will see the last value
        //     // Workaround is to declare a copy local to the loop, which will be evaluated correctly
        //     stepsForParallel["${myDeploy}"] = {execOnce("${myDeploy}")}
        // }
        // // Run test sub jobs in parallel, across the map generated above
        // parallel stepsForParallel
    } //exec

    /***********************
    Private method to execute deploy _once_
    This should not be called directly, but wrapped in exec()
    ************************/
    def execOnce(String lockedResource) {

        script.println "Deploying to locked resource: ${lockedResource}"

        final String deployVaultSubJob = 'EndpointMidmarket/Shared/Midmarket-Dynamic-Vault-Deploy'
        final String buildPropertiesFile = 'build.properties'
        final String deployPropertiesFile = "${lockedResource}" + ".properties"

        def buildProps = script.readProperties file: buildPropertiesFile
        branchOrCommit = branchOrCommit ?: buildProps['COMMIT']
        systemAdminPassword = systemAdminPassword ?: generatePassword()

        def buildResult = script.build job: deployVaultSubJob,
                propagate: false,
                parameters: [
                        [$class: 'StringParameterValue', name: 'AUTOMATION_TEST_BRANCH', value: branchOrCommit],
                        [$class: 'StringParameterValue', name: 'BRANCH', value: buildProps['BRANCH']],
                        [$class: 'StringParameterValue', name: 'DOWNLOAD_URL', value: buildProps['DOWNLOAD_URL']],
                        [$class: 'StringParameterValue', name: 'VAULT_INSTALLER', value: buildProps['VAULT_INSTALLER']],
                        [$class: 'StringParameterValue', name: 'VAULT_DBHELPER', value: buildProps['VAULT_DBHELPER']],
                        [$class: 'StringParameterValue', name: 'PARTNER_NAME', value: partnerName],
                        [$class: 'StringParameterValue', name: 'SYSTEM_ADMIN_EMAIL', value: systemAdminEmail],
                        [$class: 'StringParameterValue', name: 'SYSTEM_ADMIN_PASSWORD', value: systemAdminPassword],
                        [$class: 'LabelParameterValue', name: 'label', label: lockedResource]
                ]
        
        def specificBuild = script.specific(buildResult.id)
        script.copyArtifacts(        
            filter              : deployPropertiesFile,
            fingerprintArtifacts: true,
            flatten             : true,
            selector            : specificBuild,
            projectName         : deployVaultSubJob)

        script.archiveArtifacts(
                artifacts: "${deployPropertiesFile}",
                fingerprint: true)

        script.sh "cat ${deployPropertiesFile}"
     } //execOnce

    /***********************
    Generate a random string as a password.
    It takes random characters from alphanum, but with easily confused characters removed.
    This will be visible, but is only used for short-lived
    vault test deployments for automation in this pipeline
    ************************/
    def generatePassword() {
        String alphabet = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefhijkmnoprstuvwxyz23456789"
        def length = 8
        def randomString = ''
        Random rand = new Random()

        for (def ii = 0; ii < length; ii++) {
            randomString += alphabet[rand.nextInt(alphabet.length())]
        }
        return randomString
    } //generatePassword
} //Class

println "Initiating Deploy class"
return new DeployClass(script:this)
