package org.graalvm.datastructure.specialization;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.graalvm.datastructure.utils.CompilationRequest;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.TypeParameter;

public class TypeSpecialization {

	private static String getClassPath(String sourcepath, String fullclassname) {
    	return sourcepath + fullclassname.replace(".", "/") + ".java";
    }

    private static void ensureDirectoryExists(String filePath) throws IOException {
    	Files.createDirectories(Paths.get(filePath).getParent());
    }
    
    public static void checkUnnecessaryCasts(CompilationUnit cu) {
    	for (CastExpr cast : cu.findAll(CastExpr.class)) {
    		// check this patter: (NodeIntegerInteger[]) new NodeIntegerInteger[]
    		// TODO - implement this patter: (NodeIntegerInteger) new NodeIntegerInteger
    		List<ArrayType> arrayTypeList = cast.findAll(ArrayType.class);
    		List<ArrayCreationExpr> arrayCreationList = cast.findAll(ArrayCreationExpr.class);
    		if (arrayTypeList.size() == 1 && arrayCreationList.size() == 1) {
    			if (arrayCreationList.get(0).getElementType().equals(arrayTypeList.get(0).getComponentType())) {
    				cast.getParentNode().get().replace(cast, arrayCreationList.get(0));
    			}
    		}
    	}
    }
    
    public static String specialize(SpecializationRequest sr, SpecializationVisitor sv, CompilationUnit cu) throws IOException {
    	// file path where the specialized compilation unit will be
    	String filePath = getClassPath(sr.getGenSourcePath(), sr.getOrigPackage() + "." + sr.getGenCompilationUnit());

        // specialize the full compulation unit
        cu.accept(sv, sr);
   	
        // deletes unnecessary casts
    	checkUnnecessaryCasts(cu);

        // creating java file
    	ensureDirectoryExists(filePath);
    	try (PrintWriter out = new PrintWriter(filePath)) {
    		out.print(cu);
    	}
    	
    	return filePath;
    }
    
    public static String specialize(SpecializationRequest sr, SpecializationVisitor sv) throws Exception {
    	CompilationUnit cu = StaticJavaParser.parse(new File(getClassPath(sr.getOrigSourcePath(), sr.getOrigPackage() + "." + sr.getOrigCompilationUnit())));

    	// add all local classes for specialization 
    	for (ClassOrInterfaceDeclaration kdecl : cu.findAll(ClassOrInterfaceDeclaration.class)) {
    		boolean found = false;
    		for (TypeParameter tp : kdecl.getTypeParameters()) {
    			if (sr.getGenericTypesSubstitutions().containsKey(tp.getNameAsString())) {
    				found = true;
    				break;
    			}
    		}
    		if (found) {
    			sr.addTargetType(kdecl.getNameAsString());
    		}
    	}
    	
    	return specialize(sr, sv, cu);
    }
    
    public static String specialize(SpecializationRequest sr) throws Exception {
    	return specialize(sr, new SpecializationVisitor());
    }
    
    public static String specializeArrayList(String jdkSources, String patchedSources, String E) throws Exception {
    	String origPackage = "java.util";
    	String origCompilationUnit = "ArrayList";
    	String genCompilationUnit = "ArrayList" + E;
        
    	SpecializationRequest sr = new SpecializationRequest(jdkSources, patchedSources, origPackage, origPackage, origCompilationUnit, genCompilationUnit);
    	sr.addGenericTypeSubstitutions("E", E);

        return specialize(sr);	
    }

    public static String specializeHashMap(String jdkSources, String patchedSources, String K, String V) throws Exception {
    	String origPackage = "java.util";
    	String origCompilationUnit = "HashMap";
    	String genCompilationUnit = "HashMap" + K + V;

        SpecializationRequest sr = new SpecializationRequest(jdkSources, patchedSources, origPackage, origPackage, origCompilationUnit, genCompilationUnit);
        sr.addGenericTypeSubstitutions("K", K);
        sr.addGenericTypeSubstitutions("V", V);

        // classes outside the local compilation unit need to be manually imported for now
        sr.addTargetType("LinkedHashMap");
        sr.addTargetType("LinkedHashMap.Entry");
        return specialize(sr);	
    }
    
    public static String specializeLinkedHashMap(String jdkSources, String patchedSources, String K, String V) throws Exception {
    	String origPackage = "java.util";
    	String origCompilationUnit = "LinkedHashMap";
    	String genCompilationUnit = "LinkedHashMap" + K + V;

        SpecializationRequest sr = new SpecializationRequest(jdkSources, patchedSources, origPackage, origPackage, origCompilationUnit, genCompilationUnit);
        sr.addGenericTypeSubstitutions("K", K);
        sr.addGenericTypeSubstitutions("V", V);

        // classes outside the local compilation unit need to be manually imported for now
        sr.addTargetType("LinkedHashMap.Entry");
        sr.addTargetType("HashMap");
        sr.addTargetType("HashMap.Node");
        sr.addTargetType("Node");
        sr.addTargetType("TreeNode");
        return specialize(sr);	
    }
    
    public static String specializeConcurrentHashMap(String jdkSources, String patchedSources, String K, String V) throws Exception {
    	String origPackage = "java.util.concurrent";
    	String origCompilationUnit = "ConcurrentHashMap";
    	String genCompilationUnit = "ConcurrentHashMap" + K + V;
        
        SpecializationRequest sr = new SpecializationRequest(jdkSources, patchedSources, origPackage, origPackage, origCompilationUnit, genCompilationUnit);
        sr.addGenericTypeSubstitutions("K", K);
        sr.addGenericTypeSubstitutions("V", V);
        return specialize(sr, new SpecializationVisitorConcurrentHashMap());
    }
    
    public static void specializeASimpleCLass() throws Exception {
    	String origSourcePath = "/home/rbruno/git/analyze-java-code-examples/src/main/resources/";
    	String origPackage = "examples.source";
    	String origCompilationUnit = "ASimpleClass";
    	String genCompilationUnit = "ASimpleClassInteger";
        
        SpecializationRequest sr = new SpecializationRequest(origSourcePath, origSourcePath, origPackage, origPackage, origCompilationUnit, genCompilationUnit);
        sr.addGenericTypeSubstitutions("T", "Integer");
        sr.addTargetType("ASimpleClass");
        specialize(sr);	
    }
    
    // Syntax: java TypeSpecialization [<path to jdk sources> [<path where generated files will be placed>]] 
    public static void main(String[] args) throws Exception {
    	String jdkSources = args.length > 0 ? args[0] : "/home/rbruno/git/labs-openjdk-11/src/java.base/share/classes/";    	
    	String workDir = args.length > 1 ? args[1] : "/tmp/org.graalvm.datastructure.specialization";
    	String patchedSrcPath = workDir + "/src/";
    	String patchedModPath = workDir + "/patched/java.base/";
    	
    	System.out.println("Loaded JDK sources come from " + jdkSources);
    	System.out.println("Generated source code will be placed in " + patchedSrcPath);
    	System.out.println("Generated class files will be placed in " + patchedModPath);
    	
    	String sArrayList = specializeArrayList(jdkSources, patchedSrcPath, "Integer");
    	System.out.println("Generated " + sArrayList);
    	
    	String sHashMap = specializeHashMap(jdkSources, patchedSrcPath, "String", "String");
    	System.out.println("Generated " + sHashMap);
    	
    	String sLinkedHashMap =specializeLinkedHashMap(jdkSources, patchedSrcPath, "String", "String");
    	System.out.println("Generated " + sLinkedHashMap);
    	
    	String sConcurrentHashMap = specializeConcurrentHashMap(jdkSources, patchedSrcPath, "Integer", "Integer");
    	System.out.println("Generated " + sConcurrentHashMap);
    	
    	//String sASimpleClass = specializeASimpleCLass();
    	
    	CompilationRequest.compile(
    			patchedSrcPath,
    			patchedModPath, 
    			new String[] { 
		    			sArrayList, 
		    			sHashMap, 
		    			sLinkedHashMap, 
		    			sConcurrentHashMap 
    			});
    	System.out.println("Compiled all generated sources!");
    }
}
