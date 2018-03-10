package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

public class TopMenuButtonsController {

    public static final String LIBRARY_FXML = "/fxml/Library.fxml";
    public static final String BOOKS_LIST_FXML = "/fxml/BooksList.fxml";
    public static final String STATISTICS_FXML = "/fxml/Statistics.fxml";
    public static final String ADD_BOOKS_FXML = "/fxml/AddBooks.fxml";
    public static final String ADD_CATEGORY_FXML = "/fxml/AddCategory.fxml";
    public static final String ADD_AUTHOR_FXML = "/fxml/AddAuthor.fxml";
    private MainController mainController;

    @FXML
    private ToggleGroup topButtons;
    @FXML
    public void openLibrary() {
        mainController.setCenter(LIBRARY_FXML);
    }

    @FXML
    public void openBookList() {
        mainController.setCenter(BOOKS_LIST_FXML);
    }

    @FXML
    public void openStatistics() {
        mainController.setCenter(STATISTICS_FXML);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void addBook() {
        resetTButton();
        mainController.setCenter(ADD_BOOKS_FXML);
    }

    public void addCategory() {
        resetTButton();
        mainController.setCenter(ADD_CATEGORY_FXML);
    }
    private void resetTButton() {
        if(topButtons.getSelectedToggle()!=null){
            topButtons.getSelectedToggle().setSelected(false);
        }
    }

    public void addAuthor() {
        resetTButton();
        mainController.setCenter(ADD_AUTHOR_FXML);
    }
}