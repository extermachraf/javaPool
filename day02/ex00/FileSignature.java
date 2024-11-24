
package day02.ex00;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.io.BufferedReader;
import java.io.File;
import java.util.Arrays;

public class FileSignature {
    public static Map<String, byte[]> signatures = new HashMap<>();

    // constructor
    public FileSignature(String filePath) throws IOException {
        BufferedReader reader = null;

        try {
            FileInputStream fis = new FileInputStream(filePath);
            reader = new BufferedReader(new InputStreamReader(fis));
            String line;

            // Reading each line from the signatures file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");

                // Ensure that parts[0] is the file type and parts[1] is the hex string
                if (parts.length == 2) {
                    String fileType = parts[0].trim(); // Trim the file type
                    String hexSignature = parts[1].trim(); // Trim the hex string

                    // Convert the hex signature string to a byte array
                    byte[] byteSignature = HexStringToByteArray(hexSignature);
                    signatures.put(fileType, byteSignature);
                } else {
                    System.out.println("Invalid signature format in file: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + "'" + filePath + "'");
            System.exit(0);
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                System.out.println("An error occurred while closing the file: " + "'" + filePath + "'");
                return;
            }
        }
    }

    // geter
    public Map<String, byte[]> getSignatures() {
        return signatures;
    }

    // display the file signature
    public static void displaySignature(String sig) {
        if (signatures.containsKey(sig)) {
            System.out.println("HEX Signature for " + sig + " is " + bytesToHex(signatures.get(sig)));
        } else {
            System.out.println("No signature found for " + sig);
        }
    }

    // convert hex string to a byte array
    public static byte[] HexStringToByteArray(String hex) {
        String[] hexValues = hex.split(" ");
        byte[] bytes = new byte[hexValues.length];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hexValues[i], 16);
        }
        return bytes;
    }

    // convert a byte array to a hex string
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02X", b));
        }
        return hexString.toString();
    }

    public String returnTypeOfFile(String FilePath) {
        try (FileInputStream file = new FileInputStream(FilePath)) {
            for (Map.Entry<String, byte[]> entry : signatures.entrySet()) {
                file.getChannel().position(0);

                byte[] signatureFromFile = new byte[entry.getValue().length];
                int bytesRead = file.read(signatureFromFile);

                if (bytesRead != entry.getValue().length)
                    continue;
                if (Arrays.equals(signatureFromFile, entry.getValue())) {
                    System.out.println("PROCESSED");
                    return entry.getKey();
                }
            }
        } catch (IOException io) {
            System.err.println("an errore occured" + io.getMessage());
            System.exit(1);
        }
        System.out.println("UNDEFINED TYPE");
        return "Undefined";
    }

    public void store_data(String filePathToStore, String filepathToCheck) throws IOException {
        String dataToStore = filepathToCheck + " have a type of " + returnTypeOfFile(filepathToCheck) + "\n";
        try (FileOutputStream file = new FileOutputStream(filePathToStore, true)) {
            byte b[] = dataToStore.getBytes();
            file.write(b);
        } catch (IOException e) {
            System.err.println("an eroore occured while writing data to" + filepathToCheck);
            System.exit(1);
        }
    }
}
