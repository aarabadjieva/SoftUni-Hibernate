package softuni.workshop.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidatorUtilImpl  implements ValidatorUtil{

    private Validator validator;

    public ValidatorUtilImpl() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public <E> boolean isValid(E entity) {
        return this.validator.validate(entity).size()==0;
    }

    @Override
    public <E> String violations(E entity) {
        Set<ConstraintViolation<E>> violations = this.validator.validate(entity);
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<E> violation : violations) {
            sb.append(violation.getMessage()).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
