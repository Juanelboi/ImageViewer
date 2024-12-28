package software.ulpgc.model;

public interface Image {
    String name();
    Image next();
    Image prev();
}
