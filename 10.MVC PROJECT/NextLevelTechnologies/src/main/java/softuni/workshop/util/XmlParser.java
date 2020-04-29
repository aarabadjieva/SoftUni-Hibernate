package softuni.workshop.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {

    <E> Object importParse(Class<E> objectClass, String xmlContent) throws JAXBException;

}
