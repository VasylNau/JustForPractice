package nio.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class FileLoader {

    public RandomAccessFile loadRAFileFromClassPath(String filename) throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        RandomAccessFile file = new RandomAccessFile(classLoader.getResource(filename).getFile(), "rw");
        return file;
    }

    public File loadFileFromClassPath(String filename) throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(filename).getFile());
    }
}
