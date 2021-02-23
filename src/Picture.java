import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;

public class Picture {
    private int imageWidth;
    private int imageHeight;
    private int pictureWidth;
    private int pictureHeight;
    private String rgbNumber;
    private BigInteger indexNumber;
    private BufferedImage picture;
    private BufferedImage image;

    public BufferedImage getPicture() {
        return picture;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void imageToFile(String image) {
        boolean isExtensionSpecified = image.split("\\.").length > 1; // Указано ли расширение
        if (isExtensionSpecified) {
            readPictureFromImage(image);
            String rgbNumberFileName = image.split("\\.")[0] + "RGB.txt";
            savePictureToFile(rgbNumberFileName);
        } else {
            if (new File(image + ".png").exists()) {
                readPictureFromImage(image + ".png");
            }
            else if (new File(image + ".jpg").exists()) {
                readPictureFromImage(image + ".jpg");
            }
            String rgbNumberFileName = image + "RGB.txt";
            savePictureToFile(rgbNumberFileName);
        }
    }

    public void readPictureFromImage(String path) {
        loadImage(path);
        StringBuilder sb = new StringBuilder();
        String hexNum;
        int boolNum;
        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                boolNum = image.getRGB(x, y);
                sb.append(Integer.toHexString(boolNum));
            }
        }
        rgbNumber = sb.toString();
        pictureWidth = imageWidth;
        pictureHeight = imageHeight;
    }

    public void savePictureToFile(String path) {
        try (FileWriter file = new FileWriter(path)) {
            file.write(pictureWidth + " " + pictureHeight + "\n");
            file.write(rgbNumber);
        } catch (IOException e) {
            System.out.println("Не удалось записать RGBNumber в файл " + path);
        }
    }

    public void loadPictureFromFile(String path) {
        rgbNumber = "";
        if (path.split("\\.").length == 1) {
            path = path + "RGB.txt";
        }
        loadRGBNumber(path);
        loadPictureFromRGBNumber();
    }

    private void loadPictureFromRGBNumber() {
        picture = new BufferedImage(pictureWidth, pictureHeight, BufferedImage.TYPE_INT_RGB);
        int pixelNum = 1;
        int byteLength = 8; // R,G,B,A = 1 byte = 8 bit
        for (int x = 0; x < pictureWidth; x++) {
            for (int y = 0; y < pictureHeight; y++) {
                try {
                    String hexNum = rgbNumber.substring((pixelNum - 1) * byteLength, (pixelNum - 1) * byteLength + byteLength);
                    int color = (int) Long.parseLong(hexNum, 16);
                    picture.setRGB(x, y, color);
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("Файл короче изображения");
                    picture.setRGB(x, y, 0);
                }
                pixelNum++;
            }
        }
    }

    private void loadRGBNumber(String path) {
        File file = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String[] parameters = br.readLine().split("\\s");
            pictureWidth = Integer.parseInt(parameters[0]);
            pictureHeight = Integer.parseInt(parameters[1]);

            int startSubString = parameters[0].length() + parameters[1].length() + 1;
            String content = Files.lines(Paths.get(file.getName())).reduce("", String::concat);
            rgbNumber = content.substring(startSubString);
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл " + file.getPath());
        }
    }

    public void savePictureIndexToFile(String path) {
        if (path.split("\\.").length == 1) {
            path = path + "Index.txt";
        }

        long timer = System.currentTimeMillis();
        indexNumber = new BigInteger(rgbNumber, 16);
        try (FileWriter file = new FileWriter(path)) {
            file.write(pictureWidth + " " + pictureHeight + "\n");
            file.write(indexNumber.toString());
        } catch (IOException e) {
            System.out.println("Не удалось записать IndexNumber в файл " + path);
        }
        System.out.println(System.currentTimeMillis() - timer);
    }

    public void loadPictureFromIndexFile(String path) {

    }

    private void loadPictureFromIndexNumber() {

    }

    private void loadIndexNumber(String path) {

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

    private int getBit(BigInteger num, int index) {
        return num.testBit(index) ? 1 : 0;
    }

    private int getBitLength(int num) {
        return (int) (Math.log10(num) / Math.log10(2));
    }
}