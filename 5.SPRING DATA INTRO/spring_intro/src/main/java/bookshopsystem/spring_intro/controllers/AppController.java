package bookshopsystem.spring_intro.controllers;

import bookshopsystem.spring_intro.entities.Author;
import bookshopsystem.spring_intro.entities.Book;
import bookshopsystem.spring_intro.services.AuthorService;
import bookshopsystem.spring_intro.services.BookService;
import bookshopsystem.spring_intro.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;

@Controller
public class AppController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    @Autowired
    public AppController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.authorService.seedAuthors();
        this.categoryService.seedCategories();
        this.bookService.seedBooks();

        List<String> titles = this.bookService.findAllTitles();
        titles.forEach(System.out::println);
        Set<Author> authors = this.bookService.findAllAuthors();
        authors.forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
        List<Author> authorList = this.authorService.authorsByCountOfBooks();
        authorList.forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName() + " " + a.getBooks().size()));
        List<Book> bookList = this.bookService.getAllBooks();
        bookList.forEach(b -> System.out.println(b.getTitle() + " " + b.getReleaseDate() + " " + b.getCopies()));
    }
}
