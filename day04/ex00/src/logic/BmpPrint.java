package ex00.src.logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

public class BmpPrint {
    private char black;
    private char white;
    BufferedImage image;

    public BmpPrint(char black, char white, String pathToImage) throws IOException, CustomException {
        File BmpFile = new File(pathToImage);

        byte[] bmpSignature = new byte[2];
        BufferedImage BMP = ImageIO.read(BmpFile);
        if (BMP == null)
            throw new CustomException("you didnt provide an image file please check the path of image");

        FileInputStream fis = new FileInputStream(BmpFile);
        if (fis.read(bmpSignature) != 2)
            throw new CustomException("you need to provide a BMP image");
        if (!(bmpSignature[0] == 0x42 && bmpSignature[1] == 0x4D))
            throw new CustomException("the signature of the file dont match the signature of a BMP file");

        if (BMP.getWidth() != 16 || BMP.getHeight() != 16) {
            throw new CustomException("the image is not in the format of 16x16 pixels");
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
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print("\u001B[32m" + arr[i][j] + "\u001B[0m");
            }
            System.out.println();
        }
    }
}
