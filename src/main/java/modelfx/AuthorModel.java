package modelfx;

import database.dao.AuthorDao;
import database.dao.BookDao;
import database.dbutils.DbManager;
import database.models.Author;
import database.models.Book;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.exceptions.ApplicationException;
import utilities.exceptions.AuthorConverter;

import java.sql.SQLException;
import java.util.List;

public class AuthorModel {
    private ObjectProperty<AuthorFx> authorFxObjectProperty = new SimpleObjectProperty<>(new AuthorFx());
    private ObjectProperty<AuthorFx> authorFxObjectPropertyEditTable = new SimpleObjectProperty<>(new AuthorFx());

    private ObservableList<AuthorFx> authorFxObservableList = FXCollections.observableArrayList();

    public void init() throws ApplicationException {
        AuthorDao authorDao = new AuthorDao();
        List<Author> authorList = authorDao.queryForAll(Author.class);
        this.authorFxObservableList.clear();
        authorList.forEach(author->{
            AuthorFx authorFx=AuthorConverter.convertToAuthorFx(author);
            this.authorFxObservableList.add(authorFx);
        });

    }

    public void saveAuthorEditInDB() throws ApplicationException {
        saveOrUpdate(this.getAuthorFxObjectPropertyEditTable());
    }

    private void saveOrUpdate(AuthorFx authorFxObjectPropertyEditTable) throws ApplicationException {
        AuthorDao authorDao = new AuthorDao();
        Author author = AuthorConverter.converttoAuthor(authorFxObjectPropertyEditTable);
        authorDao.createOrUpdate(author);

        this.init();
    }

    public void saveAuthorInDB() throws ApplicationException {
        saveOrUpdate(this.getAuthorFxObjectProperty());
    }

    public void deleteAuthorFromDB() throws ApplicationException, SQLException {
        AuthorDao authorDao = new AuthorDao();
        authorDao.deleteById(Author.class, this.getAuthorFxObjectPropertyEditTable().getId());
        BookDao bookDao= new BookDao();
        bookDao.deleteByColumn(Book.AUTHOR_ID, getAuthorFxObjectPropertyEditTable().getId());
        this.init();
    }

    public AuthorFx getAuthorFxObjectProperty() {
        return authorFxObjectProperty.get();
    }

    public ObjectProperty<AuthorFx> authorFxObjectPropertyProperty() {
        return authorFxObjectProperty;
    }

    public void setAuthorFxObjectProperty(AuthorFx authorFxObjectProperty) {
        this.authorFxObjectProperty.set(authorFxObjectProperty);
    }

    public ObservableList<AuthorFx> getAuthorFxObservableList() {
        return authorFxObservableList;
    }

    public void setAuthorFxObservableList(ObservableList<AuthorFx> authorFxObservableList) {
        this.authorFxObservableList = authorFxObservableList;
    }

    public AuthorFx getAuthorFxObjectPropertyEditTable() {
        return authorFxObjectPropertyEditTable.get();
    }

    public ObjectProperty<AuthorFx> authorFxObjectPropertyEditTableProperty() {
        return authorFxObjectPropertyEditTable;
    }

    public void setAuthorFxObjectPropertyEditTable(AuthorFx authorFxObjectPropertyEditTable) {
        this.authorFxObjectPropertyEditTable.set(authorFxObjectPropertyEditTable);
    }
}
