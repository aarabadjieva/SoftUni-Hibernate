package app.service.impl;

import app.domain.model.Person;
import app.repository.PersonRepository;
import app.service.PersonService;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person getById(long id) {
        return this.personRepository.getOne(id);
    }

    @Override
    public String save(Person person) {
        this.personRepository.saveAndFlush(person);
        person.getPhoneNumbers().forEach(ph->ph.setPerson(person));
        return String.format("Person %s %s saved successfully", person.getFirstName(), person.getLastName());
    }
}
