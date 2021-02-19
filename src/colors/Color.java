package colors;

public class Color implements ColorList {
    private final int RGB_SPACE = 16777216;
    private int numberOfColors;
    private int[] colors;

    public Color(int numberOfColors) {
        this.numberOfColors = numberOfColors;
        colors = new int[numberOfColors];
        generateEquidistantColors(colors);
    }

    private void generateEquidistantColors(int[] colors) {
        for (int i = 0; i < numberOfColors; i++) {
            colors[i] = RGB_SPACE / numberOfColors * (i + 1);
        }
    }

    @Override
    public int getColor(int colorNumber) {
        return colors[colorNumber];
    }
}
