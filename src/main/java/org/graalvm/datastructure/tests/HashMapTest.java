package org.graalvm.datastructure.tests;

import java.util.Map;

import org.graalvm.datastructure.DataStructureFactory;

public class HashMapTest {
	
	public static void test1() {
		Map<Integer, String> map = DataStructureFactory.getInstance().HashMap(Integer.class, String.class);
		System.out.println(map.getClass());
		System.out.println(map);
		map.put(123, "321");
		System.out.println(map);
		map.put(246, "321");
		System.out.println(map);
		map.remove(246);
		System.out.println(map);
	}
	
	public static void test2() {
		Map<String, Point> map = DataStructureFactory.getInstance().HashMap(String.class, Point.class);
		System.out.println(map.getClass());
		System.out.println(map);
		map.put("00", new Point(0,0));
		System.out.println(map);
		map.put("11", new Point(1,1));
		System.out.println(map);
		map.remove("00");
		System.out.println(map);
	}
	
	public static void main(String[] args) {
		test1();
		test2();
	}
}
