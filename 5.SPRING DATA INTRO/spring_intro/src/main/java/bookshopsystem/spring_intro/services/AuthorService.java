package bookshopsystem.spring_intro.services;

import bookshopsystem.spring_intro.entities.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {

    void seedAuthors() throws IOException;

    List<Author> authorsByCountOfBooks();
}
