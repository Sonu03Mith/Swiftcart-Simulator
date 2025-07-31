package assignment;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class Warehouse {
    public static final int BAY_AREA_CAPACITY = 5;

    // We use BlockingQueue for its built-in thread-safety and blocking capabilities.
    public final BlockingQueue<Order> intakeToPickingQueue = new ArrayBlockingQueue<>(100);
    public final BlockingQueue<Order> pickingToPackingQueue = new ArrayBlockingQueue<>(100);
    public final BlockingQueue<Box> packingToLabellingQueue = new ArrayBlockingQueue<>(100);
    public final BlockingQueue<Box> labellingToSortingQueue = new ArrayBlockingQueue<>(100);
    public final BlockingQueue<Container> bayAreaQueue = new ArrayBlockingQueue<>(BAY_AREA_CAPACITY);
    public final BlockingQueue<Container> readyForTruckQueue = new ArrayBlockingQueue<>(30);
    
    // Semaphores manage access to limited resources.
    public final Semaphore autonomousLoaders = new Semaphore(3, true);
    public final Semaphore loadingBays = new Semaphore(1, true); // Set to 1 for sequential truck loading
    
    public final Statistics statistics = new Statistics();
}


