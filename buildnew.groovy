#!groovy
import java.lang.String

/***********************
 Jenkins pipeline stage to build the product
 ************************/
def exec(String branchOrCommit,
         String buildType='FAST',
         String buildScope='CI',
         String buildLabel='CEB-NewBuild',
         Boolean pushArtifactsToAzure=true,
         Boolean isNotarized=false,
         String skipComponents='',
         String clientDownloadLocation='',
         String buildBrandIndex='0',
         Boolean checkmarxScan=false,
         Boolean whitesourceScan=false) {

	final String buildSubJob = 'job1'
    final String failedBuildArtifacts = 'Build.log, Failure.txt, **/Failure*.txt'
    final String buildArtifacts = 'Build.log, Failure.txt, build.properties, build_times.csv, git_commits.log, **/TEST*.xml, **/ui/coverage/cobertura-coverage.xml, **/junit-results.xml, **/TestResults/*.trx'
    final String buildLog = 'Build.log'

    def buildResult = build job: buildSubJob,
            propagate: false,
            parameters: [
                [$class: 'StringParameterValue', name: 'name', value: buildLabel],
                [$class: 'BooleanParameterValue', name: 'build', value: isNotarized]                
            ]

    if (buildResult.result != 'SUCCESS') {
        // Try to grab the logs if the build failed
        step([$class              : 'CopyArtifact',
              filter              : failedBuildArtifacts,
              fingerprintArtifacts: false,
              flatten             : true,
              optional            : true,
              selector            : specific(buildResult.id),
              projectName         : buildSubJob])
        archiveArtifacts artifacts: failedBuildArtifacts, fingerprint: true
        error('Build sub-job failed')
    }
    step([$class              : 'CopyArtifact',
          filter              : buildArtifacts,
          fingerprintArtifacts: true,
          flatten             : true,
          selector            : specific(buildResult.id),
          projectName         : buildSubJob])

    archiveArtifacts artifacts: buildArtifacts, fingerprint: true
    warnings parserConfigurations: [[parserName: 'MSBuild', pattern: buildLog]]

    // Parse Angular and C# Unit test results
    // Use XUnit to run it because the junt plugin complains if the files are too old
    step([$class: 'XUnitBuilder',
        tools: [[
            $class: 'JUnitType',
            pattern: 'TEST*.xml',
            healthScaleFactor: 1.0,
            allowEmptyResults: true,
            allowMissing: true,
            skipNoTestFiles: true
        ],
        [
            $class: 'JUnitType',
            pattern: 'junit-results.xml',
            healthScaleFactor: 1.0,
            allowEmptyResults: true,
            allowMissing: true,
            skipNoTestFiles: true
        ],
		[
            $class: 'MSTestJunitHudsonTestType',
            pattern: '*.trx',
            healthScaleFactor: 1.0,
            allowEmptyResults: true,
            allowMissing: true,
            skipNoTestFiles: true
        ]]
    ])

    // Coverage Report for Angular
    step([
        $class: 'CoberturaPublisher',
        autoUpdateHealth: false,
        autoUpdateStability: false,
        coberturaReportFile: 'cobertura-coverage.xml',
        failUnhealthy: false,
        failUnstable: false,
        maxNumberOfBuilds: 0,
        onlyStable: false,
        sourceEncoding: 'ASCII',
        zoomCoverageChart: false,
        failNoReports: false
    ])

}

return this
