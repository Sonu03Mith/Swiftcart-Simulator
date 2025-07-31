package assignment;

import java.util.concurrent.TimeUnit;

public class Truck extends Thread {
    private final Warehouse warehouse;
    private final int TRUCK_CAPACITY = 18;

    public Truck(String name, Warehouse warehouse) { super(name); this.warehouse = warehouse; }

    @Override
    public void run() {
        long arrivalTime = System.currentTimeMillis();
        boolean bayAcquired = false;
        try {
            System.out.println(getName() + ": Arrived, waiting for the loading bay.");
            bayAcquired = warehouse.loadingBays.tryAcquire(10, TimeUnit.MINUTES);

            if (bayAcquired) {
                long dockedTime = System.currentTimeMillis();
                System.out.println(getName() + ": Docked at the loading bay.");
                int containersLoaded = 0;

                while (containersLoaded < TRUCK_CAPACITY) {
                    Container container = warehouse.readyForTruckQueue.take();
                    
                    if (container.id == -1) {
                        warehouse.readyForTruckQueue.put(container); // Put pill back for the other truck
                        break;
                    }

                    Thread.sleep(300);
                    containersLoaded++;
                    warehouse.statistics.containersShipped.incrementAndGet();
                    System.out.println(getName() + ": Loaded " + container + " (" + containersLoaded + "/" + TRUCK_CAPACITY + ")");
                }
                
                long departureTime = System.currentTimeMillis();
                System.out.println(getName() + ": Departing with " + containersLoaded + " containers.");
                warehouse.statistics.trucksDispatched.incrementAndGet();
                long waitTime = dockedTime - arrivalTime;
                long loadTime = departureTime - dockedTime;
                warehouse.statistics.recordTruckTimings(waitTime, loadTime);
            } else {
                System.out.println(getName() + ": Timed out waiting for bay. Departing empty.");
            }
        } catch (InterruptedException e) { Thread.currentThread().interrupt(); } 
        finally {
            if (bayAcquired) {
                warehouse.loadingBays.release();
                System.out.println(getName() + ": Has left the facility. Bay is now free.");
            }
        }
        System.out.println(getName() + " shutting down.");
    }
}