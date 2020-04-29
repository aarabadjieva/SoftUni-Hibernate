package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.Position;
import softuni.exam.domain.dtos.json.PlayerDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final static String PLAYERS_JSON_FILE_PATH = "D:\\Programming\\6.Hibernate\\EXAM\\Football-Info\\src\\main\\resources\\files\\json\\players.json";

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validator;
    private final Gson gson;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository, PictureRepository pictureRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidatorUtil validator, Gson gson) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.gson = gson;
    }

    @Override
    public String importPlayers() throws IOException {
        PlayerDto[] playerDtos = gson.fromJson(readPlayersJsonFile(), PlayerDto[].class);
        List<String> result = new ArrayList<>();
        Position position;
        for (PlayerDto playerDto : playerDtos) {
            try {
                position = Position.valueOf(playerDto.getPositionName());
            } catch (Exception e) {
                result.add("Invalid Player");
                continue;
            }
            Team team = teamRepository.findByName(playerDto.getTeamDto().getName()).orElse(null);
            Picture picture = pictureRepository.findByUrl(playerDto.getPictureDto().getUrl()).orElse(null);
            Player player = modelMapper.map(playerDto, Player.class);
            player.setPosition(position);
            player.setPicture(picture);
            player.setTeam(team);

            if (!validator.isValid(player)) {
                result.add("Invalid Player");
                continue;
            }

            playerRepository.saveAndFlush(player);
            result.add(String.format("Successfully imported player: %s %s", player.getFirstName(), player.getLastName()));
        }
        return String.join("\n", result);
    }

    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return fileUtil.readFile(PLAYERS_JSON_FILE_PATH);
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        BigDecimal salary = BigDecimal.valueOf(100000);
        List<Player> playersWithSalaryOver = playerRepository.findBySalaryGreaterThanOrderBySalaryDesc(salary);
        StringBuilder sb = new StringBuilder();
        for (Player player : playersWithSalaryOver) {
            sb.append(String.format("Player name: %s %s \n" +
                    "Number: %d\n" +
                    "Salary: %.2f\n" +
                    "Team: %s\n", player.getFirstName(), player.getLastName(),
                    player.getNumber(), player.getSalary(), player.getTeam().getName()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    @Override
    public String exportPlayersInATeam() {
        String teamName = "North Hub";
        List<Player> playersInTeam = playerRepository.findByTeam_NameOrderById(teamName);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Team: %s\n", teamName));
        for (Player player : playersInTeam) {
            sb.append(String.format("   Player name: %s %s - %s\n" +
                            "   Number: %d\n", player.getFirstName(), player.getLastName(),
                    player.getPosition(), player.getNumber()))
            .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
