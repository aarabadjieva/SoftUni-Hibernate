package bookshopsystem.spring_intro.services.impl;

import bookshopsystem.spring_intro.entities.Author;
import bookshopsystem.spring_intro.entities.Book;
import bookshopsystem.spring_intro.entities.Category;
import bookshopsystem.spring_intro.enums.AgeRestriction;
import bookshopsystem.spring_intro.enums.EditionType;
import bookshopsystem.spring_intro.repositories.AuthorRepository;
import bookshopsystem.spring_intro.repositories.BookRepository;
import bookshopsystem.spring_intro.repositories.CategoryRepository;
import bookshopsystem.spring_intro.services.BookService;
import bookshopsystem.spring_intro.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final static String BOOKS_FILE_PATH = "D:\\Programming\\6.Hibernate\\5.SPRING DATA INTRO\\files\\books.txt";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedBooks() throws IOException, ParseException {
        if (this.bookRepository.count() != 0) {
            return;
        }
        String[] books = this.fileUtil.fileContent(BOOKS_FILE_PATH);
        for (String s : books
        ) {
            String[] bookData = s.split("\\s+");
            Author author = randomAuthor();
            Set<Category> categories = randomCategories();
            EditionType editionType = EditionType.values()[Integer.parseInt(bookData[0])];
            LocalDate releaseDate = LocalDate.parse(bookData[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            int copies = Integer.parseInt(bookData[2]);
            BigDecimal price = new BigDecimal(bookData[3]);
            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(bookData[4])];
            StringBuilder titleBuilder = new StringBuilder();
            for (int i = 5; i < bookData.length; i++) {
                titleBuilder.append(bookData[i]).append(" ");
            }
            String title = titleBuilder.toString().trim();

            Book book = new Book();
            book.setAuthor(author);
            book.setEditionType(editionType);
            book.setReleaseDate(releaseDate);
            book.setAgeRestriction(ageRestriction);
            book.setCategories(categories);
            book.setCopies(copies);
            book.setAgeRestriction(ageRestriction);
            book.setTitle(title);
            book.setPrice(price);

            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<String> findAllTitles() {
        LocalDate date = LocalDate.parse("31/12/2000", DateTimeFormatter.ofPattern("d/M/yyyy"));
        return this.bookRepository
                .findAllByReleaseDateAfter(date)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public Set<Author> findAllAuthors() {
        LocalDate date = LocalDate.parse("01/01/1990", DateTimeFormatter.ofPattern("d/M/yyyy"));
        return this.bookRepository
                .findAllByReleaseDateBefore(date)
                .stream()
                .map(b -> b.getAuthor())
                .collect(Collectors.toSet());
    }


    private Author randomAuthor() {
        Random random = new Random();
        int id = random.nextInt((int) this.authorRepository.count()) + 1;
        return this.authorRepository.getOne(id);
    }

    private Category randomCategory() {
        Random random = new Random();
        int id = random.nextInt((int) this.categoryRepository.count()) + 1;
        return this.categoryRepository.getOne(id);
    }

    private Set<Category> randomCategories() {
        Set<Category> categories = new LinkedHashSet<>();
        Random random = new Random();
        int index = random.nextInt((int) this.categoryRepository.count()) + 1;

        for (int i = 0; i < index; i++) {
            categories.add(randomCategory());
        }

        return categories;
    }

    @Override
    public List<Book> getAllBooks() {
        return this.bookRepository.getAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitleAsc("George", "Powell");

    }
}
