package softuni.product_shop_with_xml.utils;

public interface ValidatorUtil {

    <E> boolean isValid(E entity);

    <E> String violations(E entity);
}
