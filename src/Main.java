import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        int width = 1920;
        int height = 1080;
        int colorNumber = 1023;
        String fileName = "number.txt";


        String number = getNumberFromFile(fileName);

        Picture picture = new Picture(width, height, colorNumber, number);
        Window window = new Window(picture.getPicture());

        test(window, picture, number);
    }

    static String getNumberFromFile(String fileName) {
        StringBuilder sbNumber = new StringBuilder();
        try (FileReader fileReader = new FileReader(fileName)) {
            int ch = fileReader.read();
            while (ch != -1) {
                sbNumber.append((char) ch);
                ch = fileReader.read();
            }
        } catch(IOException e) {
            throw new RuntimeException("SWW", e);
        }
        return sbNumber.toString();
    }

    static BigInteger bigIntegerExponentiation(BigInteger number, int power) {
        for (int i = 0; i < power; i++) {
            number = number.multiply(number);
        }
        return number;
    }

    static void test(Window window, Picture picture, String number) {
        BigInteger bigNumber = new BigInteger(number);
        bigNumber = bigIntegerExponentiation(bigNumber, 18);
        for (int i = 0; i < 1000; i++) {
            picture.reDraw(bigNumber);
            window.drawPicture(picture.getPicture());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String multiplier = String.valueOf((int)Math.exp((double) i));
            bigNumber = bigNumber.multiply(new BigInteger(multiplier));
        }
    }
}
