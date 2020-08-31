package me.tomassetti.examples;

import java.util.HashMap;
import java.util.Map;

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
}