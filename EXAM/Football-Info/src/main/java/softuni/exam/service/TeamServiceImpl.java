package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.xml.TeamDto;
import softuni.exam.domain.dtos.xml.TeamRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final static String TEAMS_XML_FILE_PATH = "D:\\Programming\\6.Hibernate\\EXAM\\Football-Info\\src\\main\\resources\\files\\xml\\teams.xml";

    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validator;
    private final XmlParser xmlParser;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, PictureRepository pictureRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidatorUtil validator, XmlParser xmlParser) {
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.xmlParser = xmlParser;
    }

    @Override
    public String importTeams() throws JAXBException {
        TeamRootDto teamRootDto = xmlParser.parseXml(TeamRootDto.class, TEAMS_XML_FILE_PATH);
        List<String> result = new ArrayList<>();
        for (TeamDto teamDto : teamRootDto.getTeamDtoList()) {
            Picture picture = pictureRepository.findByUrl(teamDto.getPicture().getUrl()).orElse(null);
            Team team = modelMapper.map(teamDto, Team.class);
            team.setPicture(picture);
            if (!validator.isValid(team)){
                result.add("Invalid team");
                continue;
            }

            teamRepository.saveAndFlush(team);
            result.add(String.format("Successfully imported - %s", team.getName()));
        }
        return String.join("\n", result);
    }

    @Override
    public boolean areImported() {
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return fileUtil.readFile(TEAMS_XML_FILE_PATH);
    }
}
