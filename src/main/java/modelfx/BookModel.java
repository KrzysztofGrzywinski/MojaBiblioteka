package modelfx;

import database.dao.AuthorDao;
import database.dao.BookDao;
import database.dao.CategoryDao;
import database.models.Author;
import database.models.Book;
import database.models.Category;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.BookConverter;
import utilities.CategoryConverter;
import utilities.exceptions.ApplicationException;
import utilities.exceptions.AuthorConverter;

import java.util.List;

public class BookModel {

    private ObjectProperty<BookFx> bookFxObjectProperty = new SimpleObjectProperty<>(new BookFx());
    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();
    private ObservableList<AuthorFx> authorFxObservableList = FXCollections.observableArrayList();


    public void init() throws ApplicationException {
        initAuthorList();
        initCategoryList();
    }

    private void initAuthorList() throws ApplicationException {
        AuthorDao authorDao = new AuthorDao();
        List<Author> authorList = authorDao.queryForAll(Author.class);
        authorList.forEach(c -> {
            AuthorFx authorFx = AuthorConverter.convertToAuthorFx(c);
            authorFxObservableList.add(authorFx);
        });

    }

    private void initCategoryList() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.queryForAll(Category.class);
        categoryFxObservableList.clear();
        categoryList.forEach(c -> {
            CategoryFx categoryFx = CategoryConverter.convertToCategoryFX(c);
            categoryFxObservableList.add(categoryFx);
        });
    }

    public void saveBookInDB() throws ApplicationException {
        Book book = BookConverter.convertToBook(this.getBookFxObjectProperty());

        CategoryDao categoryDao = new CategoryDao();
        Category category = categoryDao.findById(Category.class, this.getBookFxObjectProperty().getCategoryFx().getId());

        AuthorDao authorDao = new AuthorDao();
        Author author = authorDao.findById(Author.class, this.getBookFxObjectProperty().getAuthorFx().getId());

        book.setCategory(category);
        book.setAuthor(author);

        BookDao bookDao = new BookDao();
        bookDao.createOrUpdate(book);

    }



    public BookFx getBookFxObjectProperty() {
        return bookFxObjectProperty.get();
    }


    public void setBookFxObjectProperty(BookFx bookFxObjectProperty) {
        this.bookFxObjectProperty.set(bookFxObjectProperty);
    }

    public ObjectProperty<BookFx> bookFxObjectPropertyProperty() {
        return bookFxObjectProperty;
    }

    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }

    public void setCategoryFxObservableList(ObservableList<CategoryFx> categoryFxObservableList) {
        this.categoryFxObservableList = categoryFxObservableList;
    }

    public ObservableList<AuthorFx> getAuthorFxObservableList() {
        return authorFxObservableList;
    }

    public void setAuthorFxObservableList(ObservableList<AuthorFx> authorFxObservableList) {
        this.authorFxObservableList = authorFxObservableList;
    }


}
