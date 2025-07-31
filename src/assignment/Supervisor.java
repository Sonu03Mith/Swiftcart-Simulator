package assignment;

public class Supervisor extends Thread {
    private final Warehouse warehouse;

    public Supervisor(String name, Warehouse warehouse) {
        super(name);
        this.warehouse = warehouse;
        setDaemon(true); 
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(2000);
                int bayQueueSize = warehouse.bayAreaQueue.size();
                if (bayQueueSize >= Warehouse.BAY_AREA_CAPACITY) {
                    System.out.println(getName() + ": Dispatch paused. " + bayQueueSize + " containers at bay – waiting for truck.");
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}