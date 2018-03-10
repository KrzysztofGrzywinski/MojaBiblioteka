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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BooksListModel {

    private ObservableList<BookFx> bookFxObservableList = FXCollections.observableArrayList();
    private ObservableList<AuthorFx> authorFxObservableList = FXCollections.observableArrayList();
    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();

    private ObjectProperty<AuthorFx> authorFxObjectProperty = new SimpleObjectProperty<>();
    private ObjectProperty<CategoryFx> categoryFxObjectProperty = new SimpleObjectProperty<>();
    private List<BookFx> bookFxList = new ArrayList<>();


    public void init() throws ApplicationException {
        BookDao bookDao = new BookDao();
        List<Book> bookList = bookDao.queryForAll(Book.class);
        bookFxList.clear();
        bookList.forEach(c -> bookFxList.add(BookConverter.convertToBookFx(c)));
        bookFxObservableList.setAll(bookFxList);

        initAuthor();
        initCategory();
    }

    public void filterBooks() {
        if (getAuthorFxObjectProperty() != null && getCategoryFxObjectProperty() != null) {
            predicateFilter(authorPredicate().and(categoryPredicate()));
        } else if ((getCategoryFxObjectProperty() != null)) {
            predicateFilter(categoryPredicate());
        } else if (getAuthorFxObjectProperty() != null) {
            predicateFilter(authorPredicate());
        } else {
            bookFxObservableList.setAll(bookFxList);
        }
    }


    public void initAuthor() throws ApplicationException {
        AuthorDao authorDao = new AuthorDao();
        List<Author> authorList = authorDao.queryForAll(Author.class);
        authorFxObservableList.clear();
        authorList.forEach(c -> {
            AuthorFx authorFx = AuthorConverter.convertToAuthorFx(c);
            authorFxObservableList.add(authorFx);
        });
    }

    public void initCategory() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.queryForAll(Category.class);
        categoryFxObservableList.clear();
        categoryList.forEach(c -> {
            CategoryFx categoryFx = CategoryConverter.convertToCategoryFX(c);
            categoryFxObservableList.add(categoryFx);
        });
    }

    private Predicate<BookFx> categoryPredicate() {
        return bookFx -> bookFx.getCategoryFx().getId() == getCategoryFxObjectProperty().getId();
    }

    private Predicate<BookFx> authorPredicate() {
        return bookFx -> bookFx.getAuthorFx().getId() == getAuthorFxObjectProperty().getId();
    }

    private void predicateFilter(Predicate<BookFx> predicate) {
        List<BookFx> list = bookFxList.stream().filter(predicate).collect(Collectors.toList());
        bookFxObservableList.setAll(list);
    }

    public ObservableList<BookFx> getBookFxObservableList() {
        return bookFxObservableList;
    }

    public ObservableList<AuthorFx> getAuthorFxObservableList() {
        return authorFxObservableList;
    }

    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }

    public AuthorFx getAuthorFxObjectProperty() {
        return authorFxObjectProperty.get();
    }

    public ObjectProperty<AuthorFx> authorFxObjectPropertyProperty() {
        return authorFxObjectProperty;
    }

    public CategoryFx getCategoryFxObjectProperty() {
        return categoryFxObjectProperty.get();
    }

    public ObjectProperty<CategoryFx> categoryFxObjectPropertyProperty() {
        return categoryFxObjectProperty;
    }

    public void deleteBook(BookFx item) throws ApplicationException {
        BookDao bookDao = new BookDao();
        bookDao.deleteById(Book.class, item.getId());
        init();
    }


}
