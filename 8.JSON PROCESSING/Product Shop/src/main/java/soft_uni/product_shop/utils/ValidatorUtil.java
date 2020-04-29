package soft_uni.product_shop.utils;

public interface ValidatorUtil {

    <E> boolean isValid(E entity);

    <E> String violations(E entity);
}
