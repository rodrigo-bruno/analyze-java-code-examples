package my.testpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.graalvm.datastructure.DataStructureFactory;

class Stats {
    public Stats(double mean, double std) {
        this.mean = mean;
        this.std = std;
    }

    public String toString() {
        return "Outcome:\n > mean: " + mean + "\n > std: " + std;
    }

    public double mean;
    public double std;
}

public class PopSubTest {

    public static double calculateSD(double[] numArray) {
        double sum = 0.0;
        double standardDeviation = 0.0;
        int length = numArray.length;

        for (double num : numArray) {
            sum += num;
        }

        double mean = sum / length;

        for (double num : numArray) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return new Stats(mean, Math.sqrt(standardDeviation / length));
    }

    public static void popSubTest() {
        int subCount = 5;
        int runCount = 1_000;
        int objectCount = 10_000_000;
        double[] times = new double[runCount];
        Random random = new Random();
        List<Point>[] subQueues = new List<Point>[subCount];
        List<Point> popQueue = DataStructureFactory.ArrayList(Point.class);

        // Create the sub queues
        for (int i = 0; i < subCount; ++i) {
            subQueues[i] = DataStructureFactory.ArrayList(Point.class);
        }

        // Populate the pop queue
        for (int i = 0; i < objectCount; ++i) {
            Point p = new Point(random.nextInt(), random.nextInt());
            popQueue.add(p);
        }

        // Execute the experiments
        for (int run = 0; run < runCount; ++run) {
            times[run] = System.nanoTime();

            // Execute the experiment
            for (int i = 0; i < popQueue.size(); ++i) {
                Point p = popQueue.get(i);
                subQueues[i % subCount].add(p);
            }

            // Record the time
            times[run] = System.nanoTime() - times[run];
        }

        // Reduce the time
        Stats stats = calculateSD(times);
        System.out.println(stats.toString());
    }

}