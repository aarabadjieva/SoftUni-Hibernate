package softuni.workshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.ProjectDto;
import softuni.workshop.domain.dtos.ProjectsRoot;
import softuni.workshop.domain.entities.Company;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.CompanyRepository;
import softuni.workshop.repository.ProjectRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;


@Service
public class ProjectServiceImpl implements ProjectService {

    private final static String PROJECT_IMPORT_XML = "D:\\Programming\\6.Hibernate\\10.MVC PROJECT\\NextLevelTechnologies\\src\\main\\resources\\files\\xmls\\projects.xml";

    private final ProjectRepository projectRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final CompanyRepository companyRepository;
    private final XmlParser xmlParser;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper, FileUtil fileUtil, CompanyRepository companyRepository, XmlParser xmlParser) {
        this.projectRepository = projectRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.companyRepository = companyRepository;
        this.xmlParser = xmlParser;
    }

    @Override
    public void importProjects() throws IOException, JAXBException {
        ProjectsRoot projectsRoot = (ProjectsRoot) this.xmlParser.importParse(ProjectsRoot.class, readProjectsXmlFile());
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
       return this.projectRepository.count()!=0;
    }

    @Override
    public String readProjectsXmlFile() throws IOException {
        return this.fileUtil.fileContent(PROJECT_IMPORT_XML);
    }

    @Override
    public String exportFinishedProjects(){
        List<Project> finishedProjects = this.projectRepository.findAllByIsFinishedIsTrue();
        StringBuilder sb = new StringBuilder();
        for (Project p : finishedProjects) {
            sb.append(String.format("Project Name: %s\n\tDescription: %s\n\t%.2f",
                    p.getName(), p.getDescription(), p.getPayment()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
