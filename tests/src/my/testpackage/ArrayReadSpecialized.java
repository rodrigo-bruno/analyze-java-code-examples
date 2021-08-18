package my.testpackage;

import java.util.ArrayListPoint;
import java.util.Random;

public class ArrayReadSpecialized {
	public static void main(String[] args) {
	    int runCount = 100_000;
	    int objectCount = 10_000;
	    long[] times = new long[runCount];
	    Random random = new Random();
	    ArrayListPoint array = new ArrayListPoint(objectCount);
	    int nulls = 0;
	
	    System.out.println(String.format("Using %s", array.getClass()));
	
	    // Populate the pop queue
	    for (int i = 0; i < objectCount; ++i) {
	        Point p = new Point(random.nextInt(), random.nextInt());
	        array.add(p);
	    }
	
        // Calling a GC to make measurements more stable
        System.gc();
	    
	    // Execute the experiments
	    for (int run = 0; run < runCount; ++run) {
	        // Record starting time
	        times[run] = System.nanoTime();
	
	        // Execute the experiment
	        for (int i = 0; i < array.size(); ++i) {
	        	Point p = array.get(i);
	        	nulls = p.x >0 ? nulls + 1 : 0;
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
