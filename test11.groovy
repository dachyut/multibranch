#!groovy
import java.lang.String

class AutomationClass {

    Script script;
    boolean archiveTestResults = false
    String branchOrCommit='Default'
    String testPlatform = 'CEB-Client-Automation-Win10'
    String testSuite = 'suites/smoke.suite'
    String targetFolder = 'Client'
    String buildScope = 'CI'
    boolean skipInstall = false
    String logLevel = ''
    String lockedResources = ''
    String pipelineJobName = ''
    boolean azureVaultFlag = false

    /***********************
    Jenkins pipeline stage to execute automation
    testPlatform can be a comma-delimited list of Jenkins labels to run automation in parallel across those platforms
    ************************/
    def exec () {
        script.println("In Automation exec()")
        // remove any spaces from the suite list
        String [] testSuiteList = testSuite.replace(' ','').split(',')
        String [] lockedResourceList = lockedResources.replace(' ','').split(',')

        script.println "Pipeline job name: ${pipelineJobName}"

        // create a map of test sub jobs to run in parallel
        // Jenkins sandbox does not like some looping constructs to stick to a for loop
        def stepsForParallel = [:]
        int locked_resource_index = 0
        for (String suite in testSuiteList) {
            // Closure will late evaluate the loop variable so all steps will see the last value
            // Workaround is to declare a copy local to the loop, which will be evaluated correctly
            String mySuite = suite
            String lockedResource = lockedResourceList[locked_resource_index]
            locked_resource_index++
            script.println "Test Suite: " + mySuite
            stepsForParallel["${mySuite}"] = {execOnce("${mySuite}", "${targetFolder}/${mySuite}", "${lockedResource}")}
        }

        script.assert testSuiteList.length == locked_resource_index : "Test suite list length does not match locked resource index number."

        // Run test sub jobs in parallel, across the map generated above
        script.parallel stepsForParallel
    } //exec

    /***********************
    Private method to execute automation _once_
    This should not be called directly, but wrapped in exec()
    ************************/
    def private execOnce(
                        String testSuite = 'suites/smoke.suite',
                        String targetFolder = 'Client',                     
                        String lockedResource = '') {

        final String automationSubJob = 'EndpointMidmarket/Shared/Midmarket-Automation-New'
        final String buildPropertiesFile = 'build.properties'
        final String deployPropertiesFile = "${lockedResource}" + ".properties"

        def buildProps = script.readProperties file: buildPropertiesFile
        def deployProps = script.readProperties file: deployPropertiesFile
        branchOrCommit = branchOrCommit ?: buildProps['COMMIT']
        script.println ("-----\nExecuting Test Automation for branch or commit: ${branchOrCommit}\n\tPlatform: ${testPlatform}\n\tSuite: ${testSuite}\n------")
        script.sh "cat ${buildPropertiesFile}"
        script.sh "cat ${deployPropertiesFile}"

        def buildResult = script.build job: automationSubJob,
                propagate: false,
                parameters: [
                        [$class: 'StringParameterValue', name: 'AUTOMATION_TEST_BRANCH', value: branchOrCommit],
                        [$class: 'StringParameterValue', name: 'BRANCH', value: buildProps['BRANCH']],
                        [$class: 'StringParameterValue', name: 'BUILD_LEVEL', value: buildScope],
                        [$class: 'StringParameterValue', name: 'SUITE', value: testSuite],
                        [$class: 'StringParameterValue', name: 'LOG_LEVEL', value: logLevel],
                        [$class: 'StringParameterValue', name: 'DOWNLOAD_URL', value: buildProps['DOWNLOAD_URL']],
                        [$class: 'StringParameterValue', name: 'VAULT_ACTIVATION_URL', value: deployProps['VAULT_ACTIVATION_URL']],
                        [$class: 'StringParameterValue', name: 'VAULT_DB_HELPER_URL', value: deployProps['VAULT_DB_HELPER_URL']],
                        [$class: 'StringParameterValue', name: 'QUICKCACHE_INSTALLER', value: buildProps['QUICKCACHE_INSTALLER']],
                        [$class: 'StringParameterValue', name: 'DCPROTECT_WIN_INSTALLER', value: buildProps['DCPROTECT_WIN_INSTALLER']],
                        [$class: 'StringParameterValue', name: 'DCPROTECT_WIN_RESTWRAPPER', value: buildProps['DCPROTECT_WIN_RESTWRAPPER']],
                        [$class: 'StringParameterValue', name: 'DCPROTECT_MAC_INSTALLER', value: buildProps['DCPROTECT_MAC_INSTALLER']],
                        [$class: 'StringParameterValue', name: 'PARTNER_NAME', value: deployProps['PARTNER_NAME']],
                        [$class: 'StringParameterValue', name: 'SYSTEM_ADMIN_EMAIL', value: deployProps['SYSTEM_ADMIN_EMAIL']],
                        [$class: 'StringParameterValue', name: 'SYSTEM_ADMIN_PASSWORD', value: deployProps['SYSTEM_ADMIN_PASSWORD']],
                        [$class: 'StringParameterValue', name: 'PIPELINE_JOB_NAME', value: pipelineJobName],
                        [$class: 'BooleanParameterValue', name: 'SKIP_INSTALL', value: skipInstall],
                        [$class: 'BooleanParameterValue', name: 'AZURE_VAULT_FLAG', value: azureVaultFlag],
                        [$class: 'BooleanParameterValue', name: 'ARCHIVE_TEST_RESULTS', value: archiveTestResults],
                        [$class: 'LabelParameterValue', name: 'label', label: testPlatform]
                ]
        if (buildResult.result == 'FAILURE') {
            error("Automation sub-job failed for ${testPlatform}")
        }
        // Below statement should be updated if the smoke suite name ever changes
        if (buildResult.result == 'UNSTABLE' && testSuite.contains('smoke.suite')) {
            error("Automation sub-job failed due to smoke test failure for ${testPlatform}")
        }
        def specificBuild = script.specific(buildResult.id)
        script.copyArtifacts(
                    filter              : "automation/junitreports/*.*",
                    target              : targetFolder,
                    fingerprintArtifacts: true,
                    flatten             : false,
                    selector            : specificBuild,           
                    projectName         : automationSubJob)
        script.junit testDataPublishers: [[$class: 'AttachmentPublisher']], testResults: "${targetFolder}/automation/junitreports/results.xml"
    } //execOnce
} //Class

println "Initiating Automation class"
return new AutomationClass(script:this)
