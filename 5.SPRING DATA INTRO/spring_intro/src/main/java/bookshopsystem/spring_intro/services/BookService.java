package bookshopsystem.spring_intro.services;

import bookshopsystem.spring_intro.entities.Author;
import bookshopsystem.spring_intro.entities.Book;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

public interface BookService {
    void seedBooks() throws IOException, ParseException;

    List<String> findAllTitles();

    Set<Author> findAllAuthors();

    List<Book> getAllBooks();
}
