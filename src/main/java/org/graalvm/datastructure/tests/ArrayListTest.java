package org.graalvm.datastructure.tests;

import java.util.ArrayList;
import java.util.List;

import org.graalvm.datastructure.DataStructureFactory;

public class ArrayListTest {

	public static void test1() {
		List<Integer> arrayInt = DataStructureFactory.getInstance().ArrayList(Integer.class);
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
		List<Integer> arrayInt = DataStructureFactory.getInstance().ArrayList(Integer.class, 3);
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
		List<Integer> arrayInt2 = DataStructureFactory.getInstance().ArrayList(Integer.class, arrayInt);
		System.out.println(arrayInt2.getClass());
		System.out.println(arrayInt2);
	}
	
	public static void test4() {
		List<Point> array = DataStructureFactory.getInstance().ArrayList(Point.class);
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
		test2();
		test3();
		test4();
	}
}
