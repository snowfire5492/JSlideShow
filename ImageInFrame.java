import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

 class ImageInFrame {
    public static void main(String[] args) throws IOException {
    String path = "";
    URL url = new URL(path);
    BufferedImage image = ImageIO.read(url);
    JLabel label = new JLabel(new ImageIcon(image));
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(label);
    f.pack();
    f.setLocation(200,200);
    f.setVisible(true);
  }
  }