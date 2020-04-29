package alararestaurant.service;

import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String exportCategoriesByCountOfItems() {
        List<Category> categories = categoryRepository.findAll()
                .stream().sorted((c1, c2)->{
                    int comp = c2.getItems().size()-c1.getItems().size();
                    if (comp == 0){
                        comp = c2.getItems().stream()
                                .map(Item::getPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add).compareTo(
                                c1.getItems().stream()
                                .map(Item::getPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add));
                    }
                    return comp;
                }).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        for (Category category : categories) {
            sb.append(String.format("Category: %s\n", category.getName()));
            for (Item item : category.getItems()) {
                sb.append(String.format("--- Item Name: %s\n" +
                        "--- Item Price: %.2f\n", item.getName(), item.getPrice()))
                        .append(System.lineSeparator());
            }
        }
        return sb.toString().trim();
    }
}
