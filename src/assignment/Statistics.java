package assignment;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Statistics {
    public final AtomicInteger ordersProcessed = new AtomicInteger(0);
    public final AtomicInteger ordersRejected = new AtomicInteger(0);
    public final AtomicInteger boxesPacked = new AtomicInteger(0);
    public final AtomicInteger containersShipped = new AtomicInteger(0);
    public final AtomicInteger trucksDispatched = new AtomicInteger(0);

    private final List<Long> truckWaitTimes = Collections.synchronizedList(new LinkedList<>());
    private final List<Long> truckLoadTimes = Collections.synchronizedList(new LinkedList<>());

    public void recordTruckTimings(long waitTime, long loadTime) {
        if (waitTime >= 0 && loadTime >= 0) {
            truckWaitTimes.add(waitTime);
            truckLoadTimes.add(loadTime);
        }
    }
    
    private String repeatString(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) { sb.append(str); }
        return sb.toString();
    }

    public synchronized void printFinalReport() {
        String separator = repeatString("=", 32);
        System.out.println("\n" + separator);
        System.out.println("=== Final Summary Report ===");
        System.out.println(separator);

        System.out.println("\n--- Simulation Summary ---");
        System.out.printf("%-22s : %d%n", "Total Orders Processed", ordersProcessed.get());
        System.out.printf("%-22s : %d%n", "Total Orders Rejected", ordersRejected.get());
        System.out.printf("%-22s : %d%n", "Boxes Packed", boxesPacked.get());
        System.out.printf("%-22s : %d%n", "Containers Shipped", containersShipped.get());
        System.out.printf("%-22s : %d%n", "Trucks Dispatched", trucksDispatched.get());
        
        if (!truckWaitTimes.isEmpty()) {
            System.out.println("\n--- Truck Performance (ms) ---");
            long minWait = Collections.min(truckWaitTimes);
            long maxWait = Collections.max(truckWaitTimes);
            double avgWait = truckWaitTimes.stream().mapToLong(l -> l).average().orElse(0.0);
            System.out.printf("Truck Wait Time (Min/Avg/Max): %d / %.2f / %d%n", minWait, avgWait, maxWait);

            long minLoad = Collections.min(truckLoadTimes);
            long maxLoad = Collections.max(truckLoadTimes);
            double avgLoad = truckLoadTimes.stream().mapToLong(l -> l).average().orElse(0.0);
            System.out.printf("Truck Load Time (Min/Avg/Max): %d / %.2f / %d%n", minLoad, avgLoad, maxLoad);
        }
        
        System.out.println("\n" + repeatString("=", 32));
    }
}