package nio.performance;

import nio.tools.FileLoader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

public class PerformanceTester {

    private final static int BYTES_IN_MB = 1024 * 1024;

    private final FileLoader fileLoader = new FileLoader();
    private final String FILE_LOCATION = "performance/test.txt";
    private final Path TEST_FILE_PATH = fileLoader.getPath(FILE_LOCATION);

    public static void main(String[] args) {
        PerformanceTester tester = new PerformanceTester();

        tester.runBufferedStreamTest(50 * BYTES_IN_MB);
        tester.runChannelTest(50 * BYTES_IN_MB);
        tester.runBufferedStreamTest(100 * BYTES_IN_MB);
        tester.runChannelTest(100 * BYTES_IN_MB);
        tester.runBufferedStreamTest(500 * BYTES_IN_MB);
        tester.runChannelTest(500 * BYTES_IN_MB);
        tester.runBufferedStreamTest(1000 * BYTES_IN_MB);
        tester.runChannelTest(1000 * BYTES_IN_MB);
    }

    private void prepareTestFile() {
        try {
            Files.deleteIfExists(TEST_FILE_PATH);
            Files.createFile(TEST_FILE_PATH);
        } catch (IOException e) {
            System.out.println("Can't Prepare test file");
            System.exit(1);
        }
    }

    private void runBufferedStreamTest(long fileSizeInBytes) {
        System.out.printf("Running writing buffered stream test with file size %d MB\n", fileSizeInBytes / BYTES_IN_MB);
        prepareTestFile();
        long startTime = System.currentTimeMillis();

        try(BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(TEST_FILE_PATH))) {
            for (long i = 0; i < fileSizeInBytes; i++) {
                outputStream.write(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        long testTime = endTime - startTime;

        System.out.printf("Test finished. Time: %d ms\n\n", testTime);

        System.out.printf("Running buffered reading stream test with file size %d MB\n", fileSizeInBytes / BYTES_IN_MB);
        startTime = System.currentTimeMillis();

        try(BufferedInputStream inputStream = new BufferedInputStream(Files.newInputStream(TEST_FILE_PATH))) {
            for (long i = 0; i < fileSizeInBytes; i++) {
                inputStream.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();
        testTime = endTime - startTime;

        System.out.printf("Test finished. Time: %d ms\n\n", testTime);
    }

    private void runChannelTest(long fileSizeInBytes) {
        System.out.printf("Running channel writing test with file size %d MB\n", fileSizeInBytes / BYTES_IN_MB);
        prepareTestFile();
        long startTime = System.currentTimeMillis();

        try(FileChannel channel = fileLoader.getClassPathFileChannel(FILE_LOCATION)) {
            ByteBuffer buffer = ByteBuffer.allocate(2 * 1024 * 1024);
            for (long i = 0; i < fileSizeInBytes;) {
                while (buffer.position() < buffer.limit()) {
                    buffer.put((byte)1);
                    i++;
                }
                buffer.flip();
                channel.write(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        long testTime = endTime - startTime;

        System.out.printf("Writing test finished. Time: %d ms\n\n", testTime);

        System.out.printf("Running channel reading test with file size %d MB\n", fileSizeInBytes / BYTES_IN_MB);
        startTime = System.currentTimeMillis();

        try(FileChannel channel = fileLoader.getClassPathFileChannel(FILE_LOCATION)) {
            ByteBuffer buffer = ByteBuffer.allocate(2 * 1024 * 1024);
            int bytesRead = channel.read(buffer);
            while (bytesRead > -1) {
                buffer.flip();
                buffer.clear();
                bytesRead = channel.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();
        testTime = endTime - startTime;

        System.out.printf("Test finished. Time: %d ms\n\n", testTime);

    }
}
