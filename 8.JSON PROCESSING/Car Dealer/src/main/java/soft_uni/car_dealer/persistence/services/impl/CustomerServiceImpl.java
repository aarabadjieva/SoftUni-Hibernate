package soft_uni.car_dealer.persistence.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_uni.car_dealer.config.utils.impl.ValidatorUtilImpl;
import soft_uni.car_dealer.domain.dtos.seedModels.CustomerDto;
import soft_uni.car_dealer.domain.dtos.viewModels.BuyerDto;
import soft_uni.car_dealer.domain.dtos.viewModels.CustomerInfoDto;
import soft_uni.car_dealer.domain.entities.Customer;
import soft_uni.car_dealer.domain.entities.Part;
import soft_uni.car_dealer.domain.entities.Sale;
import soft_uni.car_dealer.persistence.repositories.CustomerRepository;
import soft_uni.car_dealer.persistence.services.CustomerService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final ValidatorUtilImpl validatorUtil;
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(ValidatorUtilImpl validatorUtil, ModelMapper modelMapper, CustomerRepository customerRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public void seedCustomers(CustomerDto[] customerDtos) {
        for (CustomerDto customerDto : customerDtos) {
            if (!this.validatorUtil.isValid(customerDto)){
                System.out.println(this.validatorUtil.violations(customerDto));
            }else {
                Customer customer = this.modelMapper.map(customerDto, Customer.class);
                this.customerRepository.saveAndFlush(customer);
            }
        }
    }

    @Override
    public Set<CustomerInfoDto> getOrderedCustomers() {
        Set<Customer> customers = this.customerRepository.findAllByBirthDate();
        Set<CustomerInfoDto> customerInfoDtos = new LinkedHashSet<>();
        for (Customer customer : customers) {
            CustomerInfoDto customerInfoDto = this.modelMapper.map(customer, CustomerInfoDto.class);
            customerInfoDtos.add(customerInfoDto);
        }
        return customerInfoDtos;
    }

    @Override
    public List<BuyerDto> getBuyers() {
        return this.customerRepository.getCustomersWithCarsBought(10)
                .stream().map(c->{
                    BuyerDto buyer = new BuyerDto();
                    buyer.setFullName(c.getName());
                    buyer.setBoughtCars(c.getSales().size());
                    buyer.setSpendMoney(calculateSpendMoney(c));
                    return buyer;
                })
                .sorted((b1, b2)->{
                    int result = b2.getSpendMoney().compareTo(b1.getSpendMoney());
                    if (result==0){
                        result = b2.getBoughtCars()-b1.getBoughtCars();
                    }
                    return result;
                })
                .collect(Collectors.toList());
    }

    private BigDecimal calculateSpendMoney(Customer c) {
        BigDecimal spentMoney = BigDecimal.ZERO;
        for (Sale sale : c.getSales()) {
            BigDecimal totalprice = BigDecimal.ZERO;
            for (Part part : sale.getCar().getParts()) {
                totalprice = totalprice.add(part.getPrice());
            }
            totalprice = totalprice.subtract(totalprice.multiply(BigDecimal.valueOf(sale.getDiscount())));
            spentMoney = spentMoney.add(totalprice);
        }
        return spentMoney;
    }
}
