package software.ulpgc.model;

public interface Image {
    String id();
    Image next();
    Image prev();
}
