import java.text.*;

public class Performance {
    public static void measurePerformance(Runnable task) {
        //Accessing the runtime instance in order to interact with the Java Runtime Environment (JRE)
        Runtime runtime = Runtime.getRuntime();

        //Store memory and time before task execution
        long startMemory = runtime.totalMemory() - runtime.freeMemory();
        long startTime = System.nanoTime();

        task.run(); //Execute the provided task

        //Store memory and time after task execution
        long endTime = System.nanoTime();
        long endMemory = runtime.totalMemory() - runtime.freeMemory();

        //Convert data into desired measuring units
        double duration = (endTime - startTime) / 1000000.0;
        double usedMemory = (endMemory - startMemory) / 1024.0;

        //Instantiate a DecimalFormat object in order to format the values and output the result
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        System.out.println("Execution time: " + decimalFormat.format(duration) + " milliseconds.");
        System.out.println("Memory used: " + decimalFormat.format(usedMemory) + " kilobytes.");
    }
}