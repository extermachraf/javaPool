package day02.ex02;

import java.io.File;
import java.io.IOException;

public class Shell {
    private File currentDirectory;
    private File rootDirectory;

    public Shell(String rootDirectory) throws CustomException {
        this.currentDirectory = new File(rootDirectory);
        this.rootDirectory = new File(rootDirectory);
        if (!currentDirectory.isDirectory())
            throw new CustomException("Invalid root directory: " + rootDirectory);
    }

    public void handleCmds(String line) throws IOException {
        String[] splitline = line.trim().split(" ", 2);
        String cmd = splitline[0];

        switch (cmd) {
            case "cd":
                if (splitline.length > 1) {
                    navigateDir(splitline);
                } else {
                    System.out.println("cd: missing argument");
                }
                break;
            case "ls":
                listFiles(splitline);
                break;
            case "mv":
                System.out.println("mv the file");
                break;
            case "exit":
                System.out.println("exit from shell");
                break;
            default:
                System.out.println("Unknown command: " + cmd);
                break;
        }
    }

    // implement the ls cmd
    private void listFiles(String[] splitline) {
        if (splitline.length > 1) {
            System.out.println("Unknown argument for ls");
            return;
        }
        File[] files = currentDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                System.out.println(file.getName() + " " + (file.length() / 1024) + " - KB");
            }
        } else {
            System.out.println("Failed to list files");
        }
    }

    // implemetation od cd
    private void navigateDir(String[] splitline) throws IOException {
        if (splitline.length != 2) {
            System.err.println("Unknown argument for cd");
            return;
        }
        File nextDir = new File(splitline[1]);
        // check if the next directory is a valide direcory
        if (!nextDir.isAbsolute()) {
            nextDir = new File(currentDirectory, splitline[1]);
        }
        String canonicalRoot = rootDirectory.getCanonicalPath();
        String canonicalNextDir = nextDir.getCanonicalPath();

        System.out.println("Root Directory: " + canonicalRoot);
        System.out.println("Next Directory: " + canonicalNextDir);

        if (!canonicalNextDir.startsWith(canonicalRoot)) {
            throw new IOException();
        }
        if (nextDir.isDirectory()) {
            currentDirectory = nextDir;
        } else {
            System.err.println("cd: not a directory: " + splitline[1]);
        }
    }
}
