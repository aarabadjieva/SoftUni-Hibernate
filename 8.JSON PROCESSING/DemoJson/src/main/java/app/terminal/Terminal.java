package app.terminal;


import app.domain.dto.PersonDto;
import app.domain.model.Person;
import app.service.PersonService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Component
@Transactional
public class Terminal implements CommandLineRunner {

    private static final String personJson = "{\n" +
            "  \"firstName\": \"Stoyan\",\n" +
            "  \"lastName\": \"Stoyanov\",\n" +
            "  \"address\": {\n" +
            "    \"country\": \"Bulgaria\",\n" +
            "    \"city\": \"Varna\"\n" +
            "  },\n" +
            "  \"phoneNumbers\": [\n" +
            "    {\n" +
            "      \"number\": \"8888\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"number\": \"9999\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"number\": \"7777\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    private final PersonService personService;
    private ModelMapper modelMapper;

    @Autowired
    public Terminal(PersonService personService) {
        this.personService = personService;
        this.modelMapper = new ModelMapper();
    }


    @Override
    public void run(String... strings) throws Exception {
        //gsonToJson();
        gsonFromJson();
    }

    private void gsonFromJson() {
        Gson gson = new GsonBuilder().create();
        PersonDto personDto = gson.fromJson(personJson, PersonDto.class);
        Person person = this.modelMapper.map(personDto, Person.class);
        System.out.println(this.personService.save(person));
    }

    private void gsonToJson() {
        Person person = this.personService.getById(2);
        PersonDto personDto = this.modelMapper.map(person, PersonDto.class);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        System.out.println(gson.toJson(personDto));
    }
}
