package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.xml.PlaneRootSeedDto;
import softuni.exam.models.dtos.xml.PlaneSeedDto;
import softuni.exam.models.entities.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
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
public class PlaneServiceImpl implements PlaneService {

    private final PlaneRepository planeRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validator;
    private final XmlParser xmlParser;

    @Autowired
    public PlaneServiceImpl(PlaneRepository planeRepository, ModelMapper mapper, ValidationUtil validator, XmlParser xmlParser) {
        this.planeRepository = planeRepository;
        this.mapper = mapper;
        this.validator = validator;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return Files.readString(Path.of(PATH_TO_XML_FILES + "planes.xml"));
    }

    @Override
    public String importPlanes() throws JAXBException {
        PlaneRootSeedDto planeRootSeedDto = this.xmlParser.parseXml(PlaneRootSeedDto.class, PATH_TO_XML_FILES + "planes.xml");
        List<String> messages = new ArrayList<>();
        for (PlaneSeedDto p : planeRootSeedDto.getPlanes()) {
            if (this.validator.isValid(p)){
                if (findPlaneByRegisterNumber(p.getRegisterNumber())==null){
                    Plane plane = this.mapper.map(p, Plane.class);
                    this.planeRepository.saveAndFlush(plane);
                    messages.add(String.format(SUCCESSFULLY_IMPORTED_PLANE_MESSAGE,
                            plane.getClass().getSimpleName(), plane.getRegisterNumber()));
                }
            }else {
                messages.add(String.format(INVALID_DATA_MESSAGE, "Plane"));
            }
        }

        return String.join("\n", messages);
    }

    @Override
    public Plane findPlaneByRegisterNumber(String registerNumber) {
        return this.planeRepository.findByRegisterNumber(registerNumber);
    }
}
