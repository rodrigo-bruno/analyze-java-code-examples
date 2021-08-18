package my.testpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.graalvm.datastructure.DataStructureFactory;

public class PopSubTest {

    public static void main(String[] args) {
        int runCount = 100_000;
        int objectCount = 1_000;
        long[] times = new long[runCount];
        Random random = new Random();
        List<Point> popQueue = DataStructureFactory.ArrayList(Point.class, objectCount);

        System.out.println(String.format("Using %s", popQueue.getClass()));

        // Populate the pop queue
        for (int i = 0; i < objectCount; ++i) {
            Point p = new Point(random.nextInt(), random.nextInt());
            popQueue.add(p);
        }

        // Create the sub queues
        List<Point> subQueues0 = DataStructureFactory.ArrayList(Point.class, objectCount);
        List<Point> subQueues1 = DataStructureFactory.ArrayList(Point.class, objectCount);
        List<Point> subQueues2 = DataStructureFactory.ArrayList(Point.class, objectCount);

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
