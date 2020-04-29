package soft_uni.car_dealer.config.utils.impl;

import soft_uni.car_dealer.config.utils.ValidatorUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;


public class ValidatorUtilImpl implements ValidatorUtil {

    private final Validator validator;

    public ValidatorUtilImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <E> boolean isValid(E entity) {
       return this.validator.validate(entity).size()==0;
    }

    @Override
    public <E> String violations(E entity) {
        StringBuilder sb = new StringBuilder();
        Set<ConstraintViolation<E>> violations = this.validator.validate(entity);
        for (ConstraintViolation<E> violation : violations) {
            sb.append(violation.getMessage()).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
