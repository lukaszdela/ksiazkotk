package lukks.eu.ksiazkotk.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class ImageFileService {

    public String save(MultipartFile multipartFile){

        File file = new File("C:\\java_workplace\\proj_koncowy\\ksiazkotk\\src\\main\\resources\\static\\covers", multipartFile.getOriginalFilename());

        try {
            FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("There was exception",e);
        }
        return file.getPath();
    }
}
