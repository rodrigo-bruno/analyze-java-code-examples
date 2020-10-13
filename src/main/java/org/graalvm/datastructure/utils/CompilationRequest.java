package org.graalvm.datastructure.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CompilationRequest {

	private static void transferTo(InputStream source, OutputStream target) throws IOException {
	    byte[] buf = new byte[8192];
	    int length;
	    while ((length = source.read(buf)) > 0) {
	        target.write(buf, 0, length);
	    }
	}

	public static void compile(String patchedSrcPath, String patchedModPath, String[] filePaths) throws Exception {
		Process p;
        try {
        	String cmd = String.format("javac -cp %s --add-reads java.base=ALL-UNNAMED --patch-module java.base=%s -d %s %s",
            		System.getProperty("java.class.path"),
            		patchedSrcPath,
            		patchedModPath,
            		String.join(" ", filePaths));
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            transferTo(p.getInputStream(), System.out);
            transferTo(p.getErrorStream(), System.out);

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
