package soft_uni.car_dealer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import soft_uni.car_dealer.domain.dtos.seedModels.xmlSeedRoots.CarSeedRootDto;
import soft_uni.car_dealer.domain.dtos.seedModels.xmlSeedRoots.CustomerRootSeedDto;
import soft_uni.car_dealer.domain.dtos.seedModels.xmlSeedRoots.PartsSeedRootDto;
import soft_uni.car_dealer.domain.dtos.seedModels.xmlSeedRoots.SupplierSeedRootDto;
import soft_uni.car_dealer.domain.dtos.viewModels.xmlRoots.*;
import soft_uni.car_dealer.persistence.services.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class CarDealerController implements CommandLineRunner {

    private final static String CARS_XML_FILE_PATH = "D:\\Programming\\6.Hibernate\\9.XML PROCESSING\\Car Dealer\\src\\main\\resources\\xml\\input\\cars.xml";
    private final static String CUSTOMERS_XML_FILE_PATH = "D:\\Programming\\6.Hibernate\\9.XML PROCESSING\\Car Dealer\\src\\main\\resources\\xml\\input\\customers.xml";
    private final static String PARTS_XML_FILE_PATH = "D:\\Programming\\6.Hibernate\\9.XML PROCESSING\\Car Dealer\\src\\main\\resources\\xml\\input\\parts.xml";
    private final static String SUPPLIERS_XML_FILE_PATH = "D:\\Programming\\6.Hibernate\\9.XML PROCESSING\\Car Dealer\\src\\main\\resources\\xml\\input\\suppliers.xml";
    private final static String OUTPUT_XML_FILE_PATH = "D:\\Programming\\6.Hibernate\\9.XML PROCESSING\\Car Dealer\\src\\main\\resources\\xml\\output\\";

    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    @Autowired
    public CarDealerController(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
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
//        saveOrderedCustomers();
//        saveCarsByMakeToyota();
//        saveLocalSuppliers();
//        saveCarsPartsInfo();
//        saveBuyers();
        saveSalesWithDiscount();
    }

    private void saveSalesWithDiscount() throws JAXBException, FileNotFoundException {
        SaleInfoRootDto sales = this.saleService.getSales();
        JAXBContext context = JAXBContext.newInstance(SaleInfoRootDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(sales, new FileOutputStream(OUTPUT_XML_FILE_PATH + "sales-discounts.xml"));
    }

    private void saveBuyers() throws JAXBException, FileNotFoundException {
        BuyerRootDto customers = this.customerService.getBuyers();
        JAXBContext context = JAXBContext.newInstance(BuyerRootDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(customers, new FileOutputStream(OUTPUT_XML_FILE_PATH + "customers-total-sales.xml"));
    }

    private void saveCarsPartsInfo() throws FileNotFoundException, JAXBException {
        CarsPartsRootDto cars = this.carService.getCarsWithParts();
        JAXBContext context = JAXBContext.newInstance(CarsPartsRootDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(cars, new FileOutputStream(OUTPUT_XML_FILE_PATH + "cars-and-parts.xml"));
    }

    private void saveLocalSuppliers() throws JAXBException, FileNotFoundException {
        SupplierInfoRootDto suppliers = this.supplierService.getLocalSuppliers();
        JAXBContext context = JAXBContext.newInstance(SupplierInfoRootDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(suppliers, new FileOutputStream(OUTPUT_XML_FILE_PATH + "local-suppliers.xml"));
    }

    private void saveCarsByMakeToyota() throws JAXBException, FileNotFoundException {
        CarsShortInfoRootDto cars = this.carService.getCarsByMake("Toyota");
        JAXBContext context = JAXBContext.newInstance(CarsShortInfoRootDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(cars, new FileOutputStream(OUTPUT_XML_FILE_PATH + "toyota-cars.xml"));
    }

    private void saveOrderedCustomers() throws JAXBException, FileNotFoundException {
        CustomerInfoRootDto customerInfoDtos = this.customerService.getOrderedCustomers();
        JAXBContext context = JAXBContext.newInstance(CustomerInfoRootDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(customerInfoDtos, new FileOutputStream(OUTPUT_XML_FILE_PATH + "ordered-customers.xml"));
    }

    private void generateSales() {
        this.saleService.generateSales();
    }

    private void seedCustomers() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(CustomerRootSeedDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        CustomerRootSeedDto customerRootSeedDto = (CustomerRootSeedDto) unmarshaller.unmarshal(new FileInputStream(CUSTOMERS_XML_FILE_PATH));
        this.customerService.seedCustomers(customerRootSeedDto);
    }

    private void seedCars() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(CarSeedRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        CarSeedRootDto carSeedRootDto = (CarSeedRootDto) unmarshaller.unmarshal(new FileInputStream(CARS_XML_FILE_PATH));
        this.carService.seedCars(carSeedRootDto);
    }

    private void seedParts() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(PartsSeedRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        PartsSeedRootDto partsSeedRootDto = (PartsSeedRootDto) unmarshaller.unmarshal(new FileInputStream(PARTS_XML_FILE_PATH));
        this.partService.seedParts(partsSeedRootDto);
    }

    private void seedSuppliers() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(SupplierSeedRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        SupplierSeedRootDto supplierSeedRootDto = (SupplierSeedRootDto) unmarshaller.unmarshal(new FileInputStream(SUPPLIERS_XML_FILE_PATH));
        this.supplierService.seedSuppliers(supplierSeedRootDto);
    }
}
