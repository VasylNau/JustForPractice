package nio.experiments;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class FileLoader {

    private static final String TEST_FILE_NAME = "input.txt";

    public RandomAccessFile getTestFile() throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        RandomAccessFile file = new RandomAccessFile(classLoader.getResource(TEST_FILE_NAME).getFile(), "rw");
        return file;
    }
}
