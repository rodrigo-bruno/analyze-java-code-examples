package my.testpackage;

import java.util.HashMapIntegerPoint;
import java.util.Random;
import org.graalvm.datastructure.DataStructureFactory;

public class MapReadSpecialized {
	public static void main(String[] args) {
	    int runCount = 100_000;
	    int objectCount = 10_000;
	    long[] times = new long[runCount];
	    Random random = new Random();
	    HashMapIntegerPoint map = new HashMapIntegerPoint(objectCount);
	    int nulls = 0;

	    System.out.println(String.format("Using %s", map.getClass()));

	    // Populate the pop queue
	    for (int i = 0; i < objectCount; ++i) {
	        Point p = new Point(random.nextInt(), random.nextInt());
	        map.put(i, p);
	    }

        // Calling a GC to make measurements more stable
        System.gc();

	    // Execute the experiments
	    for (int run = 0; run < runCount; ++run) {
	        // Record starting time
	        times[run] = System.nanoTime();

	        // Execute the experiment
	        for (int i = 0; i < map.size(); ++i) {
	        	Point p = map.get(i);
	        	nulls = p.x > 0 ? nulls + 1 : nulls;
	        }

	        // Record the finishing time
	        times[run] = System.nanoTime() - times[run];
	    }

        // Calling a GC to make measurements more stable
        System.gc();

	    for (int run = 0; run < runCount; ++run) {
	        System.out.println(String.format("Round %s took %s ns (nulls = %d)", run, times[run], nulls));
	    }
	}
}
