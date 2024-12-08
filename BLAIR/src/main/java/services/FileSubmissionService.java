package services;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class FileSubmissionService {

    public File handleUserSubmission(Window owner) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File to Submit");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("Word Documents", "*.docx"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File file = fileChooser.showOpenDialog(owner);

        if (file != null) {
            return file;
        } else {
            return null;
        }
    }
}