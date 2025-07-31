package assignment;

public class Loader extends Thread {
    private final Warehouse warehouse;
    
    public Loader(String name, Warehouse warehouse) { super(name); this.warehouse = warehouse; }
    
    @Override
    public void run() {
        try {
            while (true) {
                warehouse.autonomousLoaders.acquire();
                try {
                    Container container = warehouse.bayAreaQueue.take();
                    if (container.id == -1) {
                        warehouse.bayAreaQueue.put(container); // Put pill back for other loaders
                        break;
                    }

                    System.out.println(getName() + ": Moving " + container + " to loading point");
                    if (Math.random() < 0.1) {
                        System.out.println(getName() + ": BREAKDOWN! Stalled for 3 seconds...");
                        Thread.sleep(3000);
                    } else {
                        Thread.sleep(500);
                    }
                    warehouse.readyForTruckQueue.put(container);
                } finally {
                    warehouse.autonomousLoaders.release();
                }
            }
        } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        System.out.println(getName() + " shutting down.");
    }
}