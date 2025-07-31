package assignment;

public class RejectHandler {
    public static void handle(Object item, String reason, Statistics stats) {
        stats.ordersRejected.incrementAndGet();
        String threadName = Thread.currentThread().getName();
        synchronized (System.out) {
            System.out.println(threadName + ": REJECTED " + item + " due to '" + reason + "'");
        }
    }
}   