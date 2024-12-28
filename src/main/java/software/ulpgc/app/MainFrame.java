package software.ulpgc.app;

import software.ulpgc.control.Command;
import software.ulpgc.view.ImageDisplay;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private final Map<String, Command> commands;
    private ImageDisplay imageDisplay;

    public MainFrame() throws HeadlessException {
        commands = new HashMap<>();
        setTitle("Image Viewer");
        setSize(1200,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(createImageDispay());
        add(createToolbar(),BorderLayout.SOUTH);
    }

    private Component createToolbar() {
        JPanel panel = new JPanel();
        panel.add(createButton("<"));
        panel.add(createButton(">"));
        return panel;
    }

    private Component createButton(String label) {
        JButton button = new JButton(label);
        button.addActionListener(e -> commands.get(label).execute());
        return button;
    }

    public void add(String name, Command command){ commands.put(name,command);}

    private Component createImageDispay() {
        SwingImageDisplay display = new SwingImageDisplay();
        this.imageDisplay=display;
        return display;
    }

    public ImageDisplay imageDisplay(){
        return imageDisplay;
    }
}
