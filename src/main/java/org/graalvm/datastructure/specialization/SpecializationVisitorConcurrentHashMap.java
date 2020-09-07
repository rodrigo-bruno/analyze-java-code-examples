package org.graalvm.datastructure.specialization;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ConditionalExpr;

import me.tomassetti.examples.SpecializationRequest;
import me.tomassetti.examples.SpecializationVisitor;

public class SpecializationVisitorConcurrentHashMap extends SpecializationVisitor {
	
	@Override
	public void visit(CompilationUnit n, SpecializationRequest arg) {
		n.addImport("java.util.concurrent.ConcurrentHashMap.KeySetView");
		visitBelow(n, arg);
	}
	
	@Override
	public void visit(MethodDeclaration n, SpecializationRequest arg) { 
		if (n.getNameAsString().equals("newKeySet")) {
			return;	
		} else {
			visitBelow(n, arg);	
		}
	}
	
	@Override
	public void visit(ConditionalExpr n, SpecializationRequest arg) {
		if (n.toString().contains("(this Map)")) {
			n.getParentNode().get().replace(n, n.getElseExpr());
		} else {
			visitBelow(n, arg);	
		}
		
	}
}
