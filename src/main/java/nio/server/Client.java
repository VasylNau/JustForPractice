package nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 9999));

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        Scanner scanner = new Scanner(System.in);
        String message = "";

        while (channel.isConnected() && !message.equals("exit")) {
            message = scanner.nextLine();
            byteBuffer.put(message.getBytes(Charset.defaultCharset()));

            byteBuffer.flip();
            channel.write(byteBuffer);

            byteBuffer.clear();
        }

    }
}
