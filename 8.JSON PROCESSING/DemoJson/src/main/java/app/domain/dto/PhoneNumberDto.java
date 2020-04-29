package app.domain.dto;

import com.google.gson.annotations.Expose;

public class PhoneNumberDto {

    @Expose
    private String number;
    @Expose
    private PersonDto personDto;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PersonDto getPersonDto() {
        return personDto;
    }

    public void setPersonDto(PersonDto personDto) {
        this.personDto = personDto;
    }
}
