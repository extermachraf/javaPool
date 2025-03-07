package fr._42.ReadSignatures;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ReadSignatures {
    public static Map<FileType, String> storeTypes() throws IOException {
        Map<FileType, String> types = new HashMap<>();
        String line;

        // Initialize reader inside the static method
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(ReadSignatures.class.getResourceAsStream("/Signatures.txt"))))) {
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) continue;  // Skip comment lines
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    FileType fileType = FileType.valueOf(parts[0].trim());
                    String signature = parts[1].trim().replace(" ", "").toUpperCase();
                    types.put(fileType, signature);
                }
            }
        }
        return types;
    }
}
