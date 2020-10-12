package org.graalvm.datastructure.tests;

import java.util.List;

import org.graalvm.datastructure.DataStructureFactory;

public class ArrayListTest2 {

	public static void test1() {
		List<Point> array = DataStructureFactory.instance.ArrayList(Point.class);
		System.out.println(array.getClass());
		array.add(new Point(0, 0));
		System.out.println(array);
		array.add(new Point(1, 1));
		System.out.println(array);
		array.remove(0);
		System.out.println(array);
	}

	public static void main(String[] args) {
		test1();
	}
}
