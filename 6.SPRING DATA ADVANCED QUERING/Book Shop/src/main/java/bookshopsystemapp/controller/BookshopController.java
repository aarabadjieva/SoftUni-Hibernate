package bookshopsystemapp.controller;

import bookshopsystemapp.service.AuthorService;
import bookshopsystemapp.service.BookService;
import bookshopsystemapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

@Controller
public class BookshopController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;
    private final Scanner scanner;

    @Autowired
    public BookshopController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... strings) throws Exception {
        this.authorService.seedAuthors();
        this.categoryService.seedCategories();
        this.bookService.seedBooks();

        //this.bookService.getAllTitlesByAgeRestriction().forEach(System.out::println);
        //this.bookService.getTitlesOfGoldenEditionsWithLessThan5000Copies().forEach(System.out::println);
        //this.bookService.getBooksWithPriceLessThan5OrPriceMoreThan40().forEach(b-> System.out.println(b.getTitle()+" - "+b.getPrice()));
        //this.bookService.getTitlesOfBooksReleasedBeforeOrAfter().forEach(System.out::println);
        //this.bookService.getBooksReleasedBefore().forEach(b-> System.out.println(b.getTitle()+" "+b.getEditionType()+" "+b.getPrice()));
        //this.authorService.getAllNamesEndingWith().forEach(a-> System.out.println(a.getFirstName()+" "+a.getLastName()));
        //this.bookService.getTitlesContaining().forEach(System.out::println);
        //this.bookService.getBooksByAuthorsLastNameContaining().forEach(b-> System.out.println(b.getTitle()+" ("+b.getAuthor().getFirstName()+
        //" "+ b.getAuthor().getLastName()+")"));
        //this.bookService.getNumberOfBooksWithTitleLongerThan();
        //this.bookService.getAuthorsByBookCopies().forEach(System.out::println);
        //System.out.println(this.bookService.getBookInfoByTitle());
        //System.out.println(this.bookService.increaseBookCopiesOfBooksReleasedAfter());
        //System.out.println(this.bookService.removeBooksWithCopiesLessThan());

        System.out.println(this.bookService.getAuthorBooksCount());

    }
}
