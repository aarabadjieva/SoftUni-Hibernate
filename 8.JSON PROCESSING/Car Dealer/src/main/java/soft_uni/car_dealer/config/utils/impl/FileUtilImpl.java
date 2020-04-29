package soft_uni.car_dealer.config.utils.impl;

import soft_uni.car_dealer.config.utils.FileUtil;

import java.io.*;

public class FileUtilImpl implements FileUtil {
    @Override
    public String fileContent(String path) throws IOException {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line=reader.readLine())!=null){
            sb.append(line).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
