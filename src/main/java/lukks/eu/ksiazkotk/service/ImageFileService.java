package lukks.eu.ksiazkotk.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class ImageFileService {

    private static final String PARENT_DIRECTORY = "coversnew";

    public String save(MultipartFile multipartFile){

        File file = new File(PARENT_DIRECTORY, multipartFile.getOriginalFilename());

        try {
            FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("There was exception",e);
        }
        return file.getPath();
    }
}
