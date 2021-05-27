package my.testpackage;

import java.util.ArrayListPoint;
import java.util.Random;

public class PopSubTestSpecialized {

    public static void main(String[] args) {
        int runCount = 100_000;
        int objectCount = 1_000;
        long[] times = new long[runCount];
        Random random = new Random();
        ArrayListPoint popQueue = new ArrayListPoint(objectCount);

        System.out.println(String.format("Using %s", popQueue.getClass()));

        // Populate the pop queue
        for (int i = 0; i < objectCount; ++i) {
            Point p = new Point(random.nextInt(), random.nextInt());
            popQueue.add(p);
        }

        // Create the sub queues
        ArrayListPoint subQueues0 = new ArrayListPoint(objectCount);
        ArrayListPoint subQueues1 = new ArrayListPoint(objectCount);
        ArrayListPoint subQueues2 = new ArrayListPoint(objectCount);

        // Execute the experiments
        for (int run = 0; run < runCount; ++run) {

            subQueues0.clear();
            subQueues1.clear();
            subQueues2.clear();

            // Calling a GC to make measurements more stable
            //System.gc();

            // Record starting time
            times[run] = System.nanoTime();

            // Execute the experiment
            for (int i = 0; i < popQueue.size(); ++i) {
                if (i % 3 == 0) {
                    subQueues0.add(popQueue.get(i));
                } else if (i % 3 == 1) {
                    subQueues1.add(popQueue.get(i));
                } else {
                    subQueues2.add(popQueue.get(i));
                }
            }

            // Record the finishing time
            times[run] = System.nanoTime() - times[run];
        }

        for (int run = 0; run < runCount; ++run) {
            System.out.println(String.format("Round %s took %s ns", run, times[run]));
        }
    }

}
