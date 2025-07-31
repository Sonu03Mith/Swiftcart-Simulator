package assignment;

public class Container {
    final int id;
    public Container(int id) { this.id = id; }
    @Override public String toString() { return "Container #" + id; }
}