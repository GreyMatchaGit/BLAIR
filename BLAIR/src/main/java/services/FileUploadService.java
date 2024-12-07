package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class FileUploadService {
    private static final String UPLOAD_DIRECTORY = StringService.convertFrom(
            Objects.requireNonNull(DatabaseService.class.getResource("/course-files/"))
    );

    public void handleFileUpload(File file) {
        Path destinationPath = Path.of(UPLOAD_DIRECTORY, file.getName());

        try {
            Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}