import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Window extends JFrame { //TODO:
    BufferedImage bufferedImage;

    public Window(BufferedImage bufferedImage) {
        JFrame frame = new JFrame();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setExtendedState(MAXIMIZED_BOTH);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int xCenter = (int) (dimension.getWidth() / 2) - (WIDTH / 2);
        int yCenter = (int) (dimension.getHeight() / 2) - (HEIGHT / 2);
        setBounds(xCenter, yCenter, width, height);

        this.bufferedImage = bufferedImage;
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
}
