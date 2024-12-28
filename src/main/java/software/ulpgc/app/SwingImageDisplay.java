package software.ulpgc.app;

import software.ulpgc.model.Image;
import software.ulpgc.view.ImageDisplay;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.Color.BLACK;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private Image image;
    private BufferedImage bitmap;



    @Override
    public void show(Image image) {
        this.image = image;
        this.bitmap = load(image.name());
        repaint();
    }

    private BufferedImage load(String name) {
        try {
            return ImageIO.read(new File(name));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Image image() {
        return image;

    }

    @Override
    public void paint(Graphics g) {
        g.setColor(BLACK);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        Resizer resizer = new Resizer(new Dimension(this.getWidth(),this.getHeight()));
        Dimension resized = resizer.resize(new Dimension(bitmap.getWidth(),bitmap.getHeight()));
        int x = (int) ((this.getWidth()-resized.getWidth())/2);
        int y = (int) ((this.getHeight()- resized.getHeight())/2);
        g.drawImage(bitmap,x,y, (int) resized.getWidth(), (int) resized.getHeight(),null);
    }

    public static class Resizer {
        private final Dimension basedimension;
        private final double aspectRatio;
        public Resizer(Dimension dimension) {
            this.basedimension = dimension;
            aspectRatio = dimension.getWidth()/dimension.getHeight();
        }

        public Dimension resize(Dimension originalDimension) {

            double newWidth = originalDimension.width;
            double newHeight = originalDimension.height;

           if (originalDimension.width>basedimension.width && originalDimension.getHeight()> basedimension.getHeight() ){
               newWidth = originalDimension.width/aspectRatio;
               newHeight = originalDimension.width/aspectRatio;
          }if (originalDimension.getWidth()>basedimension.width){
               newWidth = originalDimension.width/aspectRatio;
           }if (originalDimension.getHeight()> basedimension.getHeight()) {
               newHeight = originalDimension.height/aspectRatio;
          }
           return new Dimension((int) newWidth, (int) newHeight);
        }
    }
}
