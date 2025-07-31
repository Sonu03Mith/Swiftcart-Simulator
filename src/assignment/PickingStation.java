package assignment;

public class PickingStation extends Thread {
    private final Warehouse warehouse;

    public PickingStation(String name, Warehouse warehouse) { super(name); this.warehouse = warehouse; }

    @Override
    public void run() {
        try {
            while (true) {
                Order order = warehouse.intakeToPickingQueue.take();
                if (order.id == -1) break; // See pill, stop working.

                System.out.println("PickingStation: Picking " + order + " (Thread: " + getName() + ")");
                Thread.sleep(400);
                if (Math.random() < 0.03) {
                    RejectHandler.handle(order, "Missing Items", warehouse.statistics);
                } else {
                    warehouse.pickingToPackingQueue.put(order);
                }
            }
        } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        System.out.println(getName() + " shutting down.");
    }
}