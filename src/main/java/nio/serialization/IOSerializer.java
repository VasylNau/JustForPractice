package nio.serialization;

import nio.tools.FileLoader;

import java.io.*;

public class IOSerializer {

    private FileLoader fileLoader = new FileLoader();

    public void serialize(Serializable object, String filePath) throws IOException {
        File file = fileLoader.loadFileFromClassPath(filePath);

        try(FileOutputStream fileOutStream = new FileOutputStream(file);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(fileOutStream)) {
            objectOutStream.writeObject(object);
            objectOutStream.flush();
        }
    }

    public <T extends Serializable> T deserialize(Class<T> objectType, String filePath)
            throws IOException, ClassNotFoundException {

        File file = fileLoader.loadFileFromClassPath(filePath);

        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            ObjectInputStream objectInStream = new ObjectInputStream(fileInputStream);
            return objectType.cast(objectInStream.readObject());
        }
    }

}
