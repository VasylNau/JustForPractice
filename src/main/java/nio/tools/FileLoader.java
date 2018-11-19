package nio.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

public class FileLoader {

    public RandomAccessFile loadRAFileFromClassPath(String filename) throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        RandomAccessFile file = new RandomAccessFile(classLoader.getResource(filename).getFile(), "rw");
        return file;
    }

    public File loadFileFromClassPath(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(filename).getPath());
    }

    public FileChannel getClassPathFileChannel(String filePath) throws FileNotFoundException {
        return loadRAFileFromClassPath(filePath).getChannel();
    }

    public Path getPath(String filePath) {
        return loadFileFromClassPath(filePath).toPath();
    }
}
