package services;

import java.io.File;

public class FileDeleteService {
    public boolean handleFileDelete(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.delete();
        } else {
            return false;
        }
    }
}