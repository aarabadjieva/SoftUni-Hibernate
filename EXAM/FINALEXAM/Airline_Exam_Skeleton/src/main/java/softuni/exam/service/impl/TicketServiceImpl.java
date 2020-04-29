package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.xml.TicketRootSeedDto;
import softuni.exam.models.dtos.xml.TicketSeedDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Plane;
import softuni.exam.models.entities.Ticket;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.TicketRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.service.PlaneService;
import softuni.exam.service.TicketService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static softuni.exam.common.Constants.*;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final PassengerService passengerService;
    private final PlaneService planeService;
    private final TownService townService;
    private final ModelMapper mapper;
    private final ValidationUtil validator;
    private final XmlParser xmlParser;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, PassengerService passengerService, PlaneService planeService, TownService townService, ModelMapper mapper, ValidationUtil validator, XmlParser xmlParser) {
        this.ticketRepository = ticketRepository;
        this.passengerService = passengerService;
        this.planeService = planeService;
        this.townService = townService;
        this.mapper = mapper;
        this.validator = validator;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.ticketRepository.count()>0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of(PATH_TO_XML_FILES + "tickets.xml"));
    }

    @Override
    public String importTickets() throws JAXBException {
        TicketRootSeedDto ticketRootSeedDto = this.xmlParser.parseXml(TicketRootSeedDto.class, PATH_TO_XML_FILES + "tickets.xml");
        List<String> messages = new ArrayList<>();
        for (TicketSeedDto t : ticketRootSeedDto.getTickets()) {
            if (this.validator.isValid(t)){
                Plane plane = this.planeService.findPlaneByRegisterNumber(t.getPlane().getRegisterNumber());
                Town fromTown = this.townService.findTownByName(t.getFromTown().getName());
                Town toTown = this.townService.findTownByName(t.getToTown().getName());
                Passenger passenger = this.passengerService.findPassengerByEmail(t.getPassenger().getEmail());
                if (plane!=null&&fromTown!=null&&toTown!=null
                &&findTicketBySerialNumber(t.getSerialNumber())==null){
                    Ticket ticket = this.mapper.map(t, Ticket.class);
                    ticket.setPlane(plane);
                    ticket.setFromTown(fromTown);
                    ticket.setToTown(toTown);
                    ticket.setPassenger(passenger);
                    this.ticketRepository.saveAndFlush(ticket);
                    messages.add(String.format(SUCCESSFULLY_IMPORTED_ENTITY_MESSAGE,
                            ticket.getClass().getSimpleName(),
                            ticket.getFromTown().getName(), ticket.getToTown().getName()));
                }
            }else {
                messages.add(String.format(INVALID_DATA_MESSAGE, "Ticket"));
            }
        }
        return String.join("\n", messages);
    }

    @Override
    public Ticket findTicketBySerialNumber(String serialNumber) {
        return this.ticketRepository.findBySerialNumber(serialNumber);
    }


}
