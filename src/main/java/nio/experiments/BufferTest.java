package nio.experiments;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferTest {

    public static void main(String[] args) throws IOException {
        FileLoader fileLoader = new FileLoader();
        RandomAccessFile file = fileLoader.getTestFile();

        FileChannel channel = file.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        int bytesRead = channel.read(byteBuffer); //read into buffer.

        while (bytesRead != -1) {
            System.out.println("Bytes read: " + bytesRead);
            byteBuffer.flip();  //make buffer ready for read

            while(byteBuffer.hasRemaining()){
                System.out.print((char) byteBuffer.get()); // read 1 byte at a time
            }
            System.out.println();
            byteBuffer.clear(); //make buffer ready for writing
            bytesRead = channel.read(byteBuffer);
        }

        byteBuffer.clear();
        System.out.println("Buffer size: " + byteBuffer.position());
        byteBuffer.put((byte) 64);
        System.out.println("Buffer size: " + byteBuffer.position());
        System.out.println(channel.write(byteBuffer));
        file.close();
    }

}
