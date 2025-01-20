package software.ulpgc.app;

import software.ulpgc.control.NextImageCommand;
import software.ulpgc.control.PreviousImageCommand;
import software.ulpgc.io.FileImageLoader;
import software.ulpgc.model.Image;

import java.io.File;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.show(image());
        mainFrame.add("Previous", new PreviousImageCommand(mainFrame.presenter()));
        mainFrame.add("Next",new NextImageCommand(mainFrame.presenter()));
        mainFrame.setVisible(true);
    }
    private static Image image() {
        String username = System.getProperty("user.home");
        String picturesPath = Paths.get(username, "Pictures").toString();
        return new FileImageLoader(new File(picturesPath)).load();
    }
}