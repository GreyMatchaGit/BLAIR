package Controllers;

import LMS.Course;
import Services.ColorSelectorService;
import Services.FileDownloadService;
import Services.PageNavigationService;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class InnerCoursePageController {
    @FXML
    private Button postsBtn;
    @FXML
    private Button filesBtn;
    @FXML
    private Button returnBtn;
    @FXML
    private Label courseCode;
    @FXML
    private Label courseDesc;
    @FXML
    private Label courseTeacher;

    private Course course;
    private ArrayList<String> posts;
    private ArrayList<File> files;

    public void setCourse(Course course) {
        this.course = course;
    }

    // Temporarily initialize the files
    private void tempFilesInitializer() {
        files = new ArrayList<>();
        String directoryPath = "course-files/";
        files.add(new File(directoryPath + "LectureNotes.pdf"));
        files.add(new File(directoryPath + "Assignment1.docx"));
        files.add(new File(directoryPath + "ProjectProposal.pptx"));
        files.add(new File(directoryPath + "ReadingMaterial.txt"));
        files.add(new File(directoryPath + "ExamSchedule.pdf"));
        files.add(new File(directoryPath + "LabReport.docx"));
        files.add(new File(directoryPath + "CourseSyllabus.pdf"));
        files.add(new File(directoryPath + "PresentationSlides.pptx"));
        files.add(new File(directoryPath + "ResearchPaper.pdf"));
        files.add(new File(directoryPath + "FinalProject.docx"));
        files.add(new File(directoryPath + "FinalProject.docx"));
        files.add(new File(directoryPath + "FinalProject.docx"));
        files.add(new File(directoryPath + "ExamSchedule.pdf"));
        files.add(new File(directoryPath + "LabReport.docx"));
        files.add(new File(directoryPath + "CourseSyllabus.pdf"));
        files.add(new File(directoryPath + "PresentationSlides.pptx"));
        files.add(new File(directoryPath + "ResearchPaper.pdf"));
        files.add(new File(directoryPath + "FinalProject.docx"));
    }

    // Temporarily initialize the posts
    private void tempPostsInitializer() {
        posts = new ArrayList<>();
        posts.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eu massa ut metus gravida mollis ut et tortor.");
        posts.add("Morbi neque odio, Morbi neque odio, gravida convallis sagittis id, scelerisque vitae tellus. Lorem ipsum dolor sit amet, consectetur adipiscing elit.Morbi neque odio, gravida convallis sagittis id, scelerisque vitae tellus. Lorem ipsum dolor sit amet, consectetur adipiscing elit.gravida convallis sagittis id, scelerisque vitae tellus. Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        posts.add("In a realm where the vibes are perpetually vibing and the rizz flows like a never-ending stream of TikTok trends, we find ourselves lost in a whirlwind of chaotic energy. Picture this: a level 900 school gyatt strutting through the hallways, while the grimace rizz of existential dread lurks in every corner, threatening to sigma grind the entire operation to a halt. The sigma male archetype reigns supreme, as the huzz of uncertainty echoes through the chambers of our minds. Meanwhile, the no cap, lowkey, highkey drama unfolds like a badly scripted anime, where the characters are perpetually stuck in a state of \"I'm dead, bro.\" The world is a wild, wild west of brainrot, where the only constant is the flux of fleeting trends and the eternal quest for clout.");
        posts.add("Lorem ipsum dolor sit amet, Morbi neque odio, gravida convallis sagittis id, scelerisque vitae tellus. Lorem ipsum dolor sit amet, consectetur adipiscing elit.Morbi neque odio, gravida convallis sagittis id, scelerisque vitae tellus. Lorem ipsum dolor sit amet, consectetur adipiscing elit.consectetur adipiscing elit. Donec eu massa ut metus gravida mollis ut et tortor.");
        posts.add("Morbi neque odio, gravida convallis sagittis id, scelerisque vitae tellus. Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        posts.add("Donec eu massa ut metus gravida mollis ut et tortor. Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        posts.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eu massa ut metus gravida mollis ut et tortor.");
        posts.add("Morbi neque odio, gravida convallis sagittis id, scelerisque vitae tellus. Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        posts.add("Donec eu massa ut metus gravida mollis ut et tortor. Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
    }

    @FXML
    public void initialize() {
        courseCode.setText(course.getCode());
        courseDesc.setText(course.getDescription());
        courseTeacher.setText("Mr. Jay Vince D. Serato"); // Temporarily set teacher to sir serats
        contentArea.setStyle("-fx-background-color: transparent;");

        tempPostsInitializer();
        tempFilesInitializer();

        returnBtn.setOnAction(event -> PageNavigationService.navigateToPage(returnBtn, "course"));

        postsBtn.setOnAction(event -> {
            if (postsBtn.getStyleClass().contains("selected")) {
                postsBtn.getStyleClass().remove("selected");
            } else {
                postsBtn.getStyleClass().add("selected");
                filesBtn.getStyleClass().remove("selected");
            }
            displayPosts();
        });

        filesBtn.setOnAction(event -> {
            if (filesBtn.getStyleClass().contains("selected")) {
                filesBtn.getStyleClass().remove("selected");
            } else {
                filesBtn.getStyleClass().add("selected");
                postsBtn.getStyleClass().remove("selected");
            }
            displayFiles();
        });

        postsBtn.getStyleClass().add("selected");
        displayPosts();
    }

    @FXML
    private AnchorPane contentArea;

    @FXML
    private VBox postsVBox;
    @FXML
    private void displayPosts() {
        contentArea.getChildren().clear();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefWidth(860.0);
        scrollPane.setPrefHeight(570.0);
        scrollPane.setPadding(new Insets(10));
        scrollPane.setStyle("-fx-background-color: transparent;");

        VBox postsContainer = new VBox();
        postsContainer.setPrefWidth(812.0);
        postsContainer.setStyle("-fx-background-color: transparent;");

        for (String postContent : posts) {
            VBox postCard = new VBox();
            postCard.setMinHeight(100);
            postCard.setPrefWidth(250);
            postCard.getStyleClass().add("post-card");

            Label postAuthor = new Label(courseTeacher.getText());
            postAuthor.getStyleClass().add("post-author");

            Label postContentLabel = new Label(postContent);
            postContentLabel.getStyleClass().add("post-content");
            postContentLabel.setWrapText(true);

            postCard.getChildren().addAll(postAuthor, postContentLabel);
            VBox.setMargin(postCard, new Insets(10));

            postsContainer.getChildren().add(postCard);
        }

        scrollPane.setContent(postsContainer);

        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 30.0);
        AnchorPane.setLeftAnchor(scrollPane, 70.0);
        AnchorPane.setRightAnchor(scrollPane, 30.0);

        contentArea.getChildren().add(scrollPane);
    }

    @FXML
    private GridPane filesGrid;
    @FXML
    private void displayFiles() {
        contentArea.getChildren().clear();

        GridPane filesGrid = new GridPane();
        filesGrid.setPadding(new Insets(40, 40 ,40, 60));
        filesGrid.setHgap(15);
        filesGrid.setVgap(15);
        filesGrid.setMaxWidth(900);
        filesGrid.setStyle("-fx-background-color: transparent;");

        int column = 0;
        int row = 0;

        for (File f : files) {
            String fileName = getFileName(f);
            VBox fileCard = new VBox();
            fileCard.setPrefWidth(200);
            fileCard.setPrefHeight(80);
            fileCard.setAlignment(Pos.BOTTOM_LEFT);

            fileCard.getStyleClass().add("file-card");

            String colorHex = new ColorSelectorService().getRandomColor();
            fileCard.setStyle("-fx-background-color: #" + colorHex + ";");

            Label fileLabel = new Label(fileName);
            fileLabel.getStyleClass().add("file-label");

            fileCard.getChildren().add(fileLabel);
            filesGrid.add(fileCard, column, row);

            column++;
            if (column >= 4) {
                column = 0;
                row++;
            }

            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), fileCard);
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.setCycleCount(1);
            scaleTransition.setAutoReverse(true);

            fileCard.setOnMouseEntered(event -> scaleTransition.playFromStart());
            fileCard.setOnMouseExited(event -> {
                scaleTransition.setRate(-1);
                scaleTransition.play();
            });

            fileCard.setOnMouseClicked(event -> new FileDownloadService().handleFileDownload(event, String.valueOf(f)));
        }

        contentArea.getChildren().add(filesGrid);
    }

    // Extract the file name w/o the file extension
    private String getFileName(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            fileName = fileName.substring(0, dotIndex);
        }
        return fileName;
    }
}