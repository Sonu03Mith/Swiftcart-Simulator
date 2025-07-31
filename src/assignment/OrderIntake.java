package assignment;

public class OrderIntake extends Thread {
    private final Warehouse warehouse;

    public OrderIntake(String name, Warehouse warehouse) { super(name); this.warehouse = warehouse; }

    @Override
    public void run() {
        try {
            System.out.println(getName() + ": System online. Generating 600 orders.");
            for (int i = 1; i <= 600; i++) {
                Thread.sleep(500);
                Order order = new Order(i);
                if (Math.random() < 0.02) {
                    RejectHandler.handle(order, "Payment/Inventory Fail", warehouse.statistics);
                } else {
                    System.out.println(getName() + ": Order #" + i + " received (Thread: " + getName() + ")");
                    warehouse.intakeToPickingQueue.put(order);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(getName() + ": Finished generating orders.");
    }
}