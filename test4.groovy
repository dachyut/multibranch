void myMapExec (Map<String, Object> myParams) {
    final String functionName = 'myMapExec'
    final List<String> validParams = ['branchOrCommit', 'buildType', 'buildScope', 'pushArtifactsToAzure', 'isNotarized']
    final Map<String, Object> defaultParams = [
            branchOrCommit: 'Default-branchOrCommit',
            buildType: 'Default-buildType',
            buildScope: 'Default-buildScope',
            pushArtifactsToAzure: true,
            isNotarized: true
    ]
    defaultParams.keySet().each {defaultParam ->
        if (!myParams.containsKey(defaultParam)) {myParams."$defaultParam" = defaultParams."$defaultParam"}
    }
    myParams.keySet().each {myParam ->
        assert myParam in validParams : "${functionName}(): Invalid parameter \"${myParam}\".  Must be in ${validParams}"
    }
    println "\n----------\n${functionName} (${myParams})\n----------" as java.lang.Object

    println 'Inside function' as java.lang.Object
    println myParams.branchOrCommit as java.lang.Object
    println myParams.buildType as java.lang.Object
    println myParams.buildScope as java.lang.Object
    println myParams.pushArtifactsToAzure as java.lang.Object
    println myParams.isNotarized as java.lang.Object
}

return this