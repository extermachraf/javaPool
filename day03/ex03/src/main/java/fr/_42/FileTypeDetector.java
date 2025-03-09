package fr._42;

class FileTypeDetector {
    public String getFileExtension(String contentType, String path) {
        if (contentType != null) {
            contentType = contentType.toLowerCase();
            if (contentType.contains("image/jpeg") || contentType.contains("image/jpg")) {
                return ".jpg";
            } else if (contentType.contains("image/png")) {
                return ".png";
            } else if (contentType.contains("image/gif")) {
                return ".gif";
            } else if (contentType.contains("application/pdf")) {
                return ".pdf";
            } else if (contentType.contains("text/html")) {
                return ".html";
            } else if (contentType.contains("text/plain")) {
                return ".txt";
            } else if (contentType.contains("application/zip")) {
                return ".zip";
            } else if (contentType.contains("application/x-gzip")) {
                return ".gz";
            } else if (contentType.contains("application/json")) {
                return ".json";
            } else if (contentType.contains("application/xml") || contentType.contains("text/xml")) {
                return ".xml";
            } else if (contentType.contains("audio/mpeg")) {
                return ".mp3";
            } else if (contentType.contains("video/mp4")) {
                return ".mp4";
            } else if (contentType.contains("application/javascript")) {
                return ".js";
            } else if (contentType.contains("text/css")) {
                return ".css";
            }
        }

        // Fallback: extract from URL path if content type didn't help
        if (path != null && path.contains(".")) {
            return path.substring(path.lastIndexOf("."));
        }

        // Default extension if we couldn't determine it
        return ".dat";
    }
}
