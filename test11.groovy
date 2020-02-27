#!groovy

class MyClass {
    Script script;     
    String branchOrCommit='Default'	
    String lockedResources=''
    String partnerName = 'Carbonite Test Partner'
    String systemAdminEmail = 'vaultdynamic01@carboniteinc.com'
    String systemAdminPassword = ''  
    
    def exec() {
        script.println "Inside test6 exec method"
        script.println branchOrCommit
        script.println lockedResources
        script.println partnerName
    }
}

return new MyClass(script:this)