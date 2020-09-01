package me.tomassetti.examples;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.TypeParameter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class ModifyingCode {

    private static String getClassPath(String sourcepath, String fullclassname) {
    	return sourcepath + fullclassname.replace(".", "/") + ".java";
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
    
    public static void specialize(SpecializationRequest sr, CompilationUnit cu) throws FileNotFoundException {

        // specialize the full compulation unit
        cu.accept(new SpecializationVisitor(), sr);
   	
        // deletes unnecessary casts
    	checkUnnecessaryCasts(cu);
        
        // creating java file
    	try (PrintWriter out = new PrintWriter(getClassPath(sr.getOrigSourcePath(), sr.getOrigPackage() + "." + sr.getGenCompilationUnit()))) {
    		out.print(cu);
    	}
    }
    
    public static void specialize(SpecializationRequest sr) throws Exception {
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

    	specialize(sr, cu);
    }
    
    public static void specializeArrayList() throws Exception {
    	String origSourcePath = "/home/rbruno/git/labs-openjdk-11/src/java.base/share/classes/";
    	String genSourcePath = origSourcePath;
    	String origPackage = "java.util";
    	String genPackage = origPackage;
    	String origCompilationUnit = "ArrayList";
    	String genCompilationUnit = "ArrayListInteger";
        
    	SpecializationRequest sr = new SpecializationRequest(origSourcePath, genSourcePath, origPackage, genPackage, origCompilationUnit, genCompilationUnit);
    	sr.addGenericTypeSubstitutions("E", "Integer");

        specialize(sr);	
    }
    
    public static void specializeHashMap() throws Exception {
    	String origSourcePath = "/home/rbruno/git/labs-openjdk-11/src/java.base/share/classes/";
    	String genSourcePath = origSourcePath;
    	String origPackage = "java.util";
    	String genPackage = origPackage;
    	String origCompilationUnit = "HashMap";
    	String genCompilationUnit = "HashMapIntegerInteger";

        SpecializationRequest sr = new SpecializationRequest(origSourcePath, genSourcePath, origPackage, genPackage, origCompilationUnit, genCompilationUnit);
        sr.addGenericTypeSubstitutions("K", "Integer");
        sr.addGenericTypeSubstitutions("V", "Integer");

        // classes outside the local compilation unit need to be manually imported for now
        sr.addTargetType("LinkedHashMap");
        sr.addTargetType("LinkedHashMap.Entry");

        specialize(sr);	
    }
    
    public static void specializeLinkedHashMap() throws Exception {
    	String origSourcePath = "/home/rbruno/git/labs-openjdk-11/src/java.base/share/classes/";
    	String genSourcePath = origSourcePath;
    	String origPackage = "java.util";
    	String genPackage = origPackage;
    	String origCompilationUnit = "LinkedHashMap";
    	String genCompilationUnit = "LinkedHashMapIntegerInteger";

        SpecializationRequest sr = new SpecializationRequest(origSourcePath, genSourcePath, origPackage, genPackage, origCompilationUnit, genCompilationUnit);
        sr.addGenericTypeSubstitutions("K", "Integer");
        sr.addGenericTypeSubstitutions("V", "Integer");

        // classes outside the local compilation unit need to be manually imported for now
        sr.addTargetType("LinkedHashMap.Entry");
        sr.addTargetType("HashMap");
        sr.addTargetType("HashMap.Node");
        sr.addTargetType("Node");
        sr.addTargetType("TreeNode");

        specialize(sr);	
    }
    
    public static void specializeConcurrentHashMap() throws Exception {
    	String origSourcePath = "/home/rbruno/git/labs-openjdk-11/src/java.base/share/classes/";
    	String genSourcePath = origSourcePath;
    	String origPackage = "java.util.concurrent";
    	String genPackage = origPackage;
    	String origCompilationUnit = "ConcurrentHashMap";
    	String genCompilationUnit = "ConcurrentHashMapIntegerInteger";
        
        SpecializationRequest sr = new SpecializationRequest(origSourcePath, genSourcePath, origPackage, genPackage, origCompilationUnit, genCompilationUnit);
        sr.addGenericTypeSubstitutions("K", "Integer");
        sr.addGenericTypeSubstitutions("V", "Integer");

        specialize(sr);
    }
    
    public static void specializeASimpleCLass() throws Exception {
    	String origSourcePath = "/home/rbruno/git/analyze-java-code-examples/src/main/resources/";
    	String genSourcePath = origSourcePath;
    	String origPackage = "examples.source";
    	String genPackage = origPackage;
    	String origCompilationUnit = "ASimpleClass";
    	String genCompilationUnit = "ASimpleClassInteger";
        
        SpecializationRequest sr = new SpecializationRequest(origSourcePath, genSourcePath, origPackage, genPackage, origCompilationUnit, genCompilationUnit);
        sr.addGenericTypeSubstitutions("T", "Integer");
        sr.addTargetType("ASimpleClass");
        specialize(sr);	
    }
    
    public static void main(String[] args) throws Exception {
    	//specializeArrayList();
    	//specializeHashMap();
    	//specializeLinkedHashMap();
    	specializeConcurrentHashMap();
    	//specializeASimpleCLass();
    }
}
