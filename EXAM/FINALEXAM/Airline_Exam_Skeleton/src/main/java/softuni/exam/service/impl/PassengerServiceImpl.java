package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.common.Constants;
import softuni.exam.models.dtos.json.PassengerSeedDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.common.Constants.INVALID_DATA_MESSAGE;
import static softuni.exam.common.Constants.SUCCESSFULLY_IMPORTED_ENTITY_MESSAGE;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final TownService townService;
    private final ModelMapper mapper;
    private final ValidationUtil validator;
    private final Gson gson;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository, TownService townService, ModelMapper mapper, ValidationUtil validator, Gson gson) {
        this.passengerRepository = passengerRepository;
        this.townService = townService;
        this.mapper = mapper;
        this.validator = validator;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of(Constants.PATH_TO_JSON_FILES + "passengers.json"));
    }

    @Override
    public String importPassengers() throws IOException {
        PassengerSeedDto[] passengerSeedDtos = this.gson.fromJson(readPassengersFileContent(), PassengerSeedDto[].class);
        List<String> messages = new ArrayList<>();
        Arrays.stream(passengerSeedDtos).forEach(p -> {
            if (this.validator.isValid(p)) {
                Town town = this.townService.findTownByName(p.getTownName());
                if (findPassengerByEmail(p.getEmail()) == null && town != null) {
                    Passenger passenger = this.mapper.map(p, Passenger.class);
                    passenger.setTown(town);
                    this.passengerRepository.saveAndFlush(passenger);
                    messages.add(String.format(SUCCESSFULLY_IMPORTED_ENTITY_MESSAGE,
                            passenger.getClass().getSimpleName(), passenger.getLastName(), passenger.getEmail()));
                }
            } else {
                messages.add(String.format(INVALID_DATA_MESSAGE, "Passenger"));
            }
        });
        return String.join("\n", messages);
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        List<Passenger> passengersByTicketCount = this.passengerRepository.findAllByTicketCountOrderedByTicketCountDescAndEmailAsc();
        List<String> result = new ArrayList<>();
        passengersByTicketCount.stream().forEach(p -> {
            result.add(String.format("Passenger %s %s\n" +
                    "\tEmail - %s\n" +
                    "Phone - %s\n" +
                    "\tNumber of tickets - %d\n",
                    p.getFirstName(), p.getLastName(),
                    p.getEmail(), p.getPhoneNumber(),
                    p.getTickets().size()));
        });
        return String.join("\n", result);
    }

    @Override
    public Passenger findPassengerByEmail(String email) {
        return this.passengerRepository.findByEmail(email);
    }
}
