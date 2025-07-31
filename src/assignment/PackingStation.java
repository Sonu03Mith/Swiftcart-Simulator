package assignment;

public class PackingStation extends Thread {
    private final Warehouse warehouse;

    public PackingStation(String name, Warehouse warehouse) { super(name); this.warehouse = warehouse; }

    @Override
    public void run() {
        try {
            while (true) {
                Order order = warehouse.pickingToPackingQueue.take();
                if (order.id == -1) break;
                
                System.out.println(getName() + ": Packing " + order);
                Thread.sleep(150);
                Box box = new Box(order);
                if (Math.random() < 0.02) {
                    RejectHandler.handle(box, "Packing Error", warehouse.statistics);
                } else {
                    System.out.println("PackingStation: Packed " + order + " (Thread: " + getName() + ")");
                    warehouse.statistics.boxesPacked.incrementAndGet();
                    warehouse.packingToLabellingQueue.put(box);
                }
            }
        } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        System.out.println(getName() + " shutting down.");
    }
}