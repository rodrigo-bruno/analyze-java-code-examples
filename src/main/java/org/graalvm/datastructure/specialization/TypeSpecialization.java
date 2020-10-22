package org.graalvm.datastructure.specialization;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.graalvm.datastructure.AOTSpecializedDataStructureFactory;
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

    public static void ensureDirectoryExists(String filePath) throws IOException {
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
    	
    	// add requested imports
    	for (String i : sr.getImports()) {
    		cu.addImport(i);
    	}

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
    
    public static String specializeArrayList(String jdkSources, String patchedSources, String fullEName) throws Exception {
    	String origPackage = "java.util";
    	String origCompilationUnit = "ArrayList";
    	String simpleEName = fullEName.substring(fullEName.lastIndexOf('.') + 1);
    	String genCompilationUnit = "ArrayList" + simpleEName;
        
    	SpecializationRequest sr = new SpecializationRequest(jdkSources, patchedSources, origPackage, origPackage, origCompilationUnit, genCompilationUnit);
    	sr.addGenericTypeSubstitutions("E", simpleEName);
    	sr.addImport(fullEName);
        return specialize(sr);	
    }

    public static String specializeHashMap(String jdkSources, String patchedSources, String fullKName, String fullVName) throws Exception {
    	String origPackage = "java.util";
    	String origCompilationUnit = "HashMap";
    	String simpleKName = fullKName.substring(fullKName.lastIndexOf('.') + 1);
    	String simpleVName = fullVName.substring(fullVName.lastIndexOf('.') + 1);
    	String genCompilationUnit = "HashMap" + simpleKName + simpleVName;

        SpecializationRequest sr = new SpecializationRequest(jdkSources, patchedSources, origPackage, origPackage, origCompilationUnit, genCompilationUnit);
        sr.addGenericTypeSubstitutions("K", simpleKName);
        sr.addGenericTypeSubstitutions("V", simpleVName);
        sr.addImport(fullKName);
        sr.addImport(fullVName);

        // classes outside the local compilation unit need to be manually imported for now
        sr.addTargetType("LinkedHashMap");
        sr.addTargetType("LinkedHashMap.Entry");
        return specialize(sr);	
    }

    public static String specializeLinkedHashMap(String jdkSources, String patchedSources, String fullKName, String fullVName) throws Exception {
    	String origPackage = "java.util";
    	String origCompilationUnit = "LinkedHashMap";
    	String simpleKName = fullKName.substring(fullKName.lastIndexOf('.') + 1);
    	String simpleVName = fullVName.substring(fullVName.lastIndexOf('.') + 1);
    	String genCompilationUnit = "LinkedHashMap" + simpleKName + simpleVName;

        SpecializationRequest sr = new SpecializationRequest(jdkSources, patchedSources, origPackage, origPackage, origCompilationUnit, genCompilationUnit);
        sr.addGenericTypeSubstitutions("K", simpleKName);
        sr.addGenericTypeSubstitutions("V", simpleVName);
        sr.addImport(fullKName);
        sr.addImport(fullVName);

        // classes outside the local compilation unit need to be manually imported for now
        sr.addTargetType("LinkedHashMap.Entry");
        sr.addTargetType("HashMap");
        sr.addTargetType("HashMap.Node");
        sr.addTargetType("Node");
        sr.addTargetType("TreeNode");
        return specialize(sr);	
    }
    
    public static String specializeConcurrentHashMap(String jdkSources, String patchedSources, String fullKName, String fullVName) throws Exception {
    	String origPackage = "java.util.concurrent";
    	String origCompilationUnit = "ConcurrentHashMap";
    	String simpleKName = fullKName.substring(fullKName.lastIndexOf('.') + 1);
    	String simpleVName = fullVName.substring(fullVName.lastIndexOf('.') + 1);
    	String genCompilationUnit = "ConcurrentHashMap" + simpleKName + simpleVName;
        
        SpecializationRequest sr = new SpecializationRequest(jdkSources, patchedSources, origPackage, origPackage, origCompilationUnit, genCompilationUnit);
        sr.addGenericTypeSubstitutions("K", simpleKName);
        sr.addGenericTypeSubstitutions("V", simpleVName);
        sr.addImport(fullKName);
        sr.addImport(fullVName);
        return specialize(sr, new SpecializationVisitorConcurrentHashMap());
    }

    // Syntax: java TypeSpecialization <path to jdk sources> <generated src> <generated bin> [<specializations> <...>]
    public static void main(String[] args) throws Exception {
    	String jdkSources = args[0] + "/";    	
    	String srcJavaBase = args[1] + "/java.base/";
    	String binJavaBase = args[2] + "/java.base/";
    	String srcUnnamedPath = args[1] + "/unnamed";
    	String binUnnamedPath = args[2] + "/unnamed";
    	ArrayList<String> compileRequestsJavaBase = new ArrayList<>();
    	ArrayList<String> compileRequestsUnnamed = new ArrayList<>();
    	AOTSpecializedDataStructureFactory aotfactor = new AOTSpecializedDataStructureFactory(srcUnnamedPath);

    	System.out.println("Loaded JDK sources come from " + jdkSources);
    	System.out.println("Generated java.base source code will be placed in " + srcJavaBase);
    	System.out.println("Generated java.base class files will be placed in " + binJavaBase);
    	System.out.println("Factory source and class will be placed in " + srcUnnamedPath);
    	
    	for (int i = 3; i < args.length; i++) {
    		String specializationRequest = args[i];
    		String type = specializationRequest.split("<")[0];
    		String[] parameters = specializationRequest.split("<")[1].replace(">", "").replaceAll(" ", "").split(",");
    		
    		switch (type) {
    		case "java.util.ArrayList":
    	    	compileRequestsJavaBase.add(specializeArrayList(jdkSources, srcJavaBase, parameters[0]));
    	    	aotfactor.addParameterArrayList(parameters[0]);
    	    	break;
    		case "java.util.HashMap":
    			compileRequestsJavaBase.add(specializeHashMap(jdkSources, srcJavaBase, parameters[0], parameters[1]));
    			compileRequestsJavaBase.add(specializeLinkedHashMap(jdkSources, srcJavaBase, parameters[0], parameters[1]));
    			aotfactor.addParameterHashMap(parameters[0], parameters[1]);
    	    	break;
    		case "java.util.concurrent.ConcurrentHashMap":
    			compileRequestsJavaBase.add(specializeConcurrentHashMap(jdkSources, srcJavaBase, parameters[0], parameters[1]));
    			aotfactor.addParameterConcurrentHashMap(parameters[0], parameters[1]);
    			break;
    		default:
    			System.out.println(String.format("%s not yet supported...", specializationRequest));
    		}
    	}

    	compileRequestsUnnamed.add(aotfactor.generate());
    	
    	for (String compileRequest : compileRequestsJavaBase) {
    		System.out.println("Generated " + compileRequest);
    	}
    	for (String compileRequest : compileRequestsUnnamed) {
    		System.out.println("Generated " + compileRequest);
    	}
    	
    	CompilationRequest.compile(srcJavaBase, binJavaBase, compileRequestsJavaBase.toArray(new String[0]));
    	CompilationRequest.compile(srcJavaBase, binUnnamedPath, compileRequestsUnnamed.toArray(new String[0]));
    	
    	System.out.println("Compiled all generated sources!");
    }
}
