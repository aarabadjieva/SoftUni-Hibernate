package bookshopsystem.spring_intro.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileUtil {

    String[] fileContent(String path) throws IOException;
}
