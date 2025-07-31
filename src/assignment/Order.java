package assignment;

public class Order {
    final int id;
    public Order(int id) { this.id = id; }
    @Override public String toString() { return "Order #" + id; }
}