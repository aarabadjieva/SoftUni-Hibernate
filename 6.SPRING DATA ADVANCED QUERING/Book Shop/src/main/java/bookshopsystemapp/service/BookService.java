package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.Book;
import bookshopsystemapp.domain.entities.ReducedBook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
public interface BookService {

    void seedBooks() throws IOException;

    List<String> getAllBooksTitlesAfter();

    Set<String> getAllAuthorsWithBookBefore();

    List<String> getAllTitlesByAgeRestriction();

    List<String> getTitlesOfGoldenEditionsWithLessThan5000Copies();

    List<Book> getBooksWithPriceLessThan5OrPriceMoreThan40();

    List<String> getTitlesOfBooksReleasedBeforeOrAfter();

    List<Book> getBooksReleasedBefore();

    List<String> getTitlesContaining();

    List<Book> getBooksByAuthorsLastNameContaining();

    void getNumberOfBooksWithTitleLongerThan();

    List<String>  getAuthorsByBookCopies();

    String getBookInfoByTitle();

    Integer increaseBookCopiesOfBooksReleasedAfter();

    Integer removeBooksWithCopiesLessThan();

    String getAuthorBooksCount();
}
