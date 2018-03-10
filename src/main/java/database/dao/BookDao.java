package database.dao;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import database.models.Book;
import utilities.exceptions.ApplicationException;

import java.sql.SQLException;

public class BookDao extends CommonDao {

        public BookDao() {
            super();
        }

        public void deleteByColumn(String name, int id) throws ApplicationException, SQLException {
        Dao<Book, Object> dao = getDao(Book.class);
            DeleteBuilder<Book, Object> deleteBuilder = dao.deleteBuilder();
            deleteBuilder.where().eq(name, id);
            dao.delete(deleteBuilder.prepare());
        }
    }

