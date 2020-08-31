package me.tomassetti.examples;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.type.ArrayType;
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
        sr.getGenericTypesSubstitutions().put("E", "Integer");
        sr.getTargetTypes().put("ArrayList", "ArrayListInteger");
        sr.getTargetTypes().put("SubList", "SubListInteger");

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
        sr.getGenericTypesSubstitutions().put("K", "Integer");
        sr.getGenericTypesSubstitutions().put("V", "Integer");
        sr.getTargetTypes().put("HashMap", "HashMapIntegerInteger");
        sr.getTargetTypes().put("Node", "NodeIntegerInteger");
        sr.getTargetTypes().put("HashMapSpliterator", "HashMapSpliteratorIntegerInteger");
        sr.getTargetTypes().put("KeySpliterator", "KeySpliteratorIntegerInteger");
        sr.getTargetTypes().put("ValueSpliterator", "ValueSpliteratorIntegerInteger");
        sr.getTargetTypes().put("EntrySpliterator", "EntrySpliteratorIntegerInteger");
        sr.getTargetTypes().put("TreeNode", "TreeNodeIntegerInteger");
        sr.getTargetTypes().put("LinkedHashMap.Entry", "LinkedHashMapIntegerInteger.EntryIntegerInteger");
        sr.getTargetTypes().put("LinkedHashMap", "LinkedHashMapIntegerInteger");
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
        sr.getGenericTypesSubstitutions().put("K", "Integer");
        sr.getGenericTypesSubstitutions().put("V", "Integer");
        sr.getTargetTypes().put("HashMap", "HashMapIntegerInteger");
        sr.getTargetTypes().put("HashMap.Node", "HashMapIntegerInteger.NodeIntegerInteger");
        sr.getTargetTypes().put("Node", "NodeIntegerInteger");
        sr.getTargetTypes().put("LinkedHashMap", "LinkedHashMapIntegerInteger");
        sr.getTargetTypes().put("Entry", "EntryIntegerInteger");
        sr.getTargetTypes().put("LinkedHashMap.Entry", "LinkedHashMapIntegerInteger.EntryIntegerInteger");
        sr.getTargetTypes().put("TreeNode", "TreeNodeIntegerInteger");
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
        sr.getGenericTypesSubstitutions().put("T", "Integer");
        sr.getTargetTypes().put("ASimpleClass", "ASimpleClassInteger");
        specialize(sr);	
    }
    
    public static void main(String[] args) throws Exception {
    	specializeArrayList();
    	specializeHashMap();
    	specializeLinkedHashMap();
    	specializeASimpleCLass();
    }
}
