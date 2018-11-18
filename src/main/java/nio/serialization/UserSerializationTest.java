package nio.serialization;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class UserSerializationTest {

    private static final String USER_LIST_FILE = "users/user_lists.txt";
    private static final String USERS_FILE = "users/users.txt";

    public static void main(String[] args) {
        testSingleUserSerialization();
        testUserListSerialization();
    }

    private static void testSingleUserSerialization() {
        System.out.println("\n===== User serialization test started =====\n");

        User user = new User(1L, "User", "user@test.com");
        LocalDateTime birthDate = LocalDateTime.now();
        user.setDateOfBirth(birthDate);
        user.setPassportID("AB 123456");

        System.out.println("Created user: " + user);
        System.out.println("Starting serialization");

        NIOSerializer nioSerializer = new NIOSerializer();
        try {
            nioSerializer.serialize(user, USERS_FILE);
        } catch (IOException e) {
            System.out.println("An error occurred while serializing a user object");
            e.printStackTrace();
        }

        System.out.println("User serialized");
        System.out.println("Starting deserialization");

        User deserializedUser = null;
        try {
            deserializedUser = nioSerializer.deserialize(User.class, USERS_FILE);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("An error occurred while deserializing a user object");
            e.printStackTrace();
        }

        System.out.println("Deserialized user: " + deserializedUser);

        System.out.println("\n===== User serialization test finished =====\n");
    }

    private static void testUserListSerialization() {
        System.out.println("\n===== User array serialization test started =====\n");

        User[] userArray = createUsers();

        System.out.println("Created user array with size: " + userArray.length);
        Arrays.stream(userArray).forEach(System.out::println);
        System.out.println("Starting serialization");

        NIOSerializer nioSerializer = new NIOSerializer();
        try {
            nioSerializer.serialize(userArray, USER_LIST_FILE);
        } catch (IOException e) {
            System.out.println("An error occurred while serializing a user array");
            e.printStackTrace();
        }

        System.out.println("User array serialized");
        System.out.println("Starting deserialization");

        User[] deserializedUserArray = null;
        try {
            deserializedUserArray = nioSerializer.deserialize(User[].class, USER_LIST_FILE);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("An error occurred while deserializing a user array");
            e.printStackTrace();
        }

        System.out.println("Deserialized user array: ");
        Arrays.stream(deserializedUserArray).forEach(System.out::println);

        System.out.println("\n===== User array serialization test finished =====\n");
    }

    private static User[] createUsers() {
        Random random = new Random();
        User[] userArray = new User[15];
        for (int i = 0; i < userArray.length; i++) {
            User user = new User(i, "User-" + i, "user_" + i + "@test.com");
            LocalDateTime birthDate = LocalDateTime.now();
            user.setDateOfBirth(birthDate);
            user.setPassportID("AB " + (random.nextInt(900_000) + 100_000));
            userArray[i]  = user;
        }
        return userArray;
    }
}
