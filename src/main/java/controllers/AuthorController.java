package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import modelfx.AuthorFx;
import modelfx.AuthorModel;
import utilities.Dialogs;
import utilities.exceptions.ApplicationException;

import java.sql.SQLException;

public class AuthorController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private Button addButton;
    @FXML
    private TableView<AuthorFx> authorTableView;
    @FXML
    private TableColumn<AuthorFx, String> nameColumn;
    @FXML
    private TableColumn<AuthorFx, String> surnameColumn;

    @FXML
    private MenuItem deleteMenuItem;

    private AuthorModel authorModel;

    public void initialize() {
        this.authorModel = new AuthorModel();
        try {
            this.authorModel.init();
        } catch (ApplicationException e) {
            Dialogs.errorDialog(e.getMessage());
        }
        this.authorModel.authorFxObjectPropertyProperty().get().nameProperty().bind(this.nameTextField.textProperty());
        this.authorModel.authorFxObjectPropertyProperty().get().surnameProperty().bind(this.surnameTextField.textProperty());
        this.addButton.disableProperty().bind(this.nameTextField.textProperty().isEmpty().or(this.surnameTextField.textProperty().isEmpty()));
        this.deleteMenuItem.disableProperty().bind(this.authorTableView.getSelectionModel().selectedItemProperty().isNull());

        this.authorTableView.setItems(this.authorModel.getAuthorFxObservableList());
        this.nameColumn.setCellValueFactory(cell -> cell.getValue().nameProperty());
        this.surnameColumn.setCellValueFactory(cell -> cell.getValue().surnameProperty());

        this.nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        this.authorTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.authorModel.setAuthorFxObjectPropertyEditTable(newValue);
        });
    }

    public void addAuthorOnAction() {
        try {
            authorModel.saveAuthorInDB();
        } catch (ApplicationException e) {
            Dialogs.errorDialog(e.getMessage());
        }
        this.nameTextField.clear();
        this.surnameTextField.clear();
    }

    public void editCommitName(TableColumn.CellEditEvent<AuthorFx, String> authorFxStringCellEditEvent) {
        this.authorModel.getAuthorFxObjectPropertyEditTable().setName(authorFxStringCellEditEvent.getNewValue());
        updateInDB();

    }


    public void editCommitSurname(TableColumn.CellEditEvent<AuthorFx, String> authorFxStringCellEditEvent) {
        this.authorModel.getAuthorFxObjectPropertyEditTable().setSurname(authorFxStringCellEditEvent.getNewValue());
        updateInDB();
    }

    private void updateInDB() {
        try {
            this.authorModel.saveAuthorEditInDB();
        } catch (ApplicationException e) {
            Dialogs.errorDialog(e.getMessage());
        }
    }

    public void deleteAuthor() {
        try {
            try {
                authorModel.deleteAuthorFromDB();
            } catch (SQLException e) {
                Dialogs.errorDialog(e.getMessage());
            }
        } catch (ApplicationException e) {
            Dialogs.errorDialog(e.getMessage());
        }
    }
}
