package assignment;

import java.util.UUID;

public class LabellingStation extends Thread {
    private final Warehouse warehouse;

    public LabellingStation(String name, Warehouse warehouse) { super(name); this.warehouse = warehouse; }

    @Override
    public void run() {
        try {
            while (true) {
                Box box = warehouse.packingToLabellingQueue.take();
                if (box.order.id == -1) break;
                
                Thread.sleep(100);
                box.trackingId = "A" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
                System.out.println("LabellingStation: Labelled " + box.order + " with Tracking ID #" + box.trackingId + " (Thread: " + getName() + ")");
                warehouse.statistics.ordersProcessed.incrementAndGet();
                warehouse.labellingToSortingQueue.put(box);
            }
        } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        System.out.println(getName() + " shutting down.");
    }
}