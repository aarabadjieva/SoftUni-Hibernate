package soft_uni.car_dealer.config.utils;

public interface ValidatorUtil {

    <E> boolean isValid(E entity);

    <E> String violations(E entity);
}
