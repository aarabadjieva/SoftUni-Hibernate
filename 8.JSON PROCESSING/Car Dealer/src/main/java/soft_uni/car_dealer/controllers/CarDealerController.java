package soft_uni.car_dealer.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import soft_uni.car_dealer.config.utils.impl.FileUtilImpl;
import soft_uni.car_dealer.domain.dtos.seedModels.CarDto;
import soft_uni.car_dealer.domain.dtos.seedModels.CustomerDto;
import soft_uni.car_dealer.domain.dtos.seedModels.PartDto;
import soft_uni.car_dealer.domain.dtos.seedModels.SupplierDto;
import soft_uni.car_dealer.domain.dtos.viewModels.*;
import soft_uni.car_dealer.persistence.services.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Controller
public class CarDealerController implements CommandLineRunner {

    private final static String CARS_JSON_FILE_PATH = "D:\\Programming\\6.Hibernate\\8.JSON PROCESSING\\Car Dealer\\src\\main\\resources\\json\\input\\cars.json";
    private final static String CUSTOMERS_JSON_FILE_PATH = "D:\\Programming\\6.Hibernate\\8.JSON PROCESSING\\Car Dealer\\src\\main\\resources\\json\\input\\customers.json";
    private final static String PARTS_JSON_FILE_PATH = "D:\\Programming\\6.Hibernate\\8.JSON PROCESSING\\Car Dealer\\src\\main\\resources\\json\\input\\parts.json";
    private final static String SUPPLIERS_JSON_FILE_PATH = "D:\\Programming\\6.Hibernate\\8.JSON PROCESSING\\Car Dealer\\src\\main\\resources\\json\\input\\suppliers.json";

    private final FileUtilImpl fileUtil;
    private final Gson gson;
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    @Autowired
    public CarDealerController(FileUtilImpl fileUtil, Gson gson, SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
//        seedSuppliers();
//        seedParts();
//        seedCars();
//        seedCustomers();
//        generateSales();
//        printOrderedCustomers();
//        printCarsByMakeToyota();
//        printLocalSuppliers();
//        printCarsPartsInfo();
//        printBuyers();
        printSales();
    }

    private void printSales() {
        List<SaleInfoDto> sales = this.saleService.getSales();
        System.out.println(this.gson.toJson(sales));
    }

    private void printBuyers() {
        List<BuyerDto> customers = this.customerService.getBuyers();
        System.out.println(this.gson.toJson(customers));
    }

    private void printCarsPartsInfo() {
        List<CarsPartsDto> cars = this.carService.getCarsWithParts();
        System.out.println(this.gson.toJson(cars));
    }

    private void printLocalSuppliers() {
        List<SupplierInfoDto> suppliers = this.supplierService.getLocalSuppliers();
        System.out.println(this.gson.toJson(suppliers));
    }

    private void printCarsByMakeToyota() {
        List<CarShortInfoDto> cars = this.carService.getCarsByMake("Toyota");
        System.out.println(this.gson.toJson(cars));
    }

    private void printOrderedCustomers() {
        Set<CustomerInfoDto> customerInfoDtos = this.customerService.getOrderedCustomers();
        System.out.println(this.gson.toJson(customerInfoDtos));
    }

    private void generateSales() {
        this.saleService.generateSales();
    }

    private void seedCustomers() throws IOException {
        String content = this.fileUtil.fileContent(CUSTOMERS_JSON_FILE_PATH);
        CustomerDto[] customerDtos = this.gson.fromJson(content, CustomerDto[].class);
        this.customerService.seedCustomers(customerDtos);
    }

    private void seedCars() throws IOException {
        String content = this.fileUtil.fileContent(CARS_JSON_FILE_PATH);
        CarDto[] carDtos = this.gson.fromJson(content, CarDto[].class);
        this.carService.seedCars(carDtos);
    }

    private void seedParts() throws IOException {
        String content = this.fileUtil.fileContent(PARTS_JSON_FILE_PATH);
        PartDto[] partDtos = this.gson.fromJson(content, PartDto[].class);
        this.partService.seedParts(partDtos);
    }

    private void seedSuppliers() throws IOException {
        String content = this.fileUtil.fileContent(SUPPLIERS_JSON_FILE_PATH);
        SupplierDto[] supplierDtos = this.gson.fromJson(content, SupplierDto[].class);
        this.supplierService.seedSuppliers(supplierDtos);
    }
}
