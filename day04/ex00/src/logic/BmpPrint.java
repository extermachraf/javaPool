package ex00.src.logic;

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

        System.out.println("image is : " + this.image);
        System.out.println("black is : " + this.black);
        System.out.println("white is : " + this.white);
    }
}
