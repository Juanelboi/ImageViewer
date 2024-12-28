package software.ulpgc.control;

import software.ulpgc.app.ImagePresenter;
import software.ulpgc.view.ImageDisplay;

public class NextImageCommand implements Command{

    private final ImagePresenter presenter;

    public NextImageCommand(ImagePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        presenter.show(presenter.image().next());
    }
}
