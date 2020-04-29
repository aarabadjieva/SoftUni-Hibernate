package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.PictureSeedDto;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static softuni.exam.common.Constants.*;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final CarService carService;
    private final ModelMapper mapper;
    private final ValidationUtil validator;
    private final Gson gson;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, CarService carService, ModelMapper mapper, ValidationUtil validator, Gson gson) {
        this.pictureRepository = pictureRepository;
        this.carService = carService;
        this.mapper = mapper;
        this.validator = validator;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count()>0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files.readString(Path.of(PATH_TO_JSON_FILES + "pictures.json"));
    }

    @Override
    public String importPictures() throws IOException {
        PictureSeedDto[] pictureSeedDtos = this.gson.fromJson(readPicturesFromFile(), PictureSeedDto[].class);
        List<String> messages = new ArrayList<>();
        for (PictureSeedDto pictureDto : pictureSeedDtos) {
            if (this.validator.isValid(pictureDto)){
                Car car = this.carService.findCarById(pictureDto.getCar());
                if (car!=null&&
                this.pictureRepository.findByName(pictureDto.getName())==null){
                    Picture picture = this.mapper.map(pictureDto, Picture.class);
                    LocalDateTime dateAndTime = LocalDateTime.parse(pictureDto.getDateAndTime(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    picture.setDateAndTime(dateAndTime);
                    picture.setCar(car);
                    this.pictureRepository.saveAndFlush(picture);
                    messages.add(String.format(SUCCESSFULLY_IMPORTED_PICTURE_MESSAGE,
                            picture.getClass().getSimpleName().toLowerCase(), picture.getName()));
                }
            }else messages.add(String.format(INVALID_DATA_MESSAGE, "picture"));
        }
        return String.join("\n", messages);
    }
}
