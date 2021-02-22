import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;

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

    public void readPictureFromImage(File image) {
        loadImage(image);
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                sb.append(decToBool(getColorFromImage(x, y)));
            }
        }
        rgbNumber = sb.toString();
        pictureWidth = imageWidth;
        pictureHeight = imageHeight;
    }

    public void readPictureFromImage(String path) {
        loadImage(path);
        StringBuilder sb = new StringBuilder();
        for (int x = imageWidth - 1; x >= 0; x--) {
            for (int y = imageHeight - 1; y >= 0; y--) {
                sb.append(decToBool(getColorFromImage(x, y)));
            }
        }
        rgbNumber = sb.toString();
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
        picture = new BufferedImage(pictureWidth, pictureHeight, BufferedImage.TYPE_INT_RGB);
        int pixelNum = 1;
        for (int x = 0; x < pictureWidth; x++) {
            for (int y = 0; y < pictureHeight; y++) {
                try {
                    picture.setRGB(x, y, getColorFromRGBNumber(pixelNum));
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Файл короче изображения");
                    picture.setRGB(x, y, 0);
                }
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
            rgbNumber = sb.toString();
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
        //StringBuilder hexNum = new StringBuilder();
        String boolNum;
        int bitNum = 31;
        try {
            for (int i = 0; i < 8; i++) {
                //hexNum.append(hexToBool(rgbNumber.charAt((pixelNum - 1) * 8 + i)));
                boolNum = hexToBool(rgbNumber.charAt((pixelNum - 1) * 8 + i));
                for (int j = 0; j < 4; j++) {
                    int bit = boolNum.charAt(j) == '1' ? 1 : 0;
                    argb |= bit << bitNum;
                    bitNum--;
                }
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Файл короче изображения");
            return 0;
        }
        return argb;
    }

    private String decToBool(int num) {
        return Integer.toBinaryString(num);
    }

    private String boolToHex(String num) { //TODO: Сделать без использования BigInteger
        StringBuilder boolNum = new StringBuilder();
        StringBuilder hexNum = new StringBuilder();
        int placeNum = 0;
        for (int i = 0; i < num.length() / 4; i++) {
            boolNum = new StringBuilder();
            for (int j = 0; j < 4; j++) {
                boolNum.append(num.charAt(placeNum));
                placeNum++;
            }
            switch (boolNum.toString()) {
                case "0000":
                    hexNum.append("0");
                    break;
                case "0001":
                    hexNum.append("1");
                    break;
                case "0010":
                    hexNum.append("2");
                    break;
                case "0011":
                    hexNum.append("3");
                    break;
                case "0100":
                    hexNum.append("4");
                    break;
                case "0101":
                    hexNum.append("5");
                    break;
                case "0110":
                    hexNum.append("6");
                    break;
                case "0111":
                    hexNum.append("7");
                    break;
                case "1000":
                    hexNum.append("8");
                    break;
                case "1001":
                    hexNum.append("9");
                    break;
                case "1010":
                    hexNum.append("A");
                    break;
                case "1011":
                    hexNum.append("B");
                    break;
                case "1100":
                    hexNum.append("C");
                    break;
                case "1101":
                    hexNum.append("D");
                    break;
                case "1110":
                    hexNum.append("E");
                    break;
                case "1111":
                    hexNum.append("F");
                    break;
            }
        }
        return hexNum.toString();
    }

    private String hexToBool(String num) {
        StringBuilder bool = new StringBuilder();
        for (int i = 0; i < num.length(); i++) {
            switch(num.charAt(i)) {
                case '0':
                    bool.append("0000");
                    break;
                case '1':
                    bool.append("0001");
                    break;
                case '2':
                    bool.append("0010");
                    break;
                case '3':
                    bool.append("0011");
                    break;
                case '4':
                    bool.append("0100");
                    break;
                case '5':
                    bool.append("0101");
                    break;
                case '6':
                    bool.append("0110");
                    break;
                case '7':
                    bool.append("0111");
                    break;
                case '8':
                    bool.append("1000");
                    break;
                case '9':
                    bool.append("1001");
                    break;
                case 'A':
                    bool.append("1010");
                    break;
                case 'B':
                    bool.append("1011");
                    break;
                case 'C':
                    bool.append("1100");
                    break;
                case 'D':
                    bool.append("1101");
                    break;
                case 'E':
                    bool.append("1110");
                    break;
                case 'F':
                    bool.append("1111");
                    break;
            }
        }
        return bool.toString();
    }

    private String hexToBool(char num) {
        switch(num) {
                case '0': return "0000";
                case '1': return "0001";
                case '2': return "0010";
                case '3': return "0011";
                case '4': return "0100";
                case '5': return "0101";
                case '6': return "0110";
                case '7': return "0111";
                case '8': return "1000";
                case '9': return "1001";
                case 'A': return "1010";
                case 'B': return "1011";
                case 'C': return "1100";
                case 'D': return "1101";
                case 'E': return "1110";
                case 'F': return "1111";
                default: throw new RuntimeException("Failed convert hex to bool");
            }
    }

    private int getBit(BigInteger num, int index) {
        return num.testBit(index) ? 1 : 0;
    }

    private int getBitLength(int num) {
        return (int) (Math.log10(num) / Math.log10(2));
    }
}
