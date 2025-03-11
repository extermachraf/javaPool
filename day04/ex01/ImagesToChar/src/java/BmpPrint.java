package ex01.ImagesToChar.src.java;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

public class BmpPrint {
    private char black;
    private char white;
    BufferedImage image;

    public BmpPrint(char black, char white) throws IOException, CustomException {
        // Load image from JAR as a resource
        InputStream imageStream = getClass().getResourceAsStream("/it.bmp");
        if (imageStream == null) {
            throw new CustomException("Image not found inside the JAR: it.bmp");
        }

        BufferedImage BMP = ImageIO.read(imageStream);
        if (BMP == null) {
            throw new CustomException("Failed to load the image. Please check the image format.");
        }

        if (BMP.getWidth() != 16 || BMP.getHeight() != 16) {
            throw new CustomException("The image is not in the format of 16x16 pixels.");
        }
        this.image = BMP;
        this.black = black;
        this.white = white;

        // System.out.println("image is : " + this.image);
        // System.out.println("black is : " + this.black);
        // System.out.println("white is : " + this.white);
    }

    public char[][] imageToString() throws CustomException {
        char[][] image = new char[this.image.getHeight()][this.image.getWidth()];

        for (int yPixel = 0; yPixel < this.image.getHeight(); yPixel++) {
            for (int xPixel = 0; xPixel < this.image.getWidth(); xPixel++) {
                int color = this.image.getRGB(xPixel, yPixel);
                if (color == Color.BLACK.getRGB()) {
                    image[yPixel][xPixel] = black;
                } else if (color == Color.WHITE.getRGB()) {
                    image[yPixel][xPixel] = white;
                } else {
                    throw new CustomException("The image must contain only black and white pixels");
                }
            }
        }
        return image;
    }

    public void displayImage(char[][] arr) {
        for (char[] chars : arr) {
            for (char aChar : chars) {
                System.out.print("\u001B[32m" + aChar + "\u001B[0m");
            }
            System.out.println();
        }
    }
}
