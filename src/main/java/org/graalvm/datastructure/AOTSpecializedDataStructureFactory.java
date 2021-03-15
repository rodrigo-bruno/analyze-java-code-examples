package org.graalvm.datastructure;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.graalvm.datastructure.specialization.TypeSpecialization;

public class AOTSpecializedDataStructureFactory {

	private String genSourcePath;
	private PrintStream stream;
	private List<String> parsArrayList;
	private List<Tuple> parsHashMap;
	private List<Tuple> parsConcurrentHashMap;

	class Tuple {
		String first;
		String second;

		public Tuple(String first, String second) {
			this.first = first;
			this.second = second;
		}
	}

	public AOTSpecializedDataStructureFactory(String srcUnnamedPath) throws IOException {
		this.genSourcePath = srcUnnamedPath + "/org/graalvm/datastructure/DataStructureFactory.java";
		TypeSpecialization.ensureDirectoryExists(genSourcePath);
		this.stream = new PrintStream(genSourcePath);
		this.parsArrayList = new ArrayList<>();
		this.parsHashMap = new ArrayList<>();
		this.parsConcurrentHashMap = new ArrayList<>();
	}

	// Helper method that returns Integer given java.util.Integer
	private String simpleName(String fullTypeName) {
		return fullTypeName.substring(fullTypeName.lastIndexOf('.') + 1);
	}

	private boolean isAutoImported(String fullTypeName) {
		switch (fullTypeName) {
		case "Boolean":
		case "Byte":
		case "Character":
		case "Float":
		case "Integer":
		case "Long":
		case "Short":
		case "Double":
		case "String":
		case "Object":
			return true;
		default:
			return false;
		}
	}

	public void addParameterArrayList(String paramenter) {
		this.parsArrayList.add(paramenter);
	}

	public void addParameterHashMap(String k, String v) {
		this.parsHashMap.add(new Tuple(k, v));
	}

	public void addParameterConcurrentHashMap(String k, String v) {
		this.parsConcurrentHashMap.add(new Tuple(k, v));
	}

	private void generateArrayList() {
		stream.println("\t public static <T> AbstractList<T> ArrayList(Class<T> parameterT) {");
		for (String parameter : parsArrayList) {
			stream.println(String.format("\t\t if (parameterT.equals(%s.class)) { return (AbstractList<T>) new ArrayList%s(); }",
					simpleName(parameter), simpleName(parameter)));
		}
		stream.println("\t\t return new ArrayList<T>();");
		stream.println("\t }");

		stream.println("\t public static <T> AbstractList<T> ArrayList(Class<T> parameterT, int initialCapacity) {");
		for (String parameter : parsArrayList) {
			stream.println(String.format("\t\t if (parameterT.equals(%s.class)) { return (AbstractList<T>) new ArrayList%s(initialCapacity); }",
					simpleName(parameter), simpleName(parameter)));
		}
		stream.println("\t\t return new ArrayList<T>(initialCapacity);");
		stream.println("\t }");

		stream.println("\t public static <T> AbstractList<T> ArrayList(Class<T> parameterT, Collection<? extends T> c) {");
		for (String parameter : parsArrayList) {
			stream.println(String.format("\t\t if (parameterT.equals(%s.class)) { return (AbstractList<T>) new ArrayList%s((Collection<%s>)c); }",
					simpleName(parameter), simpleName(parameter), simpleName(parameter)));
		}
		stream.println("\t\t return new ArrayList<T>(c);");
		stream.println("\t }");
	}

	private void generateHashMap() {
		stream.println("\t public static <K,V> AbstractMap<K,V> HashMap(Class<K> parameterK, Class<V> parameterV) {");
		for (Tuple tuple : parsHashMap) {
			stream.println(String.format("\t\t if (parameterK.equals(%s.class) && parameterV.equals(%s.class)) { return (AbstractMap<K,V>) new HashMap%s%s(); }",
					simpleName(tuple.first), simpleName(tuple.second), simpleName(tuple.first), simpleName(tuple.second)));
		}
		stream.println("\t\t return new HashMap<K,V>();");
		stream.println("\t }");

		stream.println("\t public static <K,V> AbstractMap<K,V> HashMap(Class<K> parameterK, Class<V> parameterV, int initialCapacity) {");
		for (Tuple tuple : parsHashMap) {
			stream.println(String.format("\t\t if (parameterK.equals(%s.class) && parameterV.equals(%s.class)) { return (AbstractMap<K,V>) new HashMap%s%s(initialCapacity); }",
					simpleName(tuple.first), simpleName(tuple.second), simpleName(tuple.first), simpleName(tuple.second)));
		}
		stream.println("\t\t return new HashMap<K,V>(initialCapacity);");
		stream.println("\t }");

		stream.println("\t public static <K,V> AbstractMap<K,V> HashMap(Class<K> parameterK, Class<V> parameterV, int initialCapacity, float loadFactor) {");
		for (Tuple tuple : parsHashMap) {
			stream.println(String.format("\t\t if (parameterK.equals(%s.class) && parameterV.equals(%s.class)) { return (AbstractMap<K,V>) new HashMap%s%s(initialCapacity, loadFactor); }",
					simpleName(tuple.first), simpleName(tuple.second), simpleName(tuple.first), simpleName(tuple.second)));
		}
		stream.println("\t\t return new HashMap<K,V>(initialCapacity, loadFactor);");
		stream.println("\t }");

		stream.println("\t public static <K,V> AbstractMap<K,V> HashMap(Class<K> parameterK, Class<V> parameterV, Map<? extends K, ? extends V> m) {");
		for (Tuple tuple : parsHashMap) {
			stream.println(String.format("\t\t if (parameterK.equals(%s.class) && parameterV.equals(%s.class)) { return (AbstractMap<K,V>) new HashMap%s%s((Map<%s,%s>)m); }",
					simpleName(tuple.first), simpleName(tuple.second), simpleName(tuple.first), simpleName(tuple.second), simpleName(tuple.first), simpleName(tuple.second)));
		}
		stream.println("\t\t return new HashMap<K,V>(m);");
		stream.println("\t }");
	}

	private void generateConcurrentHashMap() {
		stream.println("\t public static <K,V> AbstractMap<K,V> ConcurrentHashMap(Class<K> parameterK, Class<V> parameterV) {");
		for (Tuple tuple : parsConcurrentHashMap) {
			stream.println(String.format("\t\t if (parameterK.equals(%s.class) && parameterV.equals(%s.class)) { return (AbstractMap<K,V>) new ConcurrentHashMap%s%s(); }",
					simpleName(tuple.first), simpleName(tuple.second), simpleName(tuple.first), simpleName(tuple.second)));
		}
		stream.println("\t\t return new ConcurrentHashMap<K,V>();");
		stream.println("\t }");

		stream.println("\t public static <K,V> AbstractMap<K,V> ConcurrentHashMap(Class<K> parameterK, Class<V> parameterV, int initialCapacity) {");
		for (Tuple tuple: parsConcurrentHashMap) {
			stream.println(String.format("\t\t if (parameterK.equals(%s.class) && parameterV.equals(%s.class)) { return (AbstractMap<K,V>) new ConcurrentHashMap%s%s(initialCapacity); }",
					simpleName(tuple.first), simpleName(tuple.second), simpleName(tuple.first), simpleName(tuple.second)));
		}
		stream.println("\t\t return new ConcurrentHashMap<K,V>(initialCapacity);");
		stream.println("\t }");

		stream.println("\t public static <K,V> AbstractMap<K,V> ConcurrentHashMap(Class<K> parameterK, Class<V> parameterV, int initialCapacity, float loadFactor) {");
		for (Tuple tuple : parsConcurrentHashMap) {
			stream.println(String.format("\t\t if (parameterK.equals(%s.class) && parameterV.equals(%s.class)) { return (AbstractMap<K,V>) new ConcurrentHashMap%s%s(initialCapacity, loadFactor); }",
					simpleName(tuple.first), simpleName(tuple.second), simpleName(tuple.first), simpleName(tuple.second)));
		}
		stream.println("\t\t return new ConcurrentHashMap<K,V>(initialCapacity, loadFactor);");
		stream.println("\t }");

		stream.println("\t public static <K,V> AbstractMap<K,V> ConcurrentHashMap(Class<K> parameterK, Class<V> parameterV, int initialCapacity, float loadFactor, int concurrencyLevel) {");
		for (Tuple tuple : parsConcurrentHashMap) {
			stream.println(String.format("\t\t if (parameterK.equals(%s.class) && parameterV.equals(%s.class)) { return (AbstractMap<K,V>) new ConcurrentHashMap%s%s(initialCapacity, loadFactor, concurrencyLevel); }",
					simpleName(tuple.first), simpleName(tuple.second), simpleName(tuple.first), simpleName(tuple.second)));
		}
		stream.println("\t\t return new ConcurrentHashMap<K,V>(initialCapacity, loadFactor, concurrencyLevel);");
		stream.println("\t }");

		stream.println("\t public static <K,V> AbstractMap<K,V> ConcurrentHashMap(Class<K> parameterK, Class<V> parameterV, Map<? extends K, ? extends V> m) {");
		for (Tuple tuple : parsConcurrentHashMap) {
			stream.println(String.format("\t\t if (parameterK.equals(%s.class) && parameterV.equals(%s.class)) { return (AbstractMap<K,V>) new ConcurrentHashMap%s%s((Map<%s,%s>)m); }",
					simpleName(tuple.first), simpleName(tuple.second), simpleName(tuple.first), simpleName(tuple.second), simpleName(tuple.first), simpleName(tuple.second)));
		}
		stream.println("\t\t return new ConcurrentHashMap<K,V>(m);");
		stream.println("\t }");
	}

	public String generate() {
		stream.println("package org.graalvm.datastructure;");

		stream.println(String.format("import java.util.Collection;"));
		stream.println(String.format("import java.util.AbstractList;"));
		stream.println(String.format("import java.util.AbstractMap;"));
		stream.println(String.format("import java.util.Map;"));
		stream.println(String.format("import java.util.ArrayList;"));
		stream.println(String.format("import java.util.HashMap;"));
		stream.println(String.format("import java.util.concurrent.ConcurrentHashMap;"));

		for (String parameter : parsArrayList) {
			stream.println(String.format("import java.util.ArrayList%s;", simpleName(parameter)));
			if (!isAutoImported(parameter)) {
				stream.println(String.format("import %s;", parameter));
			}
		}

		for (Tuple tuple : parsHashMap) {
			stream.println(String.format("import java.util.HashMap%s%s;", simpleName(tuple.first), simpleName(tuple.second)));
			if (!isAutoImported(tuple.first)) {
				stream.println(String.format("import %s;", tuple.first));
			}
			if (!isAutoImported(tuple.second)) {
				stream.println(String.format("import %s;", tuple.second));
			}
		}

		for (Tuple tuple : parsConcurrentHashMap) {
			stream.println(String.format("import java.util.concurrent.ConcurrentHashMap%s%s;", simpleName(tuple.first), simpleName(tuple.second)));
			if (!isAutoImported(tuple.first)) {
				stream.println(String.format("import %s;", tuple.first));
			}
			if (!isAutoImported(tuple.second)) {
				stream.println(String.format("import %s;", tuple.second));
			}
		}

		stream.println("public class DataStructureFactory {");

		generateArrayList();
		generateHashMap();
		generateConcurrentHashMap();

		stream.println("}");

		stream.close();
		return this.genSourcePath;
	}

}
