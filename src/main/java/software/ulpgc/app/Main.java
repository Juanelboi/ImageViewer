package software.ulpgc.app;

import software.ulpgc.control.NextImageCommand;
import software.ulpgc.control.PreviousImageCommand;
import software.ulpgc.io.FileImageLoader;
import software.ulpgc.model.Image;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        Image image = new FileImageLoader(new File("C:/Users/juang/Pictures")).load();
        mainFrame.imageDisplay().show(image);
        mainFrame.add("<", new PreviousImageCommand(mainFrame.imageDisplay()));
        mainFrame.add(">",new NextImageCommand(mainFrame.imageDisplay()));
        mainFrame.setVisible(true);

    }
}