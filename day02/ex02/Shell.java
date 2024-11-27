package day02.ex02;

import java.io.File;

public class Shell {
    private File currentDirectory;

    public Shell(String rootDirectory) throws CustomException {
        this.currentDirectory = new File(rootDirectory);
        if (!currentDirectory.isDirectory())
            throw new CustomException("Invalid root directory: " + rootDirectory);
    }

    public void handleCmds(String line) {
        String[] splitline = line.trim().split(" ", 2);
        String cmd = splitline[0];

        switch (cmd) {
            case "cd":
                if (splitline.length > 1) {
                    System.out.println("change directory");
                } else {
                    System.out.println("cd: missing argument");
                }
                break;
            case "ls":
                System.out.println("List files");
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
}
