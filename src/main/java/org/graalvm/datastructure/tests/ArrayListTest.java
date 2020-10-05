package org.graalvm.datastructure.tests;

import java.util.ArrayList;
import java.util.List;

import org.graalvm.datastructure.DataStructureFactory;

public class ArrayListTest {

	public static void test1() {
		List<Integer> arrayInt = DataStructureFactory.instance.ArrayList(Integer.class);
		System.out.println(arrayInt.getClass());
		System.out.println(arrayInt);
		arrayInt.add(1);
		System.out.println(arrayInt);
		arrayInt.add(2);
		System.out.println(arrayInt);
		arrayInt.remove(0);
		System.out.println(arrayInt);
	}

	public static void test2() {
		List<Integer> arrayInt = DataStructureFactory.instance.ArrayList(Integer.class, 3);
		System.out.println(arrayInt.getClass());
		System.out.println(arrayInt);
		arrayInt.add(1);
		System.out.println(arrayInt);
		arrayInt.add(2);
		System.out.println(arrayInt);
		arrayInt.remove(0);
		System.out.println(arrayInt);
	}

	public static void test3() {
		List<Integer> arrayInt = new ArrayList<>();
		arrayInt.add(1);
		arrayInt.add(2);
		List<Integer> arrayInt2 = DataStructureFactory.instance.ArrayList(Integer.class, arrayInt);
		System.out.println(arrayInt2.getClass());
		System.out.println(arrayInt2);
	}

	public static void main(String[] args) {
		test1();
		test2();
		test3();
	}
}
