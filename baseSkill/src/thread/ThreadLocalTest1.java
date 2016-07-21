package thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by didi on 16/7/21.
 */
public class ThreadLocalTest1 {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    static class PrintDate implements Runnable {
        private int i;

        public PrintDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                Date date = simpleDateFormat.parse("2016-07-21" + " " + "10:10:" + i % 60);
                System.out.println(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Executor executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executor.execute(new PrintDate(i));
        }
    }


}
