package softuni.exam.service;

import softuni.exam.models.entity.Car;

import java.io.IOException;

public interface CarService {

    Car findCarById(int id);

    boolean areImported();

    String readCarsFileContent() throws IOException;
	
	String importCars() throws IOException;

    String getCarsOrderByPicturesCountThenByMake();
}
