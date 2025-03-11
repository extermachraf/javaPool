package ex00.src.logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class BmpPrint {
    private static final int EXPECTED_BMP_WIDTH = 16;
    private static final int EXPECTED_BMP_HEIGHT = 16;
    private static final byte BMP_SIGNATURE_B = 0x42; // 'B'
    private static final byte BMP_SIGNATURE_M = 0x4D; // 'M'

    private final char black;
    private final char white;
    private final BufferedImage image;

    public BmpPrint(char black, char white, String pathToImage) throws IOException, CustomException {
        this.black = black;
        this.white = white;

        File bmpFile = new File(pathToImage);

        // Validate BMP signature
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(bmpFile);
            byte[] bmpSignature = new byte[2];
            if (fis.read(bmpSignature) != 2 ||
                    bmpSignature[0] != BMP_SIGNATURE_B ||
                    bmpSignature[1] != BMP_SIGNATURE_M) {
                throw new CustomException("Invalid BMP file format. The file doesn't have a BMP signature.");
            }
        } finally {
            closeQuietly(fis);
        }

        // Read the image
        BufferedImage bmpImage = ImageIO.read(bmpFile);
        if (bmpImage == null) {
            throw new CustomException("Failed to read image file. Please check if it's a valid image.");
        }

        // Validate dimensions
        if (bmpImage.getWidth() != EXPECTED_BMP_WIDTH || bmpImage.getHeight() != EXPECTED_BMP_HEIGHT) {
            throw new CustomException(
                    String.format("Image dimensions must be %dx%d pixels, but got %dx%d",
                            EXPECTED_BMP_WIDTH, EXPECTED_BMP_HEIGHT,
                            bmpImage.getWidth(), bmpImage.getHeight())
            );
        }

        this.image = bmpImage;
    }
    public char[][] imageToString() throws CustomException {
        char[][] result = new char[image.getHeight()][image.getWidth()];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixelColor = image.getRGB(x, y);

                if (pixelColor == Color.BLACK.getRGB()) {
                    result[y][x] = black;
                } else if (pixelColor == Color.WHITE.getRGB()) {
                    result[y][x] = white;
                } else {
                    throw new CustomException("The image must contain only black and white pixels.");
                }
            }
        }

        return result;
    }
    public void displayImage(char[][] arr) {
        for (char[] chars : arr) {
            for (char aChar : chars) {
                System.out.print("\u001B[32m" + aChar + "\u001B[0m");
            }
            System.out.println();
        }
    }
    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // Suppress exception
            }
        }
    }
}