package hiberspring.service;

import com.google.gson.Gson;
import hiberspring.domain.dtos.importDtos.json.TownDto;
import hiberspring.domain.entities.Town;
import hiberspring.repository.TownRepository;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TownServiceImpl implements TownService {

    private final static String TOWNS_JSON_FILE_PATH = "D:\\Programming\\6.Hibernate\\11.EXAM PREPARATION\\Hiberspring Inc\\src\\main\\resources\\files\\towns.json";

    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validator;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, FileUtil fileUtil, ValidationUtil validator, ModelMapper modelMapper, Gson gson) {
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.validator = validator;
        this.modelMapper = modelMapper;
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
        TownDto[] townDtos = gson.fromJson(townsFileContent, TownDto[].class);
        List<String> result = new ArrayList<>();
        for (TownDto townDto : townDtos) {
            if (!validator.isValid(townDto)){
                result.add("Error: Invalid data.");
                continue;
            }
            Town town = modelMapper.map(townDto, Town.class);
            townRepository.saveAndFlush(town);
            result.add(String.format("Successfully imported Town %s.", town.getName()));
        }
        return String.join("\n", result);
    }
}
