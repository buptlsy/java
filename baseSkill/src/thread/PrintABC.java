package thread;

/**
 * 描述:线程A打印A,线程B打印B,线程C打印C.开三个线程,让他们顺序打印ABC 10次.
 *
 *
 * Created by liushanyong on 16/5/18.
 */
public class PrintABC implements Runnable {

    private String name;
    private Object prev;
    private Object current;

    public PrintABC(String name, Object prev, Object current) {
        this.name = name;
        this.prev = prev;
        this.current = current;
    }

    @Override
    public void run() {
        int count = 10;
        while (count > 0) {
            synchronized (prev) {
                synchronized (current) {
                    System.out.print(name);
                    count--;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    current.notifyAll();
                }
                try {
                    prev.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Object objA = new Object();
        Object objB = new Object();
        Object objC = new Object();
        PrintABC threadA = new PrintABC("A", objC, objA);
        PrintABC threadB = new PrintABC("B", objA, objB);
        PrintABC threadC = new PrintABC("C", objB, objC);
        try {
            new Thread(threadA).start();
            Thread.sleep(10);
            new Thread(threadB).start();
            Thread.sleep(10);
            new Thread(threadC).start();
            Thread.sleep(10);
        } catch (Exception e) {

        }
    }

}
