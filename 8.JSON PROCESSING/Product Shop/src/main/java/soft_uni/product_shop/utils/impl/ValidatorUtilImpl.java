package soft_uni.product_shop.utils.impl;

import org.springframework.beans.factory.annotation.Autowired;
import soft_uni.product_shop.utils.ValidatorUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class ValidatorUtilImpl implements ValidatorUtil {

    private final Validator validator;

    @Autowired
    public ValidatorUtilImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <E> boolean isValid(E entity) {
        return this.validator.validate(entity).size() == 0;
    }

    @Override
    public <E> String violations(E entity) {
        StringBuilder sb = new StringBuilder();
        Set<ConstraintViolation<E>> violations = validator.validate(entity);
        for (ConstraintViolation<E> violation : violations) {
            sb.append(violation.getMessage()).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
