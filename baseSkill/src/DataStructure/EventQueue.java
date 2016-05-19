package DataStructure;

/**
 * 实现阻塞队列.
 * 1.循环队列
 * 2.object 的 wait和notifyAll
 *
 * Created by liushanyong on 16/5/17.
 */
public class EventQueue {

    private int front, rear; // 分别指向队首和队尾
    private int capacity = 8; // 队列容量
    private int[] queue = null; // 存储元素

    public EventQueue() {
        this(8);
    }

    public EventQueue(int capacity) {
        this.capacity = capacity;
        queue = new int[capacity];
        front = rear = 0; // 少用一个元素空间，约定以“队列头指针front在队尾指针rear的下一个位置上”作为队列“满”状态的标志
    }

    public synchronized boolean enqueue(int value) throws InterruptedException {
        return enqueue(value, -1);
    }

    /**
     * @param value
     * @param maxWaitTime
     * @return
     * @throws InterruptedException
     */
    public synchronized boolean enqueue(int value, int maxWaitTime) throws InterruptedException {
        while (isFull()) {
            if (maxWaitTime > 0) {
                wait(maxWaitTime);
                if (isFull()) {
                    return false;
                }
            } else {
                wait();
            }
        }
        queue[rear % capacity] = value;
        rear = (rear + 1) % capacity;
        notifyAll();
        return true;
    }

    public synchronized int dequeue() throws InterruptedException {
        return dequeue(-1);
    }

    /**
     * @param maxWaitTime
     * @return
     * @throws InterruptedException
     */
    public synchronized int dequeue(int maxWaitTime) throws InterruptedException {
        while (isEmpty()) {
            if (maxWaitTime > 0) {
                wait(maxWaitTime);
                if (isEmpty()) {
                    return -1;
                }
            } else {
                wait();
            }
        }
        int result = queue[front % capacity];
        front = (front + 1) % capacity;
        notifyAll();
        return result;
    }

    /**
     * @return
     */
    public synchronized boolean isFull() {
        if (front == (rear + 1) % capacity) {
            return true;
        }
        return false;
    }

    /**
     * @return
     */
    public synchronized boolean isEmpty() {
        if (front == rear) {
            return true;
        }
        return false;
    }

    /**
     * 1.如果rear > front,则size=rear-front
     * 2.如果rear < front,则size=capacity-front+rear
     * @return
     */
    public synchronized int getSize() {
        return (rear > front) ? (rear - front) : (capacity - front + rear);
    }

    public String printQueue() {
        StringBuilder result = new StringBuilder("");
        int i = front;
        while (i != rear) {
            result.append(i + ":" + queue[i] + "\t");
            i = (i + 1) % capacity;
        }
        return result.toString();
    }

    public static void main(String[] args) {
        EventQueue que = new EventQueue(4);
        try {
            que.enqueue(1);
            System.out.println(que.printQueue());
            que.enqueue(2);
            System.out.println(que.printQueue());
            que.enqueue(3);
            System.out.println(que.printQueue());
            que.dequeue();
            System.out.println(que.printQueue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
