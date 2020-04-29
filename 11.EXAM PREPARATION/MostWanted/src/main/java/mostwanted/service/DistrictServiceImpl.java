package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.DistrictImportDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Town;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService{

    private final static String DISTRICT_JSON_FILE_PATH = System.getProperty("user.dir")+"/src/main/resources/files/districts.json";

    private final DistrictRepository districtRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validator;
    private final Gson gson;

    public DistrictServiceImpl(DistrictRepository districtRepository, TownRepository townRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validator, Gson gson) {
        this.districtRepository = districtRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.gson = gson;
    }

    @Override
    public Boolean districtsAreImported() {
        return districtRepository.count()>0;
    }

    @Override
    public String readDistrictsJsonFile() throws IOException {
        return fileUtil.readFile(DISTRICT_JSON_FILE_PATH);
    }

    @Override
    public String importDistricts(String districtsFileContent) throws IOException {
        districtsFileContent = readDistrictsJsonFile();
        DistrictImportDto[] districts = gson.fromJson(districtsFileContent, DistrictImportDto[].class);
        List<String> result = new ArrayList<>();
        for (DistrictImportDto districtDto : districts) {
            Town town = townRepository.findByName(districtDto.getTownName()).orElse(null);
            District district = modelMapper.map(districtDto, District.class);
            if (town==null||!validator.isValid(district)){
                result.add("Error: Incorrect Data!");
                continue;
            }
            district.setTown(town);
            try {
                districtRepository.saveAndFlush(district);
                result.add(String.format("Successfully imported District – %s.", district.getName()));
            }catch (Exception e){
                result.add("Error: Duplicate Data!");
            }
        }
        return String.join("\n", result);
    }
}
