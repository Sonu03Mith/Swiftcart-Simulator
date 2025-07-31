package assignment;

public class Box {
    final Order order;
    String trackingId;
    public Box(Order order) { this.order = order; }
    @Override public String toString() { return "Box for " + order.id; }
}