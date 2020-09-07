package org.graalvm.datastructure.tests;

import java.util.List;

import org.graalvm.datastructure.DataStructureFactory;

public class ArrayListTest {
	public static void main(String[] args) {
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
}