package softuni.workshop.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.importDto.ProjectDto;
import softuni.workshop.domain.dtos.importDto.ProjectsRoot;
import softuni.workshop.domain.dtos.jsonDtos.exportDto.ProjectExportDto;
import softuni.workshop.domain.entities.Company;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.CompanyRepository;
import softuni.workshop.repository.ProjectRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final static String PROJECT_IMPORT_XML = "D:\\Programming\\6.Hibernate\\10.MVC PROJECT\\Next Level Technologies 2\\src\\main\\resources\\files\\xmls\\projects.xml";
    private final static String PROJECT_JSON_FILE = "D:\\Programming\\6.Hibernate\\10.MVC PROJECT\\Next Level Technologies 2\\src\\main\\resources\\files\\jsons\\projects.json";

    private final ProjectRepository projectRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final CompanyRepository companyRepository;
    private final XmlParser xmlParser;
    private final Gson gson;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper, FileUtil fileUtil, CompanyRepository companyRepository, XmlParser xmlParser, Gson gson) {
        this.projectRepository = projectRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.companyRepository = companyRepository;

        this.xmlParser = xmlParser;
        this.gson = gson;
    }

    @Override
    public void importProjects() throws JAXBException, IOException {
        ProjectsRoot projectsRoot = (ProjectsRoot) this.xmlParser.importXMl(ProjectsRoot.class, readProjectsXmlFile());
        for (ProjectDto projectDto : projectsRoot.getProjectDtoList()) {
            Company company = this.companyRepository.findByName(projectDto.getCompany().getName());
            Project project = this.modelMapper.map(projectDto, Project.class);
            project.setCompany(company);
            if (!this.validatorUtil.isValid(project)){
                System.out.println(this.validatorUtil.violations(project));
            }else {
                this.projectRepository.saveAndFlush(project);
            }
        }
    }


    @Override
    public boolean areImported() {
        return this.projectRepository.count() > 0;
    }

    @Override
    public String readProjectsXmlFile() throws IOException {
        return this.fileUtil.readFile(PROJECT_IMPORT_XML);
    }

    @Override
    public String exportFinishedProjects() {
        List<ProjectExportDto> finishedProjects = this.projectRepository.findAllByIsFinishedIsTrue()
                .stream()
                .map(p->this.modelMapper.map(p, ProjectExportDto.class))
                .collect(Collectors.toList());
        return this.gson.toJson(finishedProjects);
    }


    @Override
    public void exportProjectToJson() throws IOException {
        List<ProjectExportDto> projectDtos = this.projectRepository.findAll()
                .stream()
                .map(p->this.modelMapper.map(p, ProjectExportDto.class))
                .collect(Collectors.toList());
        String content = this.gson.toJson(projectDtos);
        FileWriter writer = new FileWriter((new File(PROJECT_JSON_FILE)));
        writer.write(content);
        writer.close();
    }

    @Override
    public String readProjectJsonFile() throws IOException {
        return this.fileUtil.readFile(PROJECT_JSON_FILE);
    }

    @Override
    public boolean areExported() throws IOException {
        return this.readProjectJsonFile().length() > 0;
    }

    @Override
    public String exportProjectsWithNameEnding() throws IOException {
        List<ProjectExportDto> projectsEndingWith = this.projectRepository.findProjectsByNameIsEndingWith("e")
                .stream()
                .map(p->this.modelMapper.map(p, ProjectExportDto.class))
                .collect(Collectors.toList());
        return this.gson.toJson(projectsEndingWith);
    }
}
