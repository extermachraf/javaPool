package fr._42.handleSignatures;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class FileTypeIdentifier {
    public static String getFileType(String path, Map<FileType, String> types) throws IOException {
        File file = new File(path);
        try(FileInputStream fis = new FileInputStream(file)){
            byte[] signature = new byte[8];
            int isRead = fis.read(signature);
            if (isRead != signature.length) throw new IOException("Invalid signature");

            String signatureHex = bytesToHex(signature).toUpperCase();
            for (Map.Entry<FileType, String> entry : types.entrySet()) {
                if (signatureHex.startsWith(entry.getValue().toUpperCase())) {
                    return entry.getKey().name();  // Return the file type
                }
            }
        }
        return "Unknown";
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for(byte b : bytes){
            String hex = Integer.toHexString(0xff & b);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
