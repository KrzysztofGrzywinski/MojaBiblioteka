package biblioteka.main;


import database.dbutils.DbManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utilities.FxmlUtils;


import java.util.Locale;

public class Main extends Application {

    public static final String BORDER_PANE_MAIN_FXML_PATH = "/fxml/BorderPaneMain.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        Locale.setDefault(new Locale("pl"));
        Pane pane = FxmlUtils.fxmlLoader(BORDER_PANE_MAIN_FXML_PATH);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle(FxmlUtils.getResourceBundle().getString("title.app"));
        primaryStage.show();

        DbManager.initDatabase();
    }
}
