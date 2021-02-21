import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Window extends JFrame { //TODO:
    BufferedImage bufferedImage;
    private int width;
    private int height;
    private int xCenter;
    private int yCenter;


    public Window() {
        JFrame frame = new JFrame();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setExtendedState(MAXIMIZED_BOTH);


        setBounds(xCenter, yCenter, width, height);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bufferedImage, 0, 0, null);
            }
        };

        add(panel);
        setVisible(true);
    }

    public void drawPicture(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        repaint();
    }

    public void drawPicture(Picture picture) {
        loadPicture(picture);
        repaint();
    }

    public void drawImage(Picture picture) {
        loadImage(picture);
        repaint();
    }

    private void loadPicture(Picture picture) {
        bufferedImage = picture.getPicture();
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
        setWindowBounds();
    }

    private void loadImage(Picture picture) {
        bufferedImage = picture.getImage();
        setWindowBounds();
    }

    private void setWindowBounds() {
        setWindowSize();
        setWindowPos();
        setBounds(xCenter, yCenter, width, height);
    }

    private void setWindowPos() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        xCenter = (int) (dimension.getWidth() / 2) - (width / 2);
        yCenter = (int) (dimension.getHeight() / 2) - (height / 2);
    }

    private void setWindowSize() {
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
    }
}
