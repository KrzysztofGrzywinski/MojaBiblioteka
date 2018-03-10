package modelfx;

import database.dao.BookDao;
import database.dao.CategoryDao;
import database.dbutils.DbManager;
import database.models.Book;
import database.models.Category;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import utilities.CategoryConverter;
import utilities.exceptions.ApplicationException;

import java.sql.SQLException;
import java.util.List;

public class CategoryModel {

    private ObservableList<CategoryFx> categoryList = FXCollections.observableArrayList();
    private ObjectProperty<CategoryFx> category = new SimpleObjectProperty<>();
    private TreeItem<String> root = new TreeItem<>();

    public void init() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categories = categoryDao.queryForAll((Category.class));
        this.categoryList.clear();
        categories.forEach(c -> {
            CategoryFx categoryFx = CategoryConverter.convertToCategoryFX(c);
            this.categoryList.add(categoryFx);
        });
        initRoot(categories);
        DbManager.closeConnectionSource();
    }

    private void initRoot(List<Category> categories) {
        this.root.getChildren().clear();
        categories.forEach(c->{
            TreeItem<String> categoryItem = new TreeItem<>(c.getName());
            c.getBooks().forEach(a->{
                categoryItem.getChildren().add(new TreeItem<>(a.getTitle()));
            });
            root.getChildren().add(categoryItem);

        });
    }

    public ObservableList<CategoryFx> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ObservableList<CategoryFx> categoryList) {
        this.categoryList = categoryList;
    }

    public CategoryFx getCategory() {
        return category.get();
    }

    public void setCategory(CategoryFx category) {
        this.category.set(category);
    }

    public ObjectProperty<CategoryFx> categoryProperty() {
        return category;
    }

    public void saveCategoryInDB(String name) throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        Category category = new Category();
        category.setName(name);
        categoryDao.createOrUpdate(category);

        init();

    }

    public void deleteCategoryByID() throws ApplicationException, SQLException {
        CategoryDao categoryDao = new CategoryDao();
        categoryDao.deleteById(Category.class, category.getValue().getId());
        BookDao bookDao = new BookDao();
        bookDao.deleteByColumn(Book.CATEGORY_ID, category.getValue().getId());
        init();

    }

    public void updateCategoryInDB() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        Category tempCategory;
        tempCategory = categoryDao.findById(Category.class, getCategory().getId());
        tempCategory.setName(getCategory().getName());
        categoryDao.createOrUpdate(tempCategory);

        init();

    }

    public TreeItem<String> getRoot() {
        return root;
    }

    public void setRoot(TreeItem<String> root) {
        this.root = root;
    }
}
