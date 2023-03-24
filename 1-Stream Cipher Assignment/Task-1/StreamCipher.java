import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * StreamCipher is a program that takes a plaintext message of a sequence of bytes and an encryption key as input.
 * The output is a ciphertext consisting of a sequence of bytes.
 */
public class StreamCipher {
    //The first argument is the encryption key, given as an integer (decimal) number.
    private static long key;
    //read the input from the file with the name given as the second argument <infile>
    private static FileInputStream input;
    //write the resulting output to a file named <outfile>
    private static FileOutputStream output;

    public static void encryptDecrypt(String infile, String outfile, long key) {
        try {
            input = new FileInputStream(infile);
            output = new FileOutputStream(outfile);
            Random prng = new Random(key);

            int currentByte;
            while ((currentByte = input.read()) != -1) {
                int xorByte = currentByte ^ prng.nextInt(256);
                output.write(xorByte);
            }
            System.out.println("Operation completed successfully.");
        } catch (IOException e) {
            System.err.println("Error reading or writing files: " + e.getMessage());
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.flush();
                    output.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing input or output stream: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: StreamCipher <key> <inputFile> <outputFile>");
            System.exit(1);
        }

        try {
            key = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid key: " + e.getMessage());
            System.exit(1);
        }

        String inputFile = args[1];
        String outputFile = args[2];
        encryptDecrypt(inputFile, outputFile, key);
    }
}
