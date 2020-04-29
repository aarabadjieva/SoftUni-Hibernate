package soft_uni.car_dealer.persistence.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import soft_uni.car_dealer.config.utils.impl.ValidatorUtilImpl;
import soft_uni.car_dealer.domain.dtos.viewModels.SaleInfoDto;
import soft_uni.car_dealer.domain.dtos.viewModels.xmlRoots.SaleInfoRootDto;
import soft_uni.car_dealer.domain.entities.Car;
import soft_uni.car_dealer.domain.entities.Customer;
import soft_uni.car_dealer.domain.entities.Part;
import soft_uni.car_dealer.domain.entities.Sale;
import soft_uni.car_dealer.persistence.repositories.CarRepository;
import soft_uni.car_dealer.persistence.repositories.CustomerRepository;
import soft_uni.car_dealer.persistence.repositories.SaleRepository;
import soft_uni.car_dealer.persistence.services.SaleService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;
    private final Double[] discounts = {0d, 0.05d, 0.1d, 0.15d, 0.2d, 0.3d, 0.4d, 0.5d};
    private final Random random;
    private final ValidatorUtilImpl validatorUtil;
    private final ModelMapper modelMapper;

    public SaleServiceImpl(CarRepository carRepository, CustomerRepository customerRepository, SaleRepository saleRepository, Random random, ValidatorUtilImpl validatorUtil, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;
        this.random = random;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void generateSales() {
        List<Car> cars = this.carRepository.findAll();
        for (Car car : cars) {
            Sale sale = new Sale();
            sale.setCar(car);
            sale.setCustomer(getRandomCustomer());
            sale.setDiscount(getRandomDiscount(sale.getCustomer()));
            if (!this.validatorUtil.isValid(sale)){
                this.validatorUtil.violations(sale);
            }else {
                this.saleRepository.saveAndFlush(sale);
            }
        }
    }

    @Override
    public SaleInfoRootDto getSales() {
        SaleInfoRootDto saleInfoRootDto = new SaleInfoRootDto();
        saleInfoRootDto.setSaleInfoDtos(this.saleRepository.findAll()
                .stream()
                .map(s->{
                    SaleInfoDto saleInfoDto = this.modelMapper.map(s, SaleInfoDto.class);
                    saleInfoDto.setCustomerName(s.getCustomer().getName());
                    saleInfoDto.setPrice(s.getCar().getParts()
                            .stream().map(Part::getPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add));
                    saleInfoDto.setPriceWithDiscount(saleInfoDto.getPrice()
                            .subtract(saleInfoDto.getPrice().multiply(BigDecimal.valueOf(saleInfoDto.getDiscount()))));
                    return saleInfoDto;
                })
                .collect(Collectors.toList()));
        return saleInfoRootDto;
    }

    private Double getRandomDiscount(Customer customer) {
        int index = this.random.nextInt(this.discounts.length);
        double discount = this.discounts[index];
        if (customer.getIsYoungDriver()){
            discount = discount + 0.05d;
        }
        return discount;
    }

    private Customer getRandomCustomer() {
        int customerId = this.random.nextInt((int) this.customerRepository.count()) +1;
        return this.customerRepository.getOne(customerId);
    }
}
