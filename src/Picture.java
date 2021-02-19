import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigInteger;

public class Picture {
    private int width;
    private int height;
    private BigInteger number;
    private BufferedImage bufferedImage;
    private colors.Color color;
    private int colorBitLength;
    private int numberBitIndex;

    public Picture(int width, int height, int colorNumber, String number) {
        this.width = width;
        this.height = height;
        this.number = new BigInteger(number);
        colorBitLength = getBitLength(colorNumber);
        color = new colors.Color(colorNumber);
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        definePicture();
    }

    public BufferedImage getPicture () {
        return bufferedImage;
    }

    public void reDraw(BigInteger number) {
        this.number = number;
        definePicture();
    }

    public void reDraw(String number) {
        this.number = new BigInteger(number);
        definePicture();
    }

    private void definePicture() {
        numberBitIndex = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                bufferedImage.setRGB(i, j, color.getColor(getColorIndexOfNextPixel()));
            }
        }
    }

    private int getColorIndexOfNextPixel() {
        String colorIndex = "";
        for (int i = numberBitIndex; i < (numberBitIndex + colorBitLength); i++) {
            colorIndex += getBit(number, i);
        }
        numberBitIndex += colorBitLength;
        return Integer.valueOf(colorIndex, 2);
    }

    private int getBit(BigInteger num, int index) {
        return num.testBit(index) ? 1 : 0;
    }

    private int getBitLength(int num) {
        return (int) (Math.log10(num) / Math.log10(2));
    }
}
