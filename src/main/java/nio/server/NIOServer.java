package nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIOServer {

    private ServerSocketChannel serverSocketChannel;
    private volatile Selector selector;

    public NIOServer() throws IOException {
        selector = Selector.open();
    }

    public void start() throws IOException {
        InetSocketAddress address = new InetSocketAddress("localhost", 9999);

        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(address);

        ExecutorService socketListenerExecutor = Executors.newSingleThreadExecutor();
        socketListenerExecutor.execute(new SocketListener());

        Set<SelectionKey> selectionKeys;
        while (true) {
            int readyAmount = selector.selectNow();
            if (readyAmount > 0) {
                selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    ByteBuffer buffer = (ByteBuffer)key.attachment();
                    SocketChannel socketChannel = ((SocketChannel)key.channel());
                    socketChannel.socket().setKeepAlive(true);
                    int bytesRead;
                    bytesRead = socketChannel.read(buffer);
                    buffer.flip();
                    System.out.println(new String(buffer.array(), 0, bytesRead, Charset.defaultCharset()));
                    buffer.clear();
                    keyIterator.remove();
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            NIOServer server = new NIOServer();
            server.start();
        } catch (IOException e) {
            System.out.println("ERROR: cannot start server instance");
            e.printStackTrace();
        }
    }

    private class SocketListener implements Runnable {
        @Override
        public void run() {
            while (true) {
                SocketChannel socketChannel;
                try {
                    socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);

                    System.out.println("Trying to register new connection");

                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                    System.out.println("Connection registered");
                } catch (IOException e) {
                    System.out.println("An error occurred while connecting with client");
                    e.printStackTrace();
                }
            }
        }
    }
}
