package my.testpackage;

import org.graalvm.datastructure.specialization.TypeSpecialization;
import org.graalvm.datastructure.utils.CompilationRequest;

public class SetupSpecialization {
    public static void main(String[] args) throws Exception {
        String jdkSources = args[0] + "/";    	
        String patchedSrcPath = args[1] + "/";
        String patchedModPath = args[2] + "/";
    	
        System.out.println("Loaded JDK sources come from " + jdkSources);
        System.out.println("Generated source code will be placed in " + patchedSrcPath);
        System.out.println("Generated class files will be placed in " + patchedModPath);
    	
        String sIntegerArrayList = TypeSpecialization.specializeArrayList(jdkSources, patchedSrcPath, "Integer");
        System.out.println("Generated " + sIntegerArrayList);
        String sPointArrayList = TypeSpecialization.specializeArrayList(jdkSources, patchedSrcPath, "my.testpackage.Point");
        System.out.println("Generated " + sPointArrayList);
    	
    	CompilationRequest.compile(
    			patchedSrcPath,
    			patchedModPath, 
    			new String[] { 
		    			sIntegerArrayList, 
		    			sPointArrayList, 
    			});

        // TODO - generate a new factory
        

    	System.out.println("Compiled all generated sources!");
    }
}
