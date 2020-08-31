package me.tomassetti.examples;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.ArrayCreationLevel;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.ReceiverParameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.modules.ModuleDeclaration;
import com.github.javaparser.ast.modules.ModuleExportsDirective;
import com.github.javaparser.ast.modules.ModuleOpensDirective;
import com.github.javaparser.ast.modules.ModuleProvidesDirective;
import com.github.javaparser.ast.modules.ModuleRequiresDirective;
import com.github.javaparser.ast.modules.ModuleUsesDirective;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.SynchronizedStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.UnparsableStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.stmt.YieldStmt;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.IntersectionType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.type.UnionType;
import com.github.javaparser.ast.type.UnknownType;
import com.github.javaparser.ast.type.VarType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.type.WildcardType;
import com.github.javaparser.ast.visitor.VoidVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ModifyingCode {

	// Describes the specialization of a compilation unit.
	public static class SpecializationRequest {
		
		private String origSourcePath;
		
		private String genSourcePath;
		
		private String origPackage;
		
		private String genPackage;
		
		private String origCompilationUnit;
		
		private String genCompilationUnit;
		
		private Map<String, String> genericTypesSubstitutions;
		
		private Map<String, String> targetTypes;
		
		public SpecializationRequest(
				String origSourcePath, 
				String genSourcePath,
				String origPackage,
				String genPackage,
				String origCompilationUnit,
				String genCompilationUnit) {
			this.origSourcePath = origSourcePath;
			this.genSourcePath = genSourcePath;
			this.origPackage = origPackage;
			this.genPackage = genPackage;
			this.origCompilationUnit = origCompilationUnit;
			this.genCompilationUnit = genCompilationUnit;
			this.genericTypesSubstitutions = new HashMap<>();
			this.targetTypes = new HashMap<>();
		}
	}
	
	public static class SpecializationVisitor implements VoidVisitor<SpecializationRequest> {

		public void visitBelow(Node n, SpecializationRequest arg) {
			for (Node c : n.getChildNodes()) { 
				c.accept(this, arg); 
			};
		}
		
		@Override
		public void visit(NodeList n, SpecializationRequest arg) {
			System.out.println("NODELIST?: " + n);
		}

		@Override
		public void visit(AnnotationDeclaration n, SpecializationRequest arg) { 
			visitBelow(n, arg);
		}

		@Override
		public void visit(AnnotationMemberDeclaration n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ArrayAccessExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ArrayCreationExpr n, SpecializationRequest arg) { 
			visitBelow(n, arg);		
		}

		@Override
		public void visit(ArrayCreationLevel n, SpecializationRequest arg) {
			 visitBelow(n, arg);
		}

		@Override
		public void visit(ArrayInitializerExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ArrayType n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(AssertStmt n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(AssignExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(BinaryExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(BlockComment n, SpecializationRequest arg) { 
			visitBelow(n, arg);
		}

		@Override
		public void visit(BlockStmt n, SpecializationRequest arg) { 
			visitBelow(n, arg);
		}

		@Override
		public void visit(BooleanLiteralExpr n, SpecializationRequest arg) { 
			visitBelow(n, arg); 
		}

		@Override
		public void visit(BreakStmt n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(CastExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(CatchClause n, SpecializationRequest arg) { 
			visitBelow(n, arg);
		}

		@Override
		public void visit(CharLiteralExpr n, SpecializationRequest arg) { 
			visitBelow(n, arg);
		}

		@Override
		public void visit(ClassExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ClassOrInterfaceDeclaration n, SpecializationRequest arg) {
			if (arg.targetTypes.containsKey(n.getNameAsString())) {
				n.setTypeParameters(new NodeList<>());
				n.setName(arg.targetTypes.get(n.getNameAsString()));
			}
			visitBelow(n, arg);
		}

		@Override
		public void visit(ClassOrInterfaceType n, SpecializationRequest arg) {
			if (arg.targetTypes.containsKey(n.getNameAsString())) {
				n.removeTypeArguments();
				n.setName(arg.targetTypes.get(n.getNameAsString()));
			}
			else if (arg.genericTypesSubstitutions.containsKey(n.getNameAsString())) {
				n.setName(arg.genericTypesSubstitutions.get(n.getNameAsString()));
			}
			visitBelow(n, arg);
		}

		@Override
		public void visit(CompilationUnit n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ConditionalExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ConstructorDeclaration n, SpecializationRequest arg) {
			visitBelow(n, arg); 
		}

		@Override
		public void visit(ContinueStmt n, SpecializationRequest arg) { 
			visitBelow(n, arg);
		}

		@Override
		public void visit(DoStmt n, SpecializationRequest arg) { 
			visitBelow(n, arg);
		}

		@Override
		public void visit(DoubleLiteralExpr n, SpecializationRequest arg) { 
			visitBelow(n, arg);
		}

		@Override
		public void visit(EmptyStmt n, SpecializationRequest arg) { 
			visitBelow(n, arg);
		}

		@Override
		public void visit(EnclosedExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(EnumConstantDeclaration n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(EnumDeclaration n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ExplicitConstructorInvocationStmt n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ExpressionStmt n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(FieldAccessExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(FieldDeclaration n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ForStmt n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ForEachStmt n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(IfStmt n, SpecializationRequest arg) { 
			visitBelow(n, arg);
		}

		@Override
		public void visit(ImportDeclaration n, SpecializationRequest arg) { 
			visitBelow(n, arg); 
		}

		@Override
		public void visit(InitializerDeclaration n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(InstanceOfExpr n, SpecializationRequest arg) { 
			visitBelow(n, arg); 
		}

		@Override
		public void visit(IntegerLiteralExpr n, SpecializationRequest arg) { 
			visitBelow(n, arg); 
		}

		@Override
		public void visit(IntersectionType n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(JavadocComment n, SpecializationRequest arg) { 
			visitBelow(n, arg);
		}

		@Override
		public void visit(LabeledStmt n, SpecializationRequest arg) { 
			visitBelow(n, arg);
		}

		@Override
		public void visit(LambdaExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(LineComment n, SpecializationRequest arg) { 
			visitBelow(n, arg); 
		}

		@Override
		public void visit(LocalClassDeclarationStmt n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(LongLiteralExpr n, SpecializationRequest arg) { 
			visitBelow(n, arg); 
		}

		@Override
		public void visit(MarkerAnnotationExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(MemberValuePair n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(MethodCallExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(MethodDeclaration n, SpecializationRequest arg) { 
			visitBelow(n, arg); 
		}

		@Override
		public void visit(MethodReferenceExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(NameExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(Name n, SpecializationRequest arg) {
			if (arg.targetTypes.containsKey(n.toString())) {
				n.setIdentifier(arg.targetTypes.get(n.toString()));
			}
			visitBelow(n, arg);
		}

		@Override
		public void visit(NormalAnnotationExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(NullLiteralExpr n, SpecializationRequest arg) { 
			visitBelow(n, arg); 
		}

		@Override
		public void visit(ObjectCreationExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(PackageDeclaration n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(Parameter n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(PrimitiveType n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ReturnStmt n, SpecializationRequest arg) { 
			visitBelow(n, arg);
		}

		@Override
		public void visit(SimpleName n, SpecializationRequest arg) {
			if (arg.targetTypes.containsKey(n.toString())) {
				n.setIdentifier(arg.targetTypes.get(n.toString()));
			}
			visitBelow(n, arg);
		}

		@Override
		public void visit(SingleMemberAnnotationExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(StringLiteralExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(SuperExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(SwitchEntry n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(SwitchStmt n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(SynchronizedStmt n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ThisExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ThrowStmt n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(TryStmt n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(TypeExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(TypeParameter n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(UnaryExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(UnionType n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(UnknownType n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(VariableDeclarationExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(VariableDeclarator n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(VoidType n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(WhileStmt n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(WildcardType n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ModuleDeclaration n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ModuleRequiresDirective n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ModuleExportsDirective n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ModuleProvidesDirective n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ModuleUsesDirective n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ModuleOpensDirective n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(UnparsableStmt n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(ReceiverParameter n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(VarType n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(Modifier n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(SwitchExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(TextBlockLiteralExpr n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}

		@Override
		public void visit(YieldStmt n, SpecializationRequest arg) {
			visitBelow(n, arg);
		}
		
	}

    private static String getClassPath(String sourcepath, String fullclassname) {
    	return sourcepath + fullclassname.replace(".", "/") + ".java";
    }

    /*
    
    private static String findClassName(String fullclassname) {
    	String[] splits = fullclassname.split("\\.");
    	return splits[splits.length - 1];
    }
    
    private static Optional<ClassOrInterfaceDeclaration> findClassDeclaration(String sourcepath, String fullclassname) throws Exception {
    	try {
    		CompilationUnit cuint = StaticJavaParser.parse(new File(getClassPath(sourcepath, fullclassname)));	
    		return cuint.getClassByName(findClassName(fullclassname));
    	} catch (FileNotFoundException fnde) {
    		return Optional.empty();
    	}
    }
    
    private static Optional<ClassOrInterfaceDeclaration> findClassDeclaration(PackageDeclaration pdecl, String sourcepath, String classname) throws Exception {
    	String packagename = pdecl.getNameAsString();
    	String fullclassname = packagename + "." + classname;
    	return findClassDeclaration(sourcepath, fullclassname);
    }
    
    private static String getClassDirectory(String sourcepath, String packagename) {
    	return sourcepath + packagename.replace(".", "/");
    }
    
    private static ClassOrInterfaceDeclaration findClassDeclaration(CompilationUnit cuint, String sourcepath, String classname) throws Exception {
    	
    	// class is declared in current compilation unit
    	Optional<ClassOrInterfaceDeclaration> oklass = cuint.getClassByName(classname);
    	if (oklass.isPresent()) {
    		//return oklass.get();
    		throw new Exception(classname + " does not have its own compilation unit!");
    	}
    	
    	// class is declared in the current package
    	oklass = findClassDeclaration(cuint.getPackageDeclaration().get(), sourcepath, classname);
    	if (oklass.isPresent()) {
    		return oklass.get();
    	}
    	
    	// class is imported through one import
    	for (ImportDeclaration importdecl : cuint.getImports()) {
    		if (importdecl.isAsterisk()) {
    			throw new Exception("asterisc import not yet supported: " + importdecl);		
    		} else {
    			oklass = findClassDeclaration(sourcepath, importdecl.getNameAsString());
    			if (oklass.isPresent()) {
    	    		return oklass.get();
    	    	}		
    		}
    	}
    	
    	// no suitable declaration was found...
    	throw new Exception(classname + " not found!");
    }
    
    public static void specialize(String sourcepath, String gensourcepath, CompilationUnit cunit, ClassOrInterfaceDeclaration kdecl, Map<String, String> types) throws Exception {
    	if (kdecl.isInterface() || !kdecl.isPublic()) {
        	System.out.println("Type specialization does not support abstract, interface types, or non public types");
        	return;
        }
        
    	PackageDeclaration origpackage = cunit.getPackageDeclaration().get();
    	PackageDeclaration genpackage = new PackageDeclaration(parseName("gen." + cunit.getPackageDeclaration().get().getNameAsString()));
    	
    	// check if we need to specialize the super type
        if (kdecl.getExtendedTypes().size() > 0) {
        	ClassOrInterfaceType supertype = kdecl.getExtendedTypes(0);
        	if (supertype.getTypeArguments().isPresent()) {
        		// TODO - check if this type is in 'types'
        		ClassOrInterfaceDeclaration superkdecl = findClassDeclaration(cunit, sourcepath, supertype.getNameAsString());
            	CompilationUnit supercunit = (CompilationUnit)superkdecl.getParentNode().get();
            	specialize(sourcepath, gensourcepath, supercunit, superkdecl, types);
            	kdecl.setExtendedType(0, new JavaParser().parseClassOrInterfaceType(superkdecl.getNameAsString()).getResult().get());
            	if (!supercunit.getPackageDeclaration().get().equals(genpackage)) {
            		throw new Exception("Needs a new import!");
            	}
        	}
        }
        
        // specialize the type
        kdecl.accept(new SpecializationVisitor(), new SpecializationRequest(types));
                
    	// change package declaration
    	cunit.setPackageDeclaration(genpackage);
    	
        // add an import from the original package
        cunit.addImport(origpackage.getNameAsString(), false, true);
    	
        // output result
    	String genpackagepath = getClassDirectory(gensourcepath, genpackage.getNameAsString());
    	String genclasspath = getClassPath(gensourcepath, genpackage.getNameAsString() + "." + kdecl.getNameAsString());
    	
    	// creating directory structure
    	File directory = new File(genpackagepath);
        if (! directory.exists()){
            directory.mkdirs();
        }
    	
        // creating java file
    	try (PrintWriter out = new PrintWriter(genclasspath)) {
    		out.print(cunit);
    	}
    }
    */
    public static void specialize(SpecializationRequest sr, CompilationUnit cu) throws FileNotFoundException {

        // specialize the full compulation unit
        cu.accept(new SpecializationVisitor(), sr);
   	
        // creating java file
    	try (PrintWriter out = new PrintWriter(getClassPath(sr.origSourcePath, sr.origPackage + "." + sr.genCompilationUnit))) {
    		out.print(cu);
    	}
    }
    
    public static void specialize(SpecializationRequest sr) throws Exception {
    	CompilationUnit cu = StaticJavaParser.parse(new File(getClassPath(sr.origSourcePath, sr.origPackage + "." + sr.origCompilationUnit)));
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
        sr.genericTypesSubstitutions.put("E", "Integer");
        sr.targetTypes.put("ArrayList", "ArrayListInteger");
        sr.targetTypes.put("SubList", "SubListInteger");

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
        sr.genericTypesSubstitutions.put("T", "Integer");
    	
        specialize(sr);	
    }
    
    public static void main(String[] args) throws Exception {
    	specializeArrayList();
    	//specializeASimpleCLass();
    }
}
