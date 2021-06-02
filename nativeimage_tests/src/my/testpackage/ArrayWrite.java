package my.testpackage;

import java.util.List;
import java.util.Random;
import org.graalvm.datastructure.DataStructureFactory;

public class ArrayWrite {
	public static void main(String[] args) {
	    int runCount = 100_000;
	    int objectCount = 10_000;
	    long[] times = new long[runCount];
	    Random random = new Random();
	    Point[] points = new Point[objectCount];
	    List<Point> array = DataStructureFactory.ArrayList(Point.class, objectCount);
	    int nulls = 0;

	    System.out.println(String.format("Using %s", array.getClass()));

	    // Populate the pop queue
	    for (int i = 0; i < objectCount; ++i) {
	        points[i] = new Point(random.nextInt(), random.nextInt());
	    }

        // Calling a GC to make measurements more stable
        System.gc();

	    // Execute the experiments
        for (int run = 0; run < runCount; ++run) {
            // Record starting time
	        times[run] = System.nanoTime();

	        // Populate the pop queue
		    for (int i = 0; i < objectCount; ++i) {
		        Point p = points[i];
		        if (p.x > 0) {
		        	array.add(p);
		        	nulls++;
		        }
		    }

		    // Record the finishing time
	        times[run] = System.nanoTime() - times[run];

	        // Clearing the array to start over
	        array.clear();
        }

        // Calling a GC to make measurements more stable
        System.gc();

	    for (int run = 0; run < runCount; ++run) {
	    	System.out.println(String.format("Round %s took %s ns (nulls = %d)", run, times[run], nulls));
	    }
	}
}
