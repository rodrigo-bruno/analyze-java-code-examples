package org.graalvm.datastructure.utils;

public class CompilationRequest {

	public static void compile(String patchedSrcPath, String patchedModPath, String[] filePaths) throws Exception {
		Process p;
        try {
        	String cmd = String.format("javac -cp %s --add-reads java.base=ALL-UNNAMED --patch-module java.base=%s -d %s %s",
            		System.getProperty("java.class.path"),
            		patchedSrcPath,
            		patchedModPath,
            		String.join(" ", filePaths));
            p = Runtime.getRuntime().exec(cmd);
            p.getInputStream().transferTo(System.out);
            p.getErrorStream().transferTo(System.out);
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
