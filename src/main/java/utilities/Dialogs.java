package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;
import java.util.ResourceBundle;

public class Dialogs {

    static ResourceBundle bundle = FxmlUtils.getResourceBundle();

    public static void dialogAboutApp() {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle(bundle.getString("about.title"));
        infoAlert.setHeaderText(bundle.getString("about.header"));
        infoAlert.setContentText(bundle.getString("about.content"));
        infoAlert.showAndWait();
    }

    public static Optional<ButtonType> dialogExitApp() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle(bundle.getString("exit.title"));
        confirmAlert.setHeaderText((bundle.getString("exit.header")));
        Optional<ButtonType> result = confirmAlert.showAndWait();
        return result;
    }

    public static void errorDialog(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(bundle.getString("error.title"));
        errorAlert.setHeaderText(bundle.getString("error.header"));

        TextArea textArea = new TextArea(message);
        errorAlert.getDialogPane().setContent(textArea);
        errorAlert.showAndWait();
    }

    public static String editDialog(String value) {
        TextInputDialog dialog = new TextInputDialog(value);
        dialog.setTitle(bundle.getString("edit.title"));
        dialog.setHeaderText(bundle.getString("edit.header"));
        dialog.setContentText(bundle.getString("edit.content"));
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

}
