package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.OfferRootSeedDto;
import softuni.exam.models.dto.xml.OfferSeedDto;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Offer;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.OfferRepository;
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
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final CarService carService;
    private final SellerService sellerService;
    private final ModelMapper mapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validator;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, CarService carService, SellerService sellerService, ModelMapper mapper, XmlParser xmlParser, ValidationUtil validator) {
        this.offerRepository = offerRepository;
        this.carService = carService;
        this.sellerService = sellerService;
        this.mapper = mapper;
        this.xmlParser = xmlParser;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count()>0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(PATH_TO_XML_FILES + "offers.xml"));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        OfferRootSeedDto offerRootSeedDto = this.xmlParser.parseXml(OfferRootSeedDto.class, PATH_TO_XML_FILES + "offers.xml");
        List<String> messages = new ArrayList<>();
        for (OfferSeedDto offerDto : offerRootSeedDto.getOffers()) {
            if (this.validator.isValid(offerDto)){
                Car car = this.carService.findCarById(offerDto.getCar().getId());
                Seller seller = this.sellerService.findSellerById(offerDto.getSeller().getId());
                Offer offer = this.offerRepository.findByDescriptionAndAddedOn(offerDto.getDescription(), offerDto.getAddedOn());
                if (car!=null&&seller!=null&&offer==null){
                    offer = this.mapper.map(offerDto, Offer.class);
                    offer.setCar(car);
                    offer.setSeller(seller);
                    this.offerRepository.saveAndFlush(offer);
                    messages.add(String.format(SUCCESSFULLY_IMPORTED_MESSAGE,
                            offer
                    .getClass().getSimpleName().toLowerCase(), offer.getAddedOn(),
                            offer.isHasGoldStatus()));
                }
            }else {
                messages.add(String.format(INVALID_DATA_MESSAGE, "offer"));
            }
        }
        return String.join("\n", messages);
    }
}
