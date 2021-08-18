package my.testpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.graalvm.datastructure.DataStructureFactory;

public class TestingSpecializedDataStructures {

	public static void test1() {
		List<Integer> arrayInt = DataStructureFactory.ArrayList(Integer.class);
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
		List<Integer> arrayInt = DataStructureFactory.ArrayList(Integer.class, 3);
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
		List<Integer> arrayInt2 = DataStructureFactory.ArrayList(Integer.class, arrayInt);
		System.out.println(arrayInt2.getClass());
		System.out.println(arrayInt2);
	}
	
	public static void test4() {
		List<Point> array = DataStructureFactory.ArrayList(Point.class);
		System.out.println(array.getClass());
		array.add(new Point(0, 0));
		System.out.println(array);
		array.add(new Point(1, 1));
		System.out.println(array);
		array.remove(0);
		System.out.println(array);
	}
	
	public static void test5() {
		Map<Integer, String> map = DataStructureFactory.HashMap(Integer.class, String.class);
		System.out.println(map.getClass());
		System.out.println(map);
		map.put(123, "321");
		System.out.println(map);
		map.put(246, "321");
		System.out.println(map);
		map.remove(246);
		System.out.println(map);
	}
	
	public static void test6() {
		Map<String, Point> map = DataStructureFactory.HashMap(String.class, Point.class);
		System.out.println(map.getClass());
		System.out.println(map);
		map.put("00", new Point(0,0));
		System.out.println(map);
		map.put("11", new Point(1,1));
		System.out.println(map);
		map.remove("00");
		System.out.println(map);
	}
	
	public static void test7() {
		Map<Integer, String> map = DataStructureFactory.ConcurrentHashMap(Integer.class, String.class);
		System.out.println(map.getClass());
		System.out.println(map);
		map.put(123, "321");
		System.out.println(map);
		map.put(246, "321");
		System.out.println(map);
		map.remove(246);
		System.out.println(map);	
	}
	
	public static void test8() {
		Map<String, Point> map = DataStructureFactory.ConcurrentHashMap(String.class, Point.class);
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
		test3();
		test4();
		test5();
		test6();
		test7();
		test8();
	}
}
