import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StreamCipher2 {

    private static int key;

    /**
     * Encrypts or decrypts the input file using Caesar cipher and writes the result to the output file.
     *
     * @param inputFile  The input file to be encrypted or decrypted.
     * @param outputFile The output file to store the result.
     * @param key        The key used for the encryption or decryption process.
     */
    public static void encryptDecrypt(String inputFile, String outputFile, int key) {
        try {
            // Create Paths for input and output files
            Path inputPath = Paths.get(inputFile);
            Path outputPath = Paths.get(outputFile);

            // Read input file into a byte array
            byte[] inputBytes = Files.readAllBytes(inputPath);
            byte[] outputBytes = new byte[inputBytes.length];

            // Perform the Caesar cipher operation on each byte of the input
            for (int i = 0; i < inputBytes.length; i++) {
                outputBytes[i] = (byte) (inputBytes[i] + key);
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
            key = Integer.parseInt(args[0]);
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
