package app.service;

import app.domain.model.Person;

public interface PersonService {

    Person getById(long id);
    String save(Person person);
}
