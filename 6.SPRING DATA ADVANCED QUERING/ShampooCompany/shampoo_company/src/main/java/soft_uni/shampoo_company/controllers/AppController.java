package soft_uni.shampoo_company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import soft_uni.shampoo_company.entities.Ingredient;
import soft_uni.shampoo_company.entities.Shampoo;
import soft_uni.shampoo_company.entities.Size;
import soft_uni.shampoo_company.repositories.IngredientRepository;
import soft_uni.shampoo_company.repositories.LabelRepository;
import soft_uni.shampoo_company.repositories.ShampooRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
public class AppController implements CommandLineRunner {

    private final LabelRepository labelRepository;
    private final ShampooRepository shampooRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public AppController(LabelRepository labelRepository,
                         ShampooRepository shampooRepository,
                         IngredientRepository ingredientRepository) {
        this.labelRepository = labelRepository;
        this.shampooRepository = shampooRepository;
        this.ingredientRepository = ingredientRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        List<Shampoo> shampoos = new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>();
        //Size size = Size.valueOf(scanner.nextLine().toUpperCase());
        //Long labelId = scanner.nextLong();
        //BigDecimal price = scanner.nextBigDecimal();
        //String brandName = scanner.nextLine();
        //Character letter = scanner.nextLine().toUpperCase().charAt(0);
        String ingredientName = scanner.nextLine();
        while (!ingredientName.isBlank()){
            Ingredient ingredient = this.ingredientRepository.findByName(ingredientName);
            ingredients.add(ingredient);
            ingredientName = scanner.nextLine();
        }

        //shampoos = this.shampooRepository.findBySizeOrderById(size);
        //shampoos = this.shampooRepository.findBySizeOrLabel_IdOrderByPriceAsc(size, labelId);
        //shampoos = this.shampooRepository.findByPriceGreaterThanOrderByPriceDesc(price);
        //shampoos = this.shampooRepository.findByPriceLessThan(price);
        //shampoos = this.shampooRepository.findAllByIngredients(ingredients);
        //shampoos = this.shampooRepository.findAllByIngredientsCount(Integer.parseInt(scanner.nextLine()));
        //printShampooDetails(shampoos);
        //System.out.println(this.shampooRepository.getIngredientsCostForShampoo(brandName));
        //System.out.println(shampoos.size());

        this.ingredientRepository.updateIngredientsPriceIfInList(ingredients);
        //this.ingredientRepository.updateIngredientsPrice();
        //this.ingredientRepository.deleteIngredientByGivenName(ingredientName);
        //ingredients = this.ingredientRepository.findByNameIsStartingWith(letter);
        //ingredients = this.ingredientRepository.findAllByNameInOrderByPriceAsc(ingredientNames);
        //printIngredientDetails(ingredients);

    }

    public void printShampooDetails(List<Shampoo> shampoos){
        shampoos.forEach(s -> System.out.println(s.getBrand() + " " +
                s.getSize() + " " + s.getPrice()));
    }

    public void printIngredientDetails(List<Ingredient> ingredients){
        ingredients.forEach(i-> System.out.println(i.getName()));
    }
}
