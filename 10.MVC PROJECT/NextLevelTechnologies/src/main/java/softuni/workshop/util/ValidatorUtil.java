package softuni.workshop.util;

public interface ValidatorUtil {

    <E> boolean isValid(E entity);

    <E> String violations(E entity);
}
