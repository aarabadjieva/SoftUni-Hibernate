package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.xml.PictureDto;
import softuni.exam.domain.dtos.xml.PictureRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    private final static String PICTURES_XML_FILE_PATH = "D:\\Programming\\6.Hibernate\\EXAM\\Football-Info\\src\\main\\resources\\files\\xml\\pictures.xml";

    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validator;
    private final XmlParser xmlParser;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidatorUtil validator, XmlParser xmlParser) {
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.xmlParser = xmlParser;
    }

    @Override
    public String importPictures() throws JAXBException {
        PictureRootDto pictureRootDto = xmlParser.parseXml(PictureRootDto.class, PICTURES_XML_FILE_PATH);
        List<String> result = new ArrayList<>();
        for (PictureDto pictureDto : pictureRootDto.getPictureDtos()) {
            Picture picture = modelMapper.map(pictureDto, Picture.class);
            if (!validator.isValid(picture)) {
                result.add("Invalid picture");
                continue;
            }
            pictureRepository.saveAndFlush(picture);
            result.add(String.format("Successfully imported picture - %s", picture.getUrl()));
        }
        return String.join("\n", result);
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return fileUtil.readFile(PICTURES_XML_FILE_PATH);
    }

}
