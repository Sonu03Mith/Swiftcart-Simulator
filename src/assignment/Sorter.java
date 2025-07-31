package assignment;

import java.util.ArrayList;
import java.util.List;

public class Sorter extends Thread {
    private final Warehouse warehouse;
    private int containerIdCounter = 1;
    private int batchIdCounter = 1;

    public Sorter(String name, Warehouse warehouse) { super(name); this.warehouse = warehouse; }

    @Override
    public void run() {
        List<Box> collectedBoxes = new ArrayList<>();
        try {
            while (true) {
                Box box = warehouse.labellingToSortingQueue.take();
                if (box.order.id == -1) break;
                
                System.out.println("Sorter: Added " + box.order + " to Batch #" + batchIdCounter + " (Thread: " + getName() + ")");
                collectedBoxes.add(box);
                if (collectedBoxes.size() % 6 == 0) batchIdCounter++;
                if (collectedBoxes.size() >= 30) {
                    formAndMoveContainer(new ArrayList<>(collectedBoxes.subList(0, 30)));
                    collectedBoxes.subList(0, 30).clear();
                }
            }
        } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        
        if (!collectedBoxes.isEmpty()) {
            formAndMoveContainer(collectedBoxes);
        }
        System.out.println(getName() + " shutting down.");
    }

    private void formAndMoveContainer(List<Box> boxesForContainer) {
        if (boxesForContainer.isEmpty()) return;
        Container container = new Container(containerIdCounter++);
        try {
            System.out.println("Sorter: " + container + " with " + boxesForContainer.size() + " boxes is ready. Moving to bay...");
            warehouse.bayAreaQueue.put(container);
            System.out.println("Sorter: " + container + " moved to loading bay area.");
        } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}