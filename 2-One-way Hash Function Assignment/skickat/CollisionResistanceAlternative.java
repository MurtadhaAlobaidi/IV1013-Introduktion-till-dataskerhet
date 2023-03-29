import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

public class CollisionResistanceAlternative {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPlease enter your message to digest:");
        String inputMessage = scanner.nextLine();
        scanner.close();

        try {
            byte[] hashedMessage = hashMessage(inputMessage);
            performBruteForce(hashedMessage);
        } catch (NoSuchAlgorithmException | IOException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        }

        System.exit(0);
    }

    private static byte[] hashMessage(String message) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        return messageDigest.digest(message.getBytes(StandardCharsets.UTF_8));
    }

    private static void performBruteForce(byte[] originalHash) throws NoSuchAlgorithmException, IOException {
        System.out.print("\nBrute-forcing...");
        int attempts = 0;
        byte[] currentHash;

        do {
            attempts++;
            if (attempts % 900000 == 0) {
                System.out.print(".");
            }
            currentHash = hashMessage(Arrays.toString(byteArrayFromLong(attempts)));
        } while (!partialHashesMatch(originalHash, currentHash));

        displayResults(originalHash, currentHash, attempts);
    }

    private static byte[] byteArrayFromLong(long value) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeLong(value);
        dataOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    private static boolean partialHashesMatch(byte[] hash1, byte[] hash2) {
        return hash1[0] == hash2[0] && hash1[1] == hash2[1] && hash1[2] == hash2[2];
    }

    private static void displayResults(byte[] originalHash, byte[] bruteForcedHash, int attempts) {
        System.out.println("\n\nIt took [" + attempts + "] trials to generate a digest");
        System.out.print("\nOriginal digest:      0x");
        displayPartialHash(originalHash);
        System.out.print("\nBrute-forced digest: 0x");
        displayPartialHash(bruteForcedHash);
        System.out.println("\n\n");
    }

    private static void displayPartialHash(byte[] hash) {
        for (int i = 0; i < 3; i++) {
            System.out.format("%02x", hash[i] & 0xff);
        }
    }
}
