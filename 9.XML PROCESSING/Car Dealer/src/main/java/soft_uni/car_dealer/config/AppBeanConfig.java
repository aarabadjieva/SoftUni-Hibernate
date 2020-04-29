package soft_uni.car_dealer.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import soft_uni.car_dealer.config.utils.impl.FileUtilImpl;
import soft_uni.car_dealer.config.utils.impl.ValidatorUtilImpl;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Random;

@Configuration
public class AppBeanConfig {

    @Bean
    public FileUtilImpl fileUtil(){
        return new FileUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public Validator validator(){
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ValidatorUtilImpl validatorUtil(){
        return new ValidatorUtilImpl(this.validator());
    }

    @Bean
    public Random random(){
        return new Random();
    }
}
