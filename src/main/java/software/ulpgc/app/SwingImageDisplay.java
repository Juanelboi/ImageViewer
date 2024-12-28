package software.ulpgc.app;

import software.ulpgc.model.Image;
import software.ulpgc.view.ImageDisplay;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.awt.Color.BLACK;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    public Image image;
    private BufferedImage bitmap;

    private Shift shift = Shift.Null;
    private Released released = Released.Null;
    private int initShift;
    private List<Paint> paints = new ArrayList<>();

    public SwingImageDisplay(){
        this.addMouseListener(mouseListener());
        this.addMouseMotionListener(mouseMotionListener());
        setFocusable(true);

    }



    private MouseListener mouseListener() {
        return  new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) {
                initShift = e.getX();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                released.offset(e.getX()-initShift);
            }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }
        };
    }

    private MouseMotionListener mouseMotionListener() {
        return new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                shift.offset(e.getX()-initShift);
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        };
    }

    @Override
    public void paint(String id, int offset) {
        paints.add(new Paint(id,offset));
        repaint();
    }

    @Override
    public void clear() {
        paints.clear();
    }

    @Override
    public void on(Shift shift) {
        this.shift = shift != null ? shift : Shift.Null;
    }

    @Override
    public void on(Released released) {
        this.released = released != null ? released : Released.Null;
    }

    private record Paint(String id, int offset){
    }


    @Override
    public void show(Image image) {
        this.removeAll();
        this.image = image;
        this.bitmap = load(image.id());
        repaint();
    }

    @Override
    public Image image() {
        return image;
    }

    private BufferedImage load(String name) {
        try {
            return ImageIO.read(new File(name));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(BLACK);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        drawImages(g);
    }
    HashMap<String, BufferedImage> images = new HashMap<>();
    private void drawImages(Graphics g) {
        for (Paint paint : paints) {
            checkForSaved(paint);
            Resizer resizer = new Resizer(new Dimension(this.getWidth(), this.getHeight()));
            Dimension resized = resizer.resize(new Dimension(bitmap.getWidth(),bitmap.getHeight()));
            int x = (int) ((this.getWidth()-resized.getWidth())/2);
            int y = (int) ((this.getHeight()- resized.getHeight())/2);
            g.drawImage(bitmap,x+ paint.offset,y,resized.width,resized.height,null);
        }
    }

    private void checkForSaved(Paint paint) {
        if (images.containsKey(paint.id)){
            bitmap = images.get(paint.id);
        }else {
            images.put(paint.id,load(paint.id));
            bitmap = load(paint.id);
        }
    }

    public static class Resizer {
        private final Dimension base;
        private final double aspectRatio;

        public Resizer(Dimension dimension) {
            this.base = dimension;
            aspectRatio = dimension.getWidth()/dimension.getHeight();
        }
        public Dimension resize(Dimension original) {
            double newWidth = original.width;
            double newHeight = original.height;
           if (original.width> base.width && original.getHeight()> base.getHeight() ){
               newWidth = original.width/aspectRatio;
               newHeight = original.width/aspectRatio;
           }else if (original.getWidth()> base.width){
               newWidth = original.width/aspectRatio;
           }else if (original.getHeight()> base.getHeight()) {
               newHeight = original.height/aspectRatio;
           }
           return new Dimension((int) newWidth, (int) newHeight);
        }
    }
}
