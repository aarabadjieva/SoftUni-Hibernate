package softuni.product_shop_with_xml.configuration;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.product_shop_with_xml.utils.impl.FileUtilImpl;
import softuni.product_shop_with_xml.utils.impl.ValidatorUtilImpl;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ApplicationBeanConfiguration {

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
        return new ValidatorUtilImpl(validator());
    }
}
