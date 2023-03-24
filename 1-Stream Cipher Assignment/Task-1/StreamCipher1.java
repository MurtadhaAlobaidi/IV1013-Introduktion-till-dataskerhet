import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class StreamCipher1 {

    private static BigInteger key;
    /**
     * Encrypts or decrypts the input file using XOR and writes the result to the output file.
     *
     * @param inputFile  The input file to be encrypted or decrypted.
     * @param outputFile The output file to store the result.
     * @param key        The key used for the encryption or decryption process.
     */
    public static void encryptDecrypt(String inputFile, String outputFile, BigInteger key) {
        try {
            // Create Paths for input and output files
            // I take inspiration to use "Path" from https://blogs.oracle.com/javamagazine/post/path-files-input-output
            Path inputPath = Paths.get(inputFile);
            Path outputPath = Paths.get(outputFile);

            // Read input file into a byte array
            byte[] inputBytes = Files.readAllBytes(inputPath);
            byte[] outputBytes = new byte[inputBytes.length];

            // Initialize the pseudo-random number generator with the key
            Random prng = new Random(key.longValue());

            // Perform the XOR operation on each byte of the input
            for (int i = 0; i < inputBytes.length; i++) {
                outputBytes[i] = (byte) (inputBytes[i] ^ prng.nextInt(256));
            }

            // Write the result to the output file
            Files.write(outputPath, outputBytes);
            System.out.println("Operation completed successfully.");
        } catch (IOException e) {
            System.err.println("Error reading or writing files: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Check if the correct number of arguments are provided
        if (args.length == 3) {
        } else {
            System.out.println("Usage: StreamCipher <key> <inputFile> <outputFile>");
            System.exit(1);
        }

        // Parse the key from the first argument
        try {
            key = new BigInteger(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid key: " + e.getMessage());
            System.exit(1);
        }

        // Read the input and output file names from the arguments
        String inputFile = args[1];
        String outputFile = args[2];

        // Call the encryptDecrypt method to perform the operation
        encryptDecrypt(inputFile, outputFile, key);
    }
}
