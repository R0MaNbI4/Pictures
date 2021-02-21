import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;

public class Picture {
    private int imageWidth;
    private int imageHeight;
    private int pictureWidth;
    private int pictureHeight;
    private BigInteger rgbNumber;
    private BigInteger indexNumber;
    private BufferedImage picture;
    private BufferedImage image;
    private colors.Color color;
    private int colorBitLength;
    private int numberBitIndex;
    private Window window;

//    public Picture(int width, int height, int colorNumber, String number) {
//        this.width = width;
//        this.height = height;
//        this.number = new BigInteger(number);
//        colorBitLength = getBitLength(colorNumber);
//        color = new colors.Color(colorNumber);
//        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//    }

    public Picture () {
    }

    public BufferedImage getPicture() {
        return picture;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void readPictureFromImage(File image) {
        loadImage(image);
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                sb.append(decToBool(getColorFromImage(x, y)));
            }
        }
        rgbNumber = new BigInteger(sb.toString(), 2);
        pictureWidth = imageWidth;
        pictureHeight = imageHeight;
    }

    public void readPictureFromImage(String path) {
        loadImage(path);
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                sb.append(decToBool(getColorFromImage(x, y)));
            }
        }
        rgbNumber = new BigInteger(sb.toString(), 2);
        pictureWidth = imageWidth;
        pictureHeight = imageHeight;
    }

    public void savePictureToFile(String path) {
        try (FileWriter file = new FileWriter(path)) {
            file.write(pictureWidth + " " + pictureHeight + "\n");
            file.write(boolToHex(rgbNumber));
        } catch (IOException e) {
            System.out.println("Не удалось записать RGBNumber в файл " + path);
        }
    }

    public void savePictureToFile(File file) {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(pictureWidth + " " + pictureHeight + "\n");
            fw.write(boolToHex(rgbNumber));
        } catch (IOException e) {
            System.out.println("Не удалось записать RGBNumber в файл " + file.getPath());
        }
    }

    public void loadPictureFromFile(String path) {

    }

    public void loadPictureFromFile(File file) {
        loadRGBNumber(file);
        loadPictureFromRGBNumber();
    }

    private void loadPictureFromRGBNumber() {
        picture = new BufferedImage(pictureWidth, pictureHeight, BufferedImage.TYPE_INT_ARGB);
        int pixelNum = 1;
        for (int x = 0; x < pictureWidth; x++) {
            for (int y = 0; y < pictureHeight; y++) {
                picture.setRGB(x, y, getColorFromRGBNumber(pixelNum));
                pixelNum++;
            }
        }
    }

    private void loadRGBNumber(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String[] parameters = br.readLine().split("\\s");
            pictureWidth = Integer.parseInt(parameters[0]);
            pictureHeight = Integer.parseInt(parameters[1]);
            int symbol = br.read();
            StringBuilder sb = new StringBuilder();
            while (symbol != -1) {
                sb.append((char) symbol);
                symbol = br.read();
            }
            rgbNumber = new BigInteger(sb.toString(), 16);
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл " + file.getPath());
        }
    }

    public void savePictureIndexToFile(String path) {

    }

    public void savePictureIndexToFile(File file) {

    }

    public void loadPictureFromIndexFile(String path) {

    }

    public void loadPictureFromIndexFile(File file) {

    }

    private void loadImage(File image) {
        try {
            this.image = ImageIO.read(image);
            imageWidth = this.image.getWidth();
            imageHeight = this.image.getHeight();
        } catch (IOException e) {
            System.out.println("Не удалось загрузить изображение: " + image.getPath());
        }
    }

    private void loadImage(String path) {
        try {
            this.image = ImageIO.read(new File(path));
            imageWidth = this.image.getWidth();
            imageHeight = this.image.getHeight();
        } catch (IOException e) {
            System.out.println("Не удалось загрузить изображение: " + path);
        }
    }

    private int getColorFromImage(int x, int y) {
        // argb
        return image.getRGB(x, y);
    }

    private int getColorFromRGBNumber(int pixelNum) {
        int argb = 0;
        for (int i = 31; i >= 0; i--) {
            int numBit = (pixelNum - 1) * 32 + i;
            int bit = rgbNumber.testBit(numBit) ? 1 : 0;
            argb |= i << bit;
        }
        return argb;
    }

    private String decToBool(int num) {
        return Integer.toBinaryString(num);
    }

    private String boolToHex(BigInteger num) {
        return num.toString(16).toUpperCase();
    }

    private String boolToHex(String num) {
        return new BigInteger(num, 2).toString(16).toUpperCase();
    }

//    public void reDraw(BigInteger number) {
//        this.number = number;
//        definePicture();
//    }
//
//    public void reDraw(String number) {
//        this.number = new BigInteger(number);
//        definePicture();
//    }
//
//    private void definePicture() {
//        numberBitIndex = 0;
//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                bufferedImage.setRGB(i, j, color.getColor(getColorIndexOfNextPixel()));
//            }
//        }
//    }
//
//    private int getColorIndexOfNextPixel() {
//        String colorIndex = "";
//        for (int i = numberBitIndex; i < (numberBitIndex + colorBitLength); i++) {
//            colorIndex += getBit(number, i);
//        }
//        numberBitIndex += colorBitLength;
//        return Integer.valueOf(colorIndex, 2);
//    }

    private int getBit(BigInteger num, int index) {
        return num.testBit(index) ? 1 : 0;
    }

    private int getBitLength(int num) {
        return (int) (Math.log10(num) / Math.log10(2));
    }
}
