package mostwanted.service;

import mostwanted.domain.dtos.races.EntryImportDto;
import mostwanted.domain.dtos.races.RaceImportDto;
import mostwanted.domain.dtos.races.RaceImportRootDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RaceRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RaceServiceImpl implements RaceService {

    private final static String RACES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/races.xml";

    private final RaceRepository raceRepository;
    private final RaceEntryRepository raceEntryRepository;
    private final DistrictRepository districtRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;

    @Autowired
    public RaceServiceImpl(RaceRepository raceRepository, RaceEntryRepository raceEntryRepository, DistrictRepository districtRepository, FileUtil fileUtil, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validator) {
        this.raceRepository = raceRepository;
        this.raceEntryRepository = raceEntryRepository;
        this.districtRepository = districtRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public Boolean racesAreImported() {
        return raceRepository.count()>0;
    }

    @Override
    public String readRacesXmlFile() throws IOException {
        return fileUtil.readFile(RACES_XML_FILE_PATH);
    }

    @Override
    public String importRaces() throws JAXBException {
        RaceImportRootDto raceImportRootDto = xmlParser.parseXml(RaceImportRootDto.class, RACES_XML_FILE_PATH);
        List<String> result = new ArrayList<>();
        for (RaceImportDto raceDto : raceImportRootDto.getRaces()) {
            District district = districtRepository.findByName(raceDto.getDistrictName()).orElse(null);
            List<RaceEntry> raceEntries = new ArrayList<>();
            for (EntryImportDto entryDto : raceDto.getEntryImportRootDto().getEntries()) {
                RaceEntry raceEntry = raceEntryRepository.findById(entryDto.getEntryId()).orElse(null);
                raceEntries.add(raceEntry);
            }
            if (district==null||raceEntries.contains(null)){
                result.add("Error: Incorrect Data!");
                continue;
            }
            Race race = new Race();
            race.setLaps(raceDto.getLaps());
            race.setDistrict(district);
            race.setEntries(raceEntries);
            raceRepository.saveAndFlush(race);
            for (RaceEntry entry : race.getEntries()) {
                entry.setRace(race);
                raceEntryRepository.saveAndFlush(entry);
            }
            result.add(String.format("Successfully imported Race - %d.", race.getId()));
        }
        return String.join("\n", result);
    }
}