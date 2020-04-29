package bookshopsystem.spring_intro.services.impl;

import bookshopsystem.spring_intro.entities.Author;
import bookshopsystem.spring_intro.repositories.AuthorRepository;
import bookshopsystem.spring_intro.services.AuthorService;
import bookshopsystem.spring_intro.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final static String AUTHOR_FILE_PATH = "D:\\Programming\\6.Hibernate\\5.SPRING DATA INTRO\\files\\authors.txt";

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() != 0) {
            return;
        }
        String[] authors = this.fileUtil.fileContent(AUTHOR_FILE_PATH);
        for (String s : authors
        ) {
            String[] params = s.split("\\s+");
            Author author = new Author();
            author.setFirstName(params[0]);
            author.setLastName(params[1]);
            this.authorRepository.saveAndFlush(author);
        }
    }

    @Override
    public List<Author> authorsByCountOfBooks() {
        return this.authorRepository
                .getAllOrderedByBooksCount();
    }
}
