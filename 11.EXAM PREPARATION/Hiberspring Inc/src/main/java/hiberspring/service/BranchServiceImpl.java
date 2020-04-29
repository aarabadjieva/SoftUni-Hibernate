package hiberspring.service;

import com.google.gson.Gson;
import hiberspring.domain.dtos.importDtos.json.BranchDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Town;
import hiberspring.repository.BranchRepository;
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
public class BranchServiceImpl implements BranchService {

    private final static String BRANCHES_JSON_FILE_PATH = "D:\\Programming\\6.Hibernate\\11.EXAM PREPARATION\\Hiberspring Inc\\src\\main\\resources\\files\\branches.json";

    private final BranchRepository branchRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validator;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, TownRepository townRepository, FileUtil fileUtil, ValidationUtil validator, ModelMapper modelMapper, Gson gson) {
        this.branchRepository = branchRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public Boolean branchesAreImported() {
        return branchRepository.count()>0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return fileUtil.readFile(BRANCHES_JSON_FILE_PATH);
    }

    @Override
    public String importBranches(String branchesFileContent) throws IOException {
        branchesFileContent = readBranchesJsonFile();
        BranchDto[] branchDtos = gson.fromJson(branchesFileContent, BranchDto[].class);
        List<String> result = new ArrayList<>();
        for (BranchDto branchDto : branchDtos) {
            Town town = townRepository.findByName(branchDto.getTown()).orElse(null);
            if (town==null||!validator.isValid(branchDto)){
                result.add("Error: Invalid data.");
                continue;
            }
            Branch branch = modelMapper.map(branchDto, Branch.class);
            branch.setTown(town);
            branchRepository.saveAndFlush(branch);
            result.add(String.format("Successfully imported Branch %s.", branch.getName()));
        }
        return String.join("\n", result);
    }
}
