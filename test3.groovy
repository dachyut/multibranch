#!groovy
import java.lang.String

/***********************
 Jenkins pipeline stage to build the product
 ************************/
Script script;
def exec(String branchOrCommit,
         String buildType='FAST') {
	Script script;
    script.echo("inside exec")
}
return (script:this)