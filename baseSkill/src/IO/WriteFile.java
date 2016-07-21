package IO;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by liushanyong on 16/5/23.
 */
public class WriteFile {

    public static void main(String[] args) throws IOException {
        String fileName = "/Users/didi/Documents/test.txt";
        RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
        while (true) {
            try {
                randomFile.writeChars("123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
