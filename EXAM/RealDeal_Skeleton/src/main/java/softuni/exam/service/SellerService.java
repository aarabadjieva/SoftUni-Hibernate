package softuni.exam.service;

import softuni.exam.models.entity.Seller;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface SellerService {

    Seller findSellerById(int id);

    boolean areImported();

    String readSellersFromFile() throws IOException;

    String importSellers() throws IOException, JAXBException;

}
