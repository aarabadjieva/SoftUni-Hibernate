package softuni.product_shop_with_xml.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.product_shop_with_xml.domain.dtos.binding.CategoryRootDto;
import softuni.product_shop_with_xml.domain.dtos.binding.ProductRootDto;
import softuni.product_shop_with_xml.domain.dtos.binding.UserRootDto;
import softuni.product_shop_with_xml.domain.dtos.view.xmlRoots.CategoryStatisticRootDto;
import softuni.product_shop_with_xml.domain.dtos.view.xmlRoots.ProductInRangeRootDto;
import softuni.product_shop_with_xml.domain.dtos.view.xmlRoots.SellersRootDto;
import softuni.product_shop_with_xml.domain.dtos.view.UsersAndProductsDto;
import softuni.product_shop_with_xml.services.CategoryService;
import softuni.product_shop_with_xml.services.ProductService;
import softuni.product_shop_with_xml.services.UserService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

@Controller
public class ProductShopController implements CommandLineRunner {

    private final static String USER_IN_XML_FILE_PATH = "D:\\Programming\\6.Hibernate\\9.XML PROCESSING\\Product Shop\\src\\main\\resources\\xml\\input\\users.xml";
    private final static String CATEGORY_IN_XML_FILE_PATH = "D:\\Programming\\6.Hibernate\\9.XML PROCESSING\\Product Shop\\src\\main\\resources\\xml\\input\\categories.xml";
    private final static String PRODUCT_IN_XML_FILE_PATH = "D:\\Programming\\6.Hibernate\\9.XML PROCESSING\\Product Shop\\src\\main\\resources\\xml\\input\\products.xml";
    private final static String OUTPUT_XML_FILE_PATH = "D:\\Programming\\6.Hibernate\\9.XML PROCESSING\\Product Shop\\src\\main\\resources\\xml\\output";


    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public ProductShopController(UserService userService, CategoryService categoryService, ProductService productService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
//        seedUsers();
//        seedCategories();
//        seedProducts();
//        findProductsInRange();
//        findSellersAndSuccessfullySoldProducts();
//        findCategoriesByProducts();
        findUsersAndSoldProducts();

    }

    private void findUsersAndSoldProducts() throws JAXBException, FileNotFoundException {
        UsersAndProductsDto usersAndProductsDto = this.userService.usersAndProducts();
        JAXBContext context = JAXBContext.newInstance(UsersAndProductsDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(usersAndProductsDto, new FileOutputStream(OUTPUT_XML_FILE_PATH + "\\users-and-products.xml"));
    }

    private void findCategoriesByProducts() throws JAXBException, FileNotFoundException {
        CategoryStatisticRootDto categoryStatistics = this.categoryService.categoryStatistics();
        JAXBContext context = JAXBContext.newInstance(CategoryStatisticRootDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(categoryStatistics, new FileOutputStream(OUTPUT_XML_FILE_PATH + "\\categories-by-products.xml"));

    }

    private void findSellersAndSuccessfullySoldProducts() throws JAXBException, FileNotFoundException {
        SellersRootDto sellers = this.userService.sellersWithSales();
        JAXBContext context = JAXBContext.newInstance(SellersRootDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(sellers, new FileOutputStream(OUTPUT_XML_FILE_PATH + "\\user-sold-products.xml"));
    }

    private void findProductsInRange() throws JAXBException, FileNotFoundException {
        ProductInRangeRootDto productsInRangeDtos = this.productService.productsInRange(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));
        JAXBContext context = JAXBContext.newInstance(ProductInRangeRootDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(productsInRangeDtos, new FileOutputStream(OUTPUT_XML_FILE_PATH + "\\products-in-range.xml"));
    }


    private void seedCategories() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(CategoryRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        CategoryRootDto categoryList = (CategoryRootDto) unmarshaller.unmarshal(new FileInputStream(CATEGORY_IN_XML_FILE_PATH));
        this.categoryService.seedCategories(categoryList);
    }

    private void seedUsers() throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(UserRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        UserRootDto userList = (UserRootDto) unmarshaller.unmarshal(new FileInputStream(USER_IN_XML_FILE_PATH));
        this.userService.seedUsers(userList);
    }

    private void seedProducts() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(ProductRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ProductRootDto productList = (ProductRootDto) unmarshaller.unmarshal(new FileInputStream(PRODUCT_IN_XML_FILE_PATH));
        this.productService.seedProducts(productList);
    }
}
