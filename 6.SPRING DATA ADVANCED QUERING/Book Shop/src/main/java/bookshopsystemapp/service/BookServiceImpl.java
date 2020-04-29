package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.*;
import bookshopsystemapp.repository.AuthorRepository;
import bookshopsystemapp.repository.BookRepository;
import bookshopsystemapp.repository.CategoryRepository;
import bookshopsystemapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final static String BOOKS_FILE_PATH = "D:\\Programming\\6.Hibernate\\6.SPRING DATA ADVANCED QUERING\\Book Shop\\src\\main\\resources\\files\\books.txt";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;
    private final Scanner scanner;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() != 0) {
            return;
        }

        String[] booksFileContent = this.fileUtil.getFileContent(BOOKS_FILE_PATH);
        for (String line : booksFileContent) {
            String[] lineParams = line.split("\\s+");

            Book book = new Book();
            book.setAuthor(this.getRandomAuthor());

            EditionType editionType = EditionType.values()[Integer.parseInt(lineParams[0])];
            book.setEditionType(editionType);

            LocalDate releaseDate = LocalDate.parse(lineParams[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setReleaseDate(releaseDate);

            int copies = Integer.parseInt(lineParams[2]);
            book.setCopies(copies);

            BigDecimal price = new BigDecimal(lineParams[3]);
            book.setPrice(price);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(lineParams[4])];
            book.setAgeRestriction(ageRestriction);

            StringBuilder title = new StringBuilder();
            for (int i = 5; i < lineParams.length; i++) {
                title.append(lineParams[i]).append(" ");
            }

            book.setTitle(title.toString().trim());

            Set<Category> categories = this.getRandomCategories();
            book.setCategories(categories);

            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<String> getAllBooksTitlesAfter() {
        List<Book> books = this.bookRepository.findAllByReleaseDateAfter(LocalDate.parse("2000-12-31"));

        return books.stream().map(b -> b.getTitle()).collect(Collectors.toList());
    }

    @Override
    public Set<String> getAllAuthorsWithBookBefore() {
        List<Book> books = this.bookRepository.findAllByReleaseDateBefore(LocalDate.parse("1990-01-01"));

        return books.stream().map(b -> String.format("%s %s", b.getAuthor().getFirstName(), b.getAuthor().getLastName())).collect(Collectors.toSet());
    }

    @Override
    public List<String> getAllTitlesByAgeRestriction() {
        AgeRestriction ageRestriction = AgeRestriction.valueOf(scanner.nextLine().toUpperCase());
        List<Book> books = this.bookRepository.findAllByAgeRestriction(ageRestriction);
        List<String> titles = new ArrayList<>();
        for (Book b : books
        ) {
            titles.add(b.getTitle());
        }
        return titles;
    }

    @Override
    public List<String> getTitlesOfGoldenEditionsWithLessThan5000Copies() {
        List<Book> books = this.bookRepository.findAllByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000);
        List<String> titles = new ArrayList<>();
        for (Book b : books
        ) {
            titles.add(b.getTitle());
        }
        return titles;
    }

    @Override
    public List<Book> getBooksWithPriceLessThan5OrPriceMoreThan40() {
        List<Book> books = this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(5L), BigDecimal.valueOf(40L));
        return books;
    }

    @Override
    public List<String> getTitlesOfBooksReleasedBeforeOrAfter() {
        String date = scanner.nextLine();
        LocalDate beforeDate = LocalDate.parse(date + "-01-01");
        LocalDate afterDate = LocalDate.parse(date + "-12-31");
        List<Book> books = this.bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(beforeDate, afterDate);
        List<String> titles = new ArrayList<>();
        for (Book b : books
        ) {
            titles.add(b.getTitle());
        }
        return titles;
    }

    @Override
    public List<Book> getBooksReleasedBefore() {
        List<Book> books = this.bookRepository.findAllByReleaseDateBefore(LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("d-M-yyyy")));
        return books;
    }

    @Override
    public List<String> getTitlesContaining() {
        String line = scanner.nextLine();
        List<Book> books = this.bookRepository.findByTitleContainsIgnoreCase(line);
        List<String> titles = new ArrayList<>();
        for (Book b : books
        ) {
            titles.add(b.getTitle());
        }
        return titles;
    }

    @Override
    public List<Book> getBooksByAuthorsLastNameContaining() {
        String line = scanner.nextLine();
        return this.bookRepository.findByAuthorLastNameContainsIgnoreCase(line);
    }

    @Override
    public void getNumberOfBooksWithTitleLongerThan() {
        int minLenght = Integer.parseInt(scanner.nextLine());
        List<Book> books = this.bookRepository.findByTitleLengthLongerThan(minLenght);
        System.out.printf("There are %d books with longer title than %d symbols\n", books.size(), minLenght);
    }

    @Override
    public List<String> getAuthorsByBookCopies() {
        return this.bookRepository.getAuthorsByBooksCopies().stream()
                   .map(obj-> (String) obj)
                    .collect(Collectors.toList());
    }

    @Override
    public String getBookInfoByTitle() {
        String title = scanner.nextLine();
        return  this.bookRepository.getBookInformationByTitle(title).toString();
    }

    @Override
    public Integer increaseBookCopiesOfBooksReleasedAfter() {
        LocalDate releaseDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd MMM yyyy").withLocale(Locale.US));
        int moreCopies = Integer.parseInt(scanner.nextLine());
        return this.bookRepository.increaseBookCopiesOfBooksReleasedAfter(releaseDate, moreCopies)*moreCopies;
    }

    @Override
    public Integer removeBooksWithCopiesLessThan() {
        int minRequredCopies = Integer.parseInt(scanner.nextLine());
        return this.bookRepository.removeBooksWithCopiesLessThan(minRequredCopies);
    }

    @Override
    public String getAuthorBooksCount() {
        String[] name = scanner.nextLine().split("\\s");
        int booksCount = this.bookRepository.getAuthorsBooksCount(name[0], name[1]);
        return String.format("%s %s has written %d books.", name[0], name[1], booksCount);
    }

    private Author getRandomAuthor() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.authorRepository.count() - 1)) + 1;

        return this.authorRepository.findById(randomId).orElse(null);
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new LinkedHashSet<>();

        Random random = new Random();
        int length = random.nextInt(5);

        for (int i = 0; i < length; i++) {
            Category category = this.getRandomCategory();

            categories.add(category);
        }

        return categories;
    }

    private Category getRandomCategory() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.categoryRepository.count() - 1)) + 1;

        return this.categoryRepository.findById(randomId).orElse(null);
    }
}
