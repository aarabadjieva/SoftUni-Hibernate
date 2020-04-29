package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.exportDtos.TownExportDto;
import mostwanted.domain.dtos.TownImportDto;
import mostwanted.domain.entities.Town;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TownServiceImpl implements TownService{

    private final static String TOWNS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/towns.json";

    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validator;
    private final Gson gson;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validator, Gson gson) {
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.gson = gson;
    }

    @Override
    public Boolean townsAreImported() {
        return townRepository.count()>0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return fileUtil.readFile(TOWNS_JSON_FILE_PATH);
    }

    @Override
    public String importTowns(String townsFileContent) throws IOException {
        townsFileContent = readTownsJsonFile();
        TownImportDto[] towns = gson.fromJson(townsFileContent, TownImportDto[].class);
        List<String> result = new ArrayList<>();
        for (TownImportDto townDto : towns) {
            Town town = modelMapper.map(townDto, Town.class);
            if (!validator.isValid(town)){
                result.add("Error: Incorrect data!");
                continue;
            }
            try {
                townRepository.saveAndFlush(town);
                result.add(String.format("Successfully imported Town – %s.", town.getName()));
            }catch (Exception e){
                result.add("Error: Duplicate Data!");
            }
        }
        return String.join("\n", result);
    }

    @Override
    public String exportRacingTowns() {
        List<TownExportDto> townExportDtos = townRepository.findAllByCountOfRacers();
        StringBuilder sb = new StringBuilder();
        for (TownExportDto townExportDto : townExportDtos) {
            sb.append(townExportDto.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
