package fr._42;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final String URLS_FILE = "files_urls.txt";
    private static int threadsCount;
    private static final List<String> urls = new ArrayList<>();
    private static final BlockingQueue<Integer> fileQueue = new LinkedBlockingQueue<>();
    private static final AtomicInteger downloadedCount = new AtomicInteger(0);
    private static final AtomicInteger fileCounter = new AtomicInteger(1);

    public static void main(String[] args) {
        try {
            parseArguments(args);
            loadUrls();

            if (urls.isEmpty()) {
                System.out.println("No URLs found in " + URLS_FILE);
                return;
            }

            // Initialize the queue with file indices
            for (int i = 0; i < urls.size(); i++) {
                fileQueue.put(i);
            }

            // Create and start download threads
            Thread[] threads = new Thread[threadsCount];
            for (int i = 0; i < threadsCount; i++) {
                threads[i] = new Thread(new DownloadTask());
                threads[i].start();
            }

            // Wait for all downloads to complete
            while (downloadedCount.get() < urls.size()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.err.println("Main thread interrupted: " + e.getMessage());
                    break;
                }
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }

    private static void parseArguments(String[] args) {
        boolean threadsCountSet = false;

        for (String arg : args) {
            if (arg.startsWith("--threadsCount=")) {
                try {
                    threadsCount = Integer.parseInt(arg.substring(15));
                    if (threadsCount <= 0) {
                        throw new IllegalArgumentException("Thread count must be positive");
                    }
                    threadsCountSet = true;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid thread count format");
                }
            } else {
                throw new IllegalArgumentException("Unknown argument: " + arg);
            }
        }

        if (!threadsCountSet) {
            throw new IllegalArgumentException("--threadsCount must be specified");
        }
    }

    private static void loadUrls() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(URLS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    urls.add(line);
                }
            }
        }
    }

    static class DownloadTask implements Runnable {
        private final FileTypeDetector fileTypeDetector = new FileTypeDetector();

        @Override
        public void run() {
            while (downloadedCount.get() < urls.size()) {
                Integer fileIndex = null;
                try {
                    // Try to get the next file from the queue (non-blocking)
                    fileIndex = fileQueue.poll();

                    // If no more files, exit
                    if (fileIndex == null) {
                        break;
                    }

                    // Get the file number for display purposes
                    int fileNumber = fileCounter.getAndIncrement();

                    // Start downloading
                    String threadName = Thread.currentThread().getName();
                    String url = urls.get(fileIndex);
                    System.out.println(threadName + " start download file number " + fileNumber);

                    // Perform the download
                    downloadFile(url, fileNumber);

                    // Mark as completed
                    downloadedCount.incrementAndGet();
                    System.out.println(threadName + " finish download file number " + fileNumber);

                } catch (IOException e) {
                    System.err.println(Thread.currentThread().getName() + " error downloading file: " + e.getMessage());

                    // If there was an error, put the file back in the queue
                    if (fileIndex != null) {
                        try {
                            fileQueue.put(fileIndex);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.err.println(Thread.currentThread().getName() + " unexpected error: " + e.getMessage());
                    break;
                }
            }
        }

        private void downloadFile(String fileUrl, int fileNumber) throws IOException {
            URL url = new URL(fileUrl);
            URLConnection connection = url.openConnection();

            // Create a directory for downloads if it doesn't exist
            Path downloadDir = Paths.get("downloads");
            if (!Files.exists(downloadDir)) {
                Files.createDirectories(downloadDir);
            }

            // Get file extension using the FileTypeDetector
            String contentType = connection.getContentType();
            String extension = fileTypeDetector.getFileExtension(contentType, url.getPath());

            // Set the destination path with appropriate extension
            Path destination = downloadDir.resolve(fileNumber + extension);

            // Download the file
            try (InputStream in = connection.getInputStream()) {
                Files.copy(in, destination, StandardCopyOption.REPLACE_EXISTING);
            }

            // Simulate variable download times
            try {
                // Random delay between 1-5 seconds to simulate network variations
                Thread.sleep((long) (Math.random() * 4000 + 1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

