package org.graalvm.datastructure.tests;

import java.util.Map;

import org.graalvm.datastructure.DataStructureFactory;

public class HashMapTest {
	public static void main(String[] args) {
		Map<Integer, String> mapInt = DataStructureFactory.instance.HashMap(Integer.class, String.class);
		System.out.println(mapInt.getClass());
		System.out.println(mapInt);
		mapInt.put(123, "321");
		System.out.println(mapInt);
		mapInt.put(246, "321");
		System.out.println(mapInt);
		mapInt.remove(246);
		System.out.println(mapInt);
	}
}
