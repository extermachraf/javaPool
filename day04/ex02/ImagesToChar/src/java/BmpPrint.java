package ex02.ImagesToChar.src.java;

import com.diogonunes.jcdp.color.api.Ansi;
import com.diogonunes.jcdp.color.ColoredPrinter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class BmpPrint {
    private Ansi.BColor background1;
    private Ansi.BColor background2;
    BufferedImage image;

    public BmpPrint(Ansi.BColor background1, Ansi.BColor background2) throws IOException, CustomException {
        // Load image from JAR as a resource
        InputStream imageStream = getClass().getResourceAsStream("/resources/it.bmp");
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
        this.background1 = background1;
        this.background2 = background2;
    }

    public char[][] imageToString() throws CustomException {
        char[][] image = new char[this.image.getHeight()][this.image.getWidth()];

        for (int yPixel = 0; yPixel < this.image.getHeight(); yPixel++) {
            for (int xPixel = 0; xPixel < this.image.getWidth(); xPixel++) {
                int color = this.image.getRGB(xPixel, yPixel);
                if (color == Color.BLACK.getRGB()) {
                    image[yPixel][xPixel] = 'B'; // Use a block for black pixels
                } else if (color == Color.WHITE.getRGB()) {
                    image[yPixel][xPixel] = 'W'; // Use space for white pixels
                } else {
                    throw new CustomException("The image must contain only black and white pixels");
                }
            }
        }
        return image;
    }

    public void displayImage(char[][] arr) {
        ColoredPrinter printer = new ColoredPrinter.Builder(1, false)
                .build();

        // Define the Unicode characters for black and white squares

        // Print the image with the background color only (foreground set to black)
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == 'B') { // Black pixel
                    // Use background1 for black pixels and foreground set to black
                    printer.print(" ", Ansi.Attribute.NONE, Ansi.FColor.BLACK, this.background1);
                } else { // White pixel
                    // Use background2 for white pixels and foreground set to black
                    printer.print(" ", Ansi.Attribute.NONE, Ansi.FColor.BLACK, this.background2);
                }
            }
            printer.clear();
            System.out.println(); // Newline after each row
        }
    }

}
