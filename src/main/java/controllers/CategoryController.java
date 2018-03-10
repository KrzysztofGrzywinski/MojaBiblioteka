package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelfx.CategoryFx;
import modelfx.CategoryModel;
import utilities.Dialogs;
import utilities.exceptions.ApplicationException;

import java.sql.SQLException;

public class CategoryController {

    @FXML
    private TreeView<String> categoryTreeView;
    @FXML
    private ComboBox categoryComboBox;
    @FXML
    private Button addCategory;
    @FXML
    private TextField categoryTextField;
    @FXML
    private ComboBox<CategoryFx> CategoryComboBox;
    @FXML
    private Button deleteCategoryButton;
    @FXML
    private Button editCategoryButton;

    private CategoryModel categoryModel;

    @FXML
    public void initialize() throws ApplicationException {
        this.categoryModel = new CategoryModel();
        this.categoryModel.init();
        this.categoryComboBox.setItems(this.categoryModel.getCategoryList());
        this.categoryTreeView.setRoot(this.categoryModel.getRoot());
        initBindings();
    }

    private void initBindings() {
        addCategory.disableProperty().bind(categoryTextField.textProperty().isEmpty());
        deleteCategoryButton.disableProperty().bind(this.categoryModel.categoryProperty().isNull());
        editCategoryButton.disableProperty().bind(this.categoryModel.categoryProperty().isNull());
    }

    @FXML
    public void addCategoryOnAction() {
        try {
            categoryModel.saveCategoryInDB(categoryTextField.getText());
        } catch (ApplicationException e) {
            Dialogs.errorDialog(e.getMessage());
        }
        categoryTextField.clear();
    }


    public void deleteCategoryOnAction() {
        try {
            this.categoryModel.deleteCategoryByID();
        } catch (ApplicationException e) {
            Dialogs.errorDialog(e.getMessage());
        } catch (SQLException e) {
            Dialogs.errorDialog(e.getMessage());
        }
    }

    public void onActionCB() {
        this.categoryModel.setCategory((CategoryFx) this.categoryComboBox.getSelectionModel().getSelectedItem());

    }

    public void onActionEditCategory() {
        String categoryName = Dialogs.editDialog(this.categoryModel.getCategory().getName());
        if(categoryName!=null){
            this.categoryModel.getCategory().setName(categoryName);
            try {
                this.categoryModel.updateCategoryInDB();
            } catch (ApplicationException e) {
                Dialogs.errorDialog(e.getMessage());
            }
        }
    }
}
