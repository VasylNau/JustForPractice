package nio.serialization;

import nio.tools.FileLoader;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOSerializer {

    private FileLoader fileLoader = new FileLoader();

    public void serialize(Serializable object, String filePath) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream outStream = new ObjectOutputStream(byteStream);
        outStream.writeObject(object);
        byte[] data = byteStream.toByteArray();

        RandomAccessFile file = fileLoader.loadRAFileFromClassPath(filePath);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        FileChannel channel = file.getChannel();

        int position = 0;
        while (data.length > position) {
            buffer.put(data, position, Math.min(buffer.capacity(), data.length - position));
            buffer.flip();
            position += channel.write(buffer);
            buffer.clear();
        }
        channel.close();
        file.close();
    }

    public <T extends Serializable> T deserialize(Class<T> objectType, String filePath) throws IOException, ClassNotFoundException {
        RandomAccessFile file = fileLoader.loadRAFileFromClassPath(filePath);
        FileChannel channel = file.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        byte[] data = new byte[0];
        int bytesRead = channel.read(byteBuffer);

        while (bytesRead > -1) {
            byteBuffer.flip();

            byte[] newArray = new byte[data.length + bytesRead];
            System.arraycopy(data, 0, newArray, 0,data.length);
            System.arraycopy(byteBuffer.array(), 0, newArray, data.length, bytesRead);
            data = newArray;

            byteBuffer.clear();
            bytesRead = channel.read(byteBuffer);
        }
        channel.close();

        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
             ObjectInputStream inputStream = new ObjectInputStream(byteStream)) {
            return objectType.cast(inputStream.readObject());
        }
    }
}
