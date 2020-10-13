package my.testpackage;

import java.util.List;

public class IntegerArrayList {

	public static void main(String[] args) {
		List<Integer> arrayInt = DataStructureFactory.ArrayList(Integer.class);
		System.out.println(arrayInt.getClass());
		arrayInt.add(1);
		System.out.println(arrayInt);
		arrayInt.add(2);
		System.out.println(arrayInt);
		arrayInt.remove(0);
		System.out.println(arrayInt);
	}
}
