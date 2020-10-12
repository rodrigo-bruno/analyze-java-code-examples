package org.graalvm.datastructure.specialization;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Describes the specialization of a compilation unit.
public class SpecializationRequest {
			
	private String origSourcePath;
	
	private String genSourcePath;
	
	private String origPackage;
	
	private String genPackage;
	
	private String origCompilationUnit;
	
	private String genCompilationUnit;
	
	private Map<String, String> genericTypesSubstitutions;
	
	private Map<String, String> targetTypes;
	
	private Set<String> imports;

	private String suffix;
	
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
		this.imports = new HashSet<>();
		this.suffix = "";
	}

	public String getOrigSourcePath() {
		return origSourcePath;
	}

	public String getGenSourcePath() {
		return genSourcePath;
	}

	public String getOrigPackage() {
		return origPackage;
	}

	public String getGenPackage() {
		return genPackage;
	}

	public String getOrigCompilationUnit() {
		return origCompilationUnit;
	}

	public String getGenCompilationUnit() {
		return genCompilationUnit;
	}

	public Map<String, String> getGenericTypesSubstitutions() {
		return genericTypesSubstitutions;
	}

	public Map<String, String> getTargetTypes() {
		return targetTypes;
	}
	
	public Set<String> getImports() {
		return imports;
	}

	public void addGenericTypeSubstitutions(String generic, String substitution) {
		genericTypesSubstitutions.put(generic, substitution);
		suffix = suffix + substitution;
	}
	
	public void addTargetType(String type) {
		String[] splits = type.split("\\.");
		String result = "";
		int i = 0;
		
		for (; i < splits.length - 1; i++) {
			result = result + splits[i] + suffix + ".";
		}
		
		result = result + splits[i] + suffix;
		targetTypes.put(type, result);
	}

	public void addImport(String type) {
		imports.add(type);
	}
}