package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.RacerImportDto;
import mostwanted.domain.entities.Racer;
import mostwanted.domain.entities.Town;
import mostwanted.repository.RacerRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RacerServiceImpl implements RacerService {

    private final static String RACERS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/racers.json";

    private final RacerRepository racerRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validator;
    private final Gson gson;

    public RacerServiceImpl(RacerRepository racerRepository, TownRepository townRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validator, Gson gson) {
        this.racerRepository = racerRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.gson = gson;
    }

    @Override
    public Boolean racersAreImported() {
        return racerRepository.count()>0;
    }

    @Override
    public String readRacersJsonFile() throws IOException {
        return fileUtil.readFile(RACERS_JSON_FILE_PATH);
    }

    @Override
    public String importRacers(String racersFileContent) throws IOException {
        racersFileContent = readRacersJsonFile();
        RacerImportDto[] racers = gson.fromJson(racersFileContent, RacerImportDto[].class);
        List<String> result = new ArrayList<>();
        for (RacerImportDto racerDto : racers) {
            Town town = townRepository.findByName(racerDto.getHomeTown()).orElse(null);
            Racer racer = modelMapper.map(racerDto, Racer.class);
            if (town==null||!validator.isValid(racer)){
                result.add("Error: Incorrect data!");
                continue;
            }
            racer.setHomeTown(town);
            try {
                racerRepository.saveAndFlush(racer);
                result.add(String.format("Successfully imported Racer – %s.", racer.getName()));
            }catch (Exception e){
                result.add("Error: Duplicate Data!");
            }
        }
        return String.join("\n", result);
    }

    @Override
    public String exportRacingCars() {
        List<Racer> racers = racerRepository.findAllByCountOfCars();
        StringBuilder sb = new StringBuilder();
        for (Racer racer : racers) {
            sb.append(racer.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
