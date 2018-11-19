package nio.performance;

import nio.tools.FileLoader;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

public class PerformanceTester {

    private final FileLoader fileLoader = new FileLoader();
    private final String FILE_LOCATION = "performance/test.txt";
    private final Path TEST_FILE_PATH = fileLoader.getPath(FILE_LOCATION);

    public static void main(String[] args) {
        PerformanceTester tester = new PerformanceTester();

        System.out.println("Running buffered stream test with file size 50 MB");
        long testTime = tester.runBufferedStreamWritingTest(20 * 1024 * 1024);
        System.out.printf("Test finished. Time: %d ms\n\n", testTime);

        System.out.println("Running channel test with file size 50 MB");
        testTime = tester.runChannelWritingTest(20 * 1024 * 1024);
        System.out.printf("Test finished. Time: %d ms\n\n", testTime);

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

    private long runBufferedStreamWritingTest(long fileSizeInBytes) {
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
        return endTime - startTime;
    }

    private long runChannelWritingTest(long fileSizeInBytes) {
        prepareTestFile();
        long startTime = System.currentTimeMillis();

        try(FileChannel channel = fileLoader.getClassPathFileChannel(FILE_LOCATION)) {
            ByteBuffer buffer = ByteBuffer.allocate(1 * 1024 * 1024);
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
        return endTime - startTime;
    }
}
