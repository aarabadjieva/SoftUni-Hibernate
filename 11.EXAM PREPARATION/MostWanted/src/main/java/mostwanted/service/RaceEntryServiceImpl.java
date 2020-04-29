package mostwanted.service;

import mostwanted.domain.dtos.raceentries.RaceEntryImportDto;
import mostwanted.domain.dtos.raceentries.RaceEntryImportRootDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RaceEntryServiceImpl implements RaceEntryService {

    private final static String RACE_ENTRIES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/race-entries.xml";

    private final RaceEntryRepository raceEntryRepository;
    private final CarRepository carRepository;
    private final RacerRepository racerRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public RaceEntryServiceImpl(RaceEntryRepository raceEntryRepository, CarRepository carRepository, RacerRepository racerRepository, FileUtil fileUtil, ModelMapper modelMapper, XmlParser xmlParser) {
        this.raceEntryRepository = raceEntryRepository;
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public Boolean raceEntriesAreImported() {
        return raceEntryRepository.count()>0;
    }

    @Override
    public String readRaceEntriesXmlFile() throws IOException {
        return fileUtil.readFile(RACE_ENTRIES_XML_FILE_PATH);
    }

    @Override
    public String importRaceEntries() throws IOException, JAXBException {
        RaceEntryImportRootDto raceEntryImportRootDto = xmlParser.parseXml(RaceEntryImportRootDto.class, RACE_ENTRIES_XML_FILE_PATH);
        List<String> result = new ArrayList<>();
        for (RaceEntryImportDto raceEntryDto : raceEntryImportRootDto.getRaceEntries()) {
            Car car = carRepository.findById(raceEntryDto.getCarId()).orElse(null);
            Racer racer = racerRepository.findByName(raceEntryDto.getRacerName()).orElse(null);
            RaceEntry raceEntry = modelMapper.map(raceEntryDto, RaceEntry.class);
            if (car==null||racer==null){
                result.add("Error: Incorrect Data!");
                continue;
            }
            raceEntry.setCar(car);
            raceEntry.setRacer(racer);
            raceEntry.setRace(null);
            raceEntry.setId(null);
            raceEntryRepository.saveAndFlush(raceEntry);
            result.add(String.format("Successfully imported RaceEntry – %d.", raceEntry.getId()));
        }
        return String.join("\n", result);
    }
}
