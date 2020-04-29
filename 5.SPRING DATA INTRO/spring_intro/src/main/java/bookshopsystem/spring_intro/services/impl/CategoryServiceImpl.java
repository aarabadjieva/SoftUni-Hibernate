package bookshopsystem.spring_intro.services.impl;

import bookshopsystem.spring_intro.entities.Category;
import bookshopsystem.spring_intro.repositories.CategoryRepository;
import bookshopsystem.spring_intro.services.CategoryService;
import bookshopsystem.spring_intro.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final static String CATEGORY_FILE_PATH = "D:\\Programming\\6.Hibernate\\5.SPRING DATA INTRO\\files\\categories.txt";

    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() != 0) {
            return;
        }

        String[] categories = this.fileUtil.fileContent(CATEGORY_FILE_PATH);
        for (String s : categories
        ) {
            Category category = new Category();
            category.setName(s);
            this.categoryRepository.saveAndFlush(category);
        }
    }
}
