package org.graalvm.datastructure;

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataStructureFactory {

	public static <T> AbstractList<T> ArrayList(Class<T> parameterT) {
		return new ArrayList<T>();
	}

	public static <T> AbstractList<T> ArrayList(Class<T> parameterT, int initialCapacity) {
		return new ArrayList<T>(initialCapacity);
	}

	public static <T> AbstractList<T> ArrayList(Class<T> parameterT, Collection<? extends T> c) {
		return new ArrayList<T>(c);
	}

	public static <K,V> AbstractMap<K,V> HashMap(Class<K> parameterK, Class<V> parameterV) {
		return new HashMap<K,V>();
	}

	public static <K,V> AbstractMap<K,V> HashMap(Class<K> parameterK, Class<V> parameterV, int initialCapacity) {
		return new HashMap<K,V>(initialCapacity);
	}

	public static <K,V> AbstractMap<K,V> HashMap(Class<K> parameterK, Class<V> parameterV, int initialCapacity, float loadFactor) {
		return new HashMap<K,V>(initialCapacity, loadFactor);
	}

	public static <K,V> AbstractMap<K,V> HashMap(Class<K> parameterK, Class<V> parameterV, Map<? extends K, ? extends V> m) {
		return new HashMap<K,V>(m);
	}

	public static <K,V> AbstractMap<K,V> ConcurrentHashMap(Class<K> parameterK, Class<V> parameterV) {
		return new ConcurrentHashMap<K,V>();
	}

	public static <K,V> AbstractMap<K,V> ConcurrentHashMap(Class<K> parameterK, Class<V> parameterV, int initialCapacity) {
		return new ConcurrentHashMap<K,V>(initialCapacity);
	}

	public static <K,V> AbstractMap<K,V> ConcurrentHashMap(Class<K> parameterK, Class<V> parameterV, int initialCapacity, float loadFactor) {
		return new ConcurrentHashMap<K,V>(initialCapacity, loadFactor);
	}

	public static <K,V> AbstractMap<K,V> ConcurrentHashMap(Class<K> parameterK, Class<V> parameterV, int initialCapacity, float loadFactor, int concurrencyLevel) {
		return new ConcurrentHashMap<K,V>(initialCapacity, loadFactor, concurrencyLevel);
	}

	public static <K,V> AbstractMap<K,V> ConcurrentHashMap(Class<K> parameterK, Class<V> parameterV, Map<? extends K, ? extends V> m) {
		return new ConcurrentHashMap<K,V>(m);
	}
}
