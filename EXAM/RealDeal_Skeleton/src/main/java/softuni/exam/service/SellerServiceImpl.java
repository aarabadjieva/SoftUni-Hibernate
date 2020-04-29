package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.SellerRootSeedDto;
import softuni.exam.models.dto.xml.SellerSeedDto;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static softuni.exam.common.Constants.*;

@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final ModelMapper mapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validator;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper mapper, XmlParser xmlParser, ValidationUtil validator) {
        this.sellerRepository = sellerRepository;
        this.mapper = mapper;
        this.xmlParser = xmlParser;
        this.validator = validator;
    }

    @Override
    public Seller findSellerById(int id) {
        return this.sellerRepository.findById(id).orElse(null);
    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count()>0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(Path.of(PATH_TO_XML_FILES + "sellers.xml"));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        SellerRootSeedDto sellerRootSeedDto= xmlParser.parseXml(SellerRootSeedDto.class, PATH_TO_XML_FILES + "sellers.xml");
        List<String> messages = new ArrayList<>();
        for (SellerSeedDto sellerDto : sellerRootSeedDto.getSellers()) {
            if (this.validator.isValid(sellerDto)){
                if (this.sellerRepository.findByEmail(sellerDto.getEmail())==null){
                    Seller seller = this.mapper.map(sellerDto, Seller.class);
                    this.sellerRepository.saveAndFlush(seller);
                    messages.add(String.format(SUCCESSFULLY_IMPORTED_MESSAGE,
                            seller.getClass().getSimpleName().toLowerCase(),
                            seller.getLastName(), seller.getEmail()));
                }
            }else {
                messages.add(String.format(INVALID_DATA_MESSAGE, "seller"));
            }
        }
        return String.join("\n", messages);
    }
}
