package utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.util.ResourceBundle;

public class FxmlUtils {

    public static Pane fxmlLoader(String string) {
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(string));
        loader.setResources(getResourceBundle());
        try {
            return loader.load();
        } catch (Exception e) {
            Dialogs.errorDialog(e.getMessage());
        }
        return null;
    }

    public static FXMLLoader load(String string) {
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(string));
        loader.setResources(getResourceBundle());
        return loader;
    }

    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("bundles.messeges");
    }
}
