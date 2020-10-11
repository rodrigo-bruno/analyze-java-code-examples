package org.graalvm.datastructure.utils;

public class CompilationRequest {

	public static void compile(String patchedSrcPath, String patchedModPath, String[] filePaths) throws Exception {
		Process p;
        try {
            p = Runtime.getRuntime().exec("javac --patch-module java.base=" + patchedSrcPath +  " -d " + patchedModPath  + " " + String.join(" ", filePaths));
            p.waitFor();

            if (p.exitValue() != 0) {
            	throw new Exception("compilation of specialized data structures failed...");
            }

            p.destroy();
        } catch (Exception e) {
        	e.printStackTrace();
        	throw e;
        }
	}
}
