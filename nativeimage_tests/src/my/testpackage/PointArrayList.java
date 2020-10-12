package my.testpackage;

import java.util.List;
import java.util.ArrayListPoint;

public class PointArrayList {

	public static void main(String[] args) throws Exception {
		List<Point> array = new ArrayListPoint();
		System.out.println(array.getClass());
		System.out.println(array);
                Point p = new Point(0, 0);
                System.out.println(p);
		array.add(p);
		System.out.println(array);
		array.add(new Point(1,1));
		System.out.println(array);
		array.remove(0);
		System.out.println(array);
	}
}
