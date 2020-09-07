package org.graalvm.datastructure.specialization;

import java.util.Iterator;

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
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.SuperExpr;
import com.github.javaparser.ast.expr.SwitchExpr;
import com.github.javaparser.ast.expr.TextBlockLiteralExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
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
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.type.UnionType;
import com.github.javaparser.ast.type.UnknownType;
import com.github.javaparser.ast.type.VarType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.type.WildcardType;
import com.github.javaparser.ast.visitor.VoidVisitor;

import me.tomassetti.examples.SpecializationRequest;

public class SpecializationVisitor implements VoidVisitor<SpecializationRequest> {

	public void visitBelow(Node n, SpecializationRequest arg) {
		for (Node c : n.getChildNodes()) { 
			c.accept(this, arg); 
		};
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void visit(NodeList n, SpecializationRequest arg) {
		Iterator it = n.iterator();
		while (it.hasNext()) {
			((Node)it.next()).accept(this, arg);
		}
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

	public void removeSpecializedTypeParameters(ClassOrInterfaceDeclaration n, SpecializationRequest arg) {
		NodeList<TypeParameter> newlist = new NodeList<>();

		for (TypeParameter ta : n.getTypeParameters()) {
			if (!arg.getGenericTypesSubstitutions().containsKey(ta.toString())) {
				newlist.add(ta);
			}
		}
		
		n.setTypeParameters(newlist);
	}
	
	@Override
	public void visit(ClassOrInterfaceDeclaration n, SpecializationRequest arg) {
		if (arg.getTargetTypes().containsKey(n.getNameAsString())) {
			removeSpecializedTypeParameters(n, arg);
			n.setName(arg.getTargetTypes().get(n.getNameAsString()));
		}
		visitBelow(n, arg);
	}

	public String getFullName(ClassOrInterfaceType n) {
		String name = n.getNameAsString();
		ClassOrInterfaceType c = n;
		while (c.getScope().isPresent()) {
			c = c.getScope().get();
			name = c.getNameAsString() + "." + name;
		}
		return name;
	}
	
	public void setFullName(ClassOrInterfaceType n, String fullName) {
		ClassOrInterfaceType c = n;
		String[] types = fullName.split("\\.");
		
		
		for (int i = types.length - 1; i >= 0; i--) {
			c.setName(types[i]);
			
			if (c.getScope().isEmpty()) {
				break;
			} else {
				c = c.getScope().get();	
			}
		}
	}

	public void removeSpecializedTypeArguments(ClassOrInterfaceType n, SpecializationRequest arg) {
		NodeList<Type> newlist = new NodeList<>();
		int numGenericTypeSubsLeft = arg.getGenericTypesSubstitutions().size();
		
		if (n.getTypeArguments().isEmpty()) {
			return;
		}
		
		for (Type ta : n.getTypeArguments().get()) {
			if (!arg.getGenericTypesSubstitutions().containsKey(ta.toString())) {
				newlist.add(ta);
			} else {
				numGenericTypeSubsLeft--;
				
				// this happens if a class A extends a generic class B and uses multiple times
				// type arguments that we are specializing. This happens in ConcurrentHashMap.
				if (numGenericTypeSubsLeft < 0) {
					newlist.add(new TypeParameter(arg.getGenericTypesSubstitutions().get(ta.toString())));
				}
				
			}
		}
		
		// if we are left with '<?, ?>' (or any other combination), this look tries to remove it.
		for (Type ta : n.getTypeArguments().get()) {
			
			if (numGenericTypeSubsLeft <= 0) {
				break;
			}
			
			if (ta.toString().equals("?")) {
				newlist.remove(ta);
				numGenericTypeSubsLeft--;
			}
		}
		
		if (newlist.size() == 0) {
			// if all the type arguments got removed, remove the '<>'
			n.removeTypeArguments();
		} else {
			// install the new list of type arguments
			n.setTypeArguments(newlist);	
		}
	}
	
	@Override
	public void visit(ClassOrInterfaceType n, SpecializationRequest arg) {
		if (arg.getTargetTypes().containsKey(getFullName(n))) {
			removeSpecializedTypeArguments(n, arg);
			setFullName(n, arg.getTargetTypes().get(getFullName(n)));
		}
		else if (arg.getGenericTypesSubstitutions().containsKey(n.getNameAsString())) {
			n.setName(arg.getGenericTypesSubstitutions().get(n.getNameAsString()));
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
		if (arg.getTargetTypes().containsKey(n.toString())) {
			n.setIdentifier(arg.getTargetTypes().get(n.toString()));
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
		
		if(n.getParentNode().get() instanceof ClassOrInterfaceType) {
			// In the case of ClassOrInterfaceType, the type should be fixed already. 
			// If not fixed, then it is not supposed to be fixed.
			return; 
		}
		
		if (arg.getTargetTypes().containsKey(n.toString())) {
			n.setIdentifier(arg.getTargetTypes().get(n.toString()));
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