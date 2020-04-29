package alararestaurant.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlParserImpl implements XmlParser{

    @Override
    @SuppressWarnings(value = "unchecked")
    public <O> O importXml(Class<O> objectClass, String filePath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(objectClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (O) unmarshaller.unmarshal(new File(filePath));
    }

    @Override
    public <O> void exportXml(O entity, String filePath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(entity.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(entity, new File(filePath));
    }
}
