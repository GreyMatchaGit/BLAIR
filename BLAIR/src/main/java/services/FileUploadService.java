package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class FileUploadService {
    private String UPLOAD_DIRECTORY = StringService.convertFrom(
            Objects.requireNonNull(DatabaseService.class.getResource("/course-files/"))
    );

    public void handleFileUpload(File file, String code) {
        String fileName = file.getName().replaceFirst("^[\\\\/]+", "");
        System.out.println("File Name: " + fileName);

        try {
            Files.copy(file.toPath(), Paths.get(UPLOAD_DIRECTORY.substring(1) +  code  + "/" + fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}