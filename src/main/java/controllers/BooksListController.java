package controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelfx.AuthorFx;
import modelfx.BookFx;
import modelfx.BooksListModel;
import modelfx.CategoryFx;
import utilities.Dialogs;
import utilities.FxmlUtils;
import utilities.exceptions.ApplicationException;

import java.io.IOException;
import java.time.LocalDate;

public class BooksListController {

    @FXML
    private ComboBox categoryComboBox;
    @FXML
    private ComboBox authorComboBox;
    @FXML
    private TableView<BookFx> booksTableView;
    @FXML
    private TableColumn<BookFx, String> columnTitle;
    @FXML
    private TableColumn<BookFx, String> columnDescription;
    @FXML
    private TableColumn<BookFx, AuthorFx> columnAuthor;
    @FXML
    private TableColumn<BookFx, CategoryFx> columnCategory;
    @FXML
    private TableColumn<BookFx, Number> columnRating;
    @FXML
    private TableColumn<BookFx, String> columnISBN;
    @FXML
    private TableColumn<BookFx, LocalDate> columnReleaseDate;
    @FXML
    private TableColumn<BookFx, BookFx> columnDelete;
    @FXML
    private TableColumn<BookFx, BookFx> columnEdit;

    private BooksListModel booksListModel;

    @FXML
    void initialize() {
        booksListModel = new BooksListModel();
        try {
            booksListModel.init();
        } catch (ApplicationException e) {
            Dialogs.errorDialog(e.getMessage());
        }

        authorComboBox.setItems(booksListModel.getAuthorFxObservableList());
        categoryComboBox.setItems(booksListModel.getCategoryFxObservableList());
        booksListModel.categoryFxObjectPropertyProperty().bind(categoryComboBox.valueProperty());
        booksListModel.authorFxObjectPropertyProperty().bind(authorComboBox.valueProperty());
        booksTableView.setItems(booksListModel.getBookFxObservableList());

        booksTableView.setItems(booksListModel.getBookFxObservableList());
        columnTitle.setCellValueFactory(cell -> cell.getValue().titleProperty());
        columnAuthor.setCellValueFactory(cell -> cell.getValue().authorFxProperty());
        columnCategory.setCellValueFactory(cell -> cell.getValue().categoryFxProperty());
        columnDescription.setCellValueFactory(cell -> cell.getValue().descriptionProperty());
        columnISBN.setCellValueFactory(cell -> cell.getValue().isbnProperty());
        columnRating.setCellValueFactory(cell -> cell.getValue().ratingProperty());
        columnReleaseDate.setCellValueFactory(cell -> cell.getValue().releaseDateProperty());
        columnDelete.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue()));
        columnEdit.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue()));

        columnDelete.setCellFactory(param -> new TableCell<BookFx, BookFx>() {
            Button button = makeButton("/icons/delete.png");

            @Override
            protected void updateItem(BookFx item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                if (!empty) {
                    setGraphic(button);
                    button.setOnAction(event -> {
                        try {
                            booksListModel.deleteBook(item);
                        } catch (ApplicationException e) {
                            Dialogs.errorDialog(e.getMessage());
                        }
                    });

                }
            }
        });

        columnEdit.setCellFactory(param -> new TableCell<BookFx, BookFx>() {
            Button button = makeButton("/icons/edit.png");

            @Override
            protected void updateItem(BookFx item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                if (!empty) {
                    setGraphic(button);
                    button.setOnAction(event -> {
                        FXMLLoader loader = FxmlUtils.load("/fxml/AddBooks.fxml");
                        Scene scene = null;
                        try {
                            scene = new Scene(loader.load());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        BookController bookController = loader.getController();
                        bookController.getBookModel().setBookFxObjectProperty(item);
                        bookController.initBindings();
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();

                    });

                }
            }
        });
    }

    public void filterComboBox() {
        booksListModel.filterBooks();
    }

    public void clearCategoryCB() {
        categoryComboBox.getSelectionModel().clearSelection();
    }

    public void clearAuthorCB() {
        authorComboBox.getSelectionModel().clearSelection();
    }

    private Button makeButton(String s) {
        Button button = new Button();
        Image image = new Image(getClass().getResource(s).toString());
        ImageView imageView = new ImageView(image);
        button.setGraphic(imageView);
        button.setAlignment(Pos.CENTER);
        return button;
    }


}
