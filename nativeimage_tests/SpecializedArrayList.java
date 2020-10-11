import java.util.List;
import java.util.ArrayListInteger;

public class SpecializedArrayList {

	public static void main(String[] args) {
		List<Integer> arrayInt = new ArrayListInteger();
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
