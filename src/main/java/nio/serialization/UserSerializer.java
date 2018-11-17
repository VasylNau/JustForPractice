package nio.serialization;

import nio.tools.FileLoader;

import java.io.*;
import java.nio.channels.ByteChannel;
import java.nio.channels.Channel;
import java.time.LocalDateTime;
import java.util.List;

public class UserSerializer {

    private static final String USER_LIST_FILE = "users/user_lists.txt";
    private static final String USERS_FILE = "users/users.txt";

    private FileLoader fileLoader = new FileLoader();

    public void serialize(User user) throws IOException {
        File file = fileLoader.loadFileFromClassPath(USERS_FILE);
        ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(file));
        Channel channel =
    }

    public User deserializeUser() {
        return null;
    }

    public void serialize(List<User> userList) {

    }

    public List<User> deserializeUserList() {
        return null;
    }

    public static void main(String[] args) throws IOException {
        User user = new User(1L, "User1", "user@ex.com");
        LocalDateTime birthDate = LocalDateTime.now();
        user.setDateOfBirth(birthDate);
        user.setPassportID("AB 123456");

        System.out.println("Created user: " + user);
        System.out.println("Starting serialization");

        UserSerializer serializer = new UserSerializer();
        serializer.serialize(user);

        System.out.println("User serialized");
        System.out.println("Starting deserialization");

        User deserializedUser = serializer.deserializeUser();
        System.out.println("Deserialized user: " + deserializedUser);
    }
}
