package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.json.TownSeedDto;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.common.Constants.*;

@Service
public class TownServiceImpl implements TownService {

    private  final TownRepository townRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validator;
    private final Gson gson;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ModelMapper mapper, ValidationUtil validator, Gson gson) {
        this.townRepository = townRepository;
        this.mapper = mapper;
        this.validator = validator;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count()>0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(PATH_TO_JSON_FILES + "towns.json"));
    }

    @Override
    public String importTowns() throws IOException {
        TownSeedDto[] townSeedDtos = this.gson.fromJson(readTownsFileContent(), TownSeedDto[].class);
        List<String> messages = new ArrayList<>();
        Arrays.stream(townSeedDtos).forEach(t->{
            if (this.validator.isValid(t)){
                if (findTownByName(t.getName())==null){
                    Town town = this.mapper.map(t, Town.class);
                    this.townRepository.saveAndFlush(town);
                    messages.add(String.format(SUCCESSFULLY_IMPORTED_ENTITY_MESSAGE,
                            town.getClass().getSimpleName(), town.getName(), town.getPopulation()));
                }
            }else {
                messages.add(String.format(INVALID_DATA_MESSAGE, "Town"));
            }
        });
        return String.join("\n", messages);
    }

    @Override
    public Town findTownByName(String townName) {
        return this.townRepository.findByName(townName);
    }
}
