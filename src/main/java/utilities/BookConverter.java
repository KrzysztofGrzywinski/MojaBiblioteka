package utilities;

import database.models.Book;
import modelfx.BookFx;
import utilities.exceptions.AuthorConverter;

public class BookConverter {

    public static Book convertToBook(BookFx bookFx){
        Book book = new Book();
        book.setId(bookFx.getId());
        book.setTitle(bookFx.getTitle());
        book.setDescription(bookFx.getDescription());
        book.setRating(bookFx.getRating());
        book.setIsbn(bookFx.getIsbn());
        book.setReleaseDate(Utils.convertDate(bookFx.getReleaseDate()));
        book.setAddedDate(Utils.convertDate(bookFx.getAddedDate()));
        return book;
    }

    public static BookFx convertToBookFx(Book book) {
        BookFx bookFx = new BookFx();
        bookFx.setId(book.getId());
        bookFx.setTitle(book.getTitle());
        bookFx.setDescription(book.getDescription());
        bookFx.setIsbn(book.getIsbn());
        bookFx.setRating(book.getRating());
        bookFx.setAuthorFx(AuthorConverter.convertToAuthorFx(book.getAuthor()));
        bookFx.setCategoryFx(CategoryConverter.convertToCategoryFX(book.getCategory()));
        bookFx.setReleaseDate(Utils.convertToLocalDate(book.getReleaseDate()));
        return bookFx;
    }
}
