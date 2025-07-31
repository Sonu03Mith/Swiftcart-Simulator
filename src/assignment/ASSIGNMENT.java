package assignment;

import java.util.ArrayList;
import java.util.List;

public class ASSIGNMENT { 

    public static void main(String[] args) throws InterruptedException {
        System.out.println(">>> SWIFTCART E-COMMERCE CENTRE SIMULATION STARTING UP <<<");
        
        Warehouse warehouse = new Warehouse();
        
        // --- Create Lists to Hold Each Type of Thread for joining ---
        List<Thread> pickers = new ArrayList<>();
        List<Thread> packers = new ArrayList<>();
        List<Thread> labellers = new ArrayList<>();
        List<Thread> sorters = new ArrayList<>();
        List<Thread> loaders = new ArrayList<>();
        List<Thread> trucks = new ArrayList<>();
        
        // --- Create All Threads ---
        OrderIntake orderIntake = new OrderIntake("OrderIntake-1", warehouse);

        for (int i = 1; i <= 4; i++) pickers.add(new PickingStation("Picker-" + i, warehouse));
        for (int i = 1; i <= 2; i++) packers.add(new PackingStation("Packer-" + i, warehouse));
        for (int i = 1; i <= 2; i++) labellers.add(new LabellingStation("Labeller-" + i, warehouse));
        sorters.add(new Sorter("Sorter-1", warehouse));
        for (int i = 1; i <= 3; i++) loaders.add(new Loader("Loader-" + i, warehouse));
        for (int i = 1; i <= 2; i++) trucks.add(new Truck("Truck-" + i, warehouse));
        
        Supervisor supervisor = new Supervisor("Supervisor-1", warehouse);

        // --- Start All Threads ---
        System.out.println(">>> Starting all threads... <<<");
        orderIntake.start();
        for (Thread t : pickers) t.start();
        for (Thread t : packers) t.start();
        for (Thread t : labellers) t.start();
        for (Thread t : sorters) t.start();
        for (Thread t : loaders) t.start();
        for (Thread t : trucks) t.start();
        supervisor.start();

        // --- Orchestrated Shutdown Managed by Main Thread ---
        
        // 1. Wait for OrderIntake to finish generating 600 orders.
        orderIntake.join();
        System.out.println(">>> Main: OrderIntake finished. Waiting for pipeline to clear before signaling Pickers...");
        
        // 2. Wait until the first queue is empty, THEN poison the next stage.
        while(!warehouse.intakeToPickingQueue.isEmpty()) Thread.sleep(200);
        for (int i = 0; i < pickers.size(); i++) warehouse.intakeToPickingQueue.put(new Order(-1));
        for (Thread t : pickers) t.join();
        
        // 3. Wait for the next queue, then poison.
        while(!warehouse.pickingToPackingQueue.isEmpty()) Thread.sleep(200);
        System.out.println(">>> Main: Pickers finished. Signaling Packers...");
        for (int i = 0; i < packers.size(); i++) warehouse.pickingToPackingQueue.put(new Order(-1));
        for (Thread t : packers) t.join();

        while(!warehouse.packingToLabellingQueue.isEmpty()) Thread.sleep(200);
        System.out.println(">>> Main: Packers finished. Signaling Labellers...");
        for (int i = 0; i < labellers.size(); i++) warehouse.packingToLabellingQueue.put(new Box(new Order(-1)));
        for (Thread t : labellers) t.join();

        while(!warehouse.labellingToSortingQueue.isEmpty()) Thread.sleep(200);
        System.out.println(">>> Main: Labellers finished. Signaling Sorter...");
        for (int i = 0; i < sorters.size(); i++) warehouse.labellingToSortingQueue.put(new Box(new Order(-1)));
        for (Thread t : sorters) t.join();

        while(!warehouse.bayAreaQueue.isEmpty()) Thread.sleep(200);
        System.out.println(">>> Main: Sorter finished. Signaling Loaders...");
        for (int i = 0; i < loaders.size(); i++) warehouse.bayAreaQueue.put(new Container(-1));
        for (Thread t : loaders) t.join();

        while(!warehouse.readyForTruckQueue.isEmpty()) Thread.sleep(200);
        System.out.println(">>> Main: Loaders finished. Signaling Trucks...");
        for (int i = 0; i < trucks.size(); i++) warehouse.readyForTruckQueue.put(new Container(-1));
        for (Thread t : trucks) t.join();
        
        // Finally, stop the supervisor.
        supervisor.interrupt();

        System.out.println("\n>>> All threads have completed. Generating final report. <<<");
        warehouse.statistics.printFinalReport();
    }
}