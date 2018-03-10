package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelfx.AuthorFx;
import modelfx.BookModel;
import modelfx.CategoryFx;
import utilities.Dialogs;
import utilities.exceptions.ApplicationException;

public class BookController {

    @FXML
    private Button addBook;
    @FXML
    private ComboBox<CategoryFx> categoryComboBox;
    @FXML
    private ComboBox<AuthorFx> authorComboBox;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Slider ratingSlider;
    @FXML
    private TextField isbnTextField;
    @FXML
    private DatePicker releaseDatePicker;
    @FXML
    private TextField titleTextField;

    private BookModel bookModel;

    @FXML
    public void initialize() {
        bookModel = new BookModel();
        try {
            bookModel.init();
        } catch (ApplicationException e) {
            Dialogs.errorDialog(e.getMessage());
        }
        initBindings();
        booksValidation();
    }

    private void booksValidation() {
        addBook.disableProperty().bind(categoryComboBox.valueProperty().isNull()
        .or(authorComboBox.valueProperty().isNull()
        .or(descriptionTextArea.textProperty().isEmpty()
        .or(isbnTextField.textProperty().isEmpty()
        .or(titleTextField.textProperty().isEmpty()
        .or(releaseDatePicker.valueProperty().isNull()))))));
    }

    public void initBindings() {
        categoryComboBox.setItems(bookModel.getCategoryFxObservableList());
        authorComboBox.setItems(bookModel.getAuthorFxObservableList());

        categoryComboBox.valueProperty().bindBidirectional(bookModel.getBookFxObjectProperty().categoryFxProperty());
        authorComboBox.valueProperty().bindBidirectional(bookModel.getBookFxObjectProperty().authorFxProperty());
        descriptionTextArea.textProperty().bindBidirectional(bookModel.getBookFxObjectProperty().descriptionProperty());
        ratingSlider.valueProperty().bindBidirectional(bookModel.getBookFxObjectProperty().ratingProperty());
        isbnTextField.textProperty().bindBidirectional(bookModel.getBookFxObjectProperty().isbnProperty());
        titleTextField.textProperty().bindBidirectional(bookModel.getBookFxObjectProperty().titleProperty());
        releaseDatePicker.valueProperty().bindBidirectional(bookModel.getBookFxObjectProperty().releaseDateProperty());
    }

    public void addBookButton() {
        try {
            this.bookModel.saveBookInDB();
            //clear();
        } catch (ApplicationException e) {
            Dialogs.errorDialog(e.getMessage());
        }

    }

    private void clear() {
        categoryComboBox.getSelectionModel().clearSelection();
        authorComboBox.getSelectionModel().clearSelection();
        descriptionTextArea.clear();
        titleTextField.clear();
        isbnTextField.clear();
        releaseDatePicker.getEditor().clear();
        ratingSlider.setValue(1);
    }

    public BookModel getBookModel() {
        return bookModel;
    }

    public void setBookModel(BookModel bookModel) {
        this.bookModel = bookModel;
    }
}
