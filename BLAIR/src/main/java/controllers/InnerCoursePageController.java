package controllers;

import javafx.stage.FileChooser;
import lms.Course;
import lms.User;
import lms.usertype.Teacher;
import services.ColorSelectorService;
import services.FileDownloadService;
import services.FileUploadService;
import services.PageNavigationService;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class InnerCoursePageController {
    @FXML
    private Label courseCode, courseDesc, courseTeacher;
    @FXML
    private Button  postsBtn, activitiesBtn, filesBtn, returnBtn, uploadFileBtn, deleteFileBtn, addPostBtn, removePostBtn;
    @FXML
    private AnchorPane contentArea, fileOptionsPane, discussionOptionsPane, activityOptionsPane;
    @FXML
    private VBox postsVBox;
    @FXML
    private GridPane filesGrid;

    private ScrollPane scrollPane;
    private Course course;
    private User currentUser;
    private ArrayList<String> posts;
    private ArrayList<String> activities;
    private ArrayList<File> files;
    ArrayList<Button> navButtons;


    public void setCourse(Course course) {
        this.course = course;
    }
    public void setCurrentUser(User currentUser) {this.currentUser = currentUser;}
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

    // Temporarily initialize the activities
    private void tempActivitiesInitializer() {
        activities = new ArrayList<>();
        activities.add("Weekly Team Meeting");
        activities.add("Project Kickoff");
        activities.add("Midterm Exam Review Session");
        activities.add("Guest Lecture: Industry Insights");
        activities.add("Group Study Session");
        activities.add("Final Project Presentation");
        activities.add("Networking Event");
        activities.add("Workshop: Effective Communication");
        activities.add("Career Fair");
        activities.add("End-of-Semester Celebration");
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
        navButtons = new ArrayList<>(Arrays.asList(postsBtn, activitiesBtn, filesBtn));

        // ToDo: Remove later
        // Temporarily set all teachers to sir serats
        courseTeacher.setText("Mr. Jay Vince D. Serato");
        contentArea.setStyle("-fx-background-color: transparent;");

        tempPostsInitializer();
        tempActivitiesInitializer();
        tempFilesInitializer();

        returnBtn.setOnAction(event -> PageNavigationService.navigateToPage(returnBtn, "course"));

        postsBtn.setOnAction(event -> {
            navButtonSelection(postsBtn);
            displayPosts();
        });

        filesBtn.setOnAction(event -> {
            navButtonSelection(filesBtn);
            displayFiles();
        });

        activitiesBtn.setOnAction(event -> {
            navButtonSelection(activitiesBtn);
            displayActivities();
        });

        uploadFileBtn.setOnAction(event -> displayUploadPrompt());
        deleteFileBtn.setOnAction(event -> displayRemovePrompt());
        addPostBtn.setOnAction(event -> System.out.println("Event here"));
        removePostBtn.setOnAction(event -> System.out.println("Event here"));

        postsBtn.getStyleClass().add("selected");
        displayPosts();
    }

    @FXML
    private void displayPosts() {
        contentArea.getChildren().removeIf(node -> (node != fileOptionsPane && node != discussionOptionsPane && node != activityOptionsPane));
        fileOptionsPane.setVisible(false);
        activityOptionsPane.setVisible(false);
        scrollPane = makeScrollPane();

        if (currentUser instanceof Teacher) {
            discussionOptionsPane.setVisible(true);
        }

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
        if (currentUser instanceof Teacher) {
            AnchorPane.setBottomAnchor(scrollPane, 83.0);
        } else {
            AnchorPane.setBottomAnchor(scrollPane, 10.0);
        }
        AnchorPane.setLeftAnchor(scrollPane, 80.0);
        AnchorPane.setRightAnchor(scrollPane, 30.0);

        contentArea.getChildren().add(scrollPane);
    }

    @FXML
    private void displayActivities() {
        contentArea.getChildren().removeIf(node -> (node != fileOptionsPane && node != discussionOptionsPane && node != activityOptionsPane));
        fileOptionsPane.setVisible(false);
        discussionOptionsPane.setVisible(false);
        scrollPane = makeScrollPane();
        
        if (currentUser instanceof Teacher) {
            activityOptionsPane.setVisible(true);
        }

        VBox activitiesContainer = new VBox();
        activitiesContainer.setPrefWidth(812.0);
        activitiesContainer.setStyle("-fx-background-color: transparent;");

        for (String title: activities) {
            VBox activityCard = new VBox();
            activityCard.setMinHeight(100);
            activityCard.setPrefWidth(700);
            activityCard.getStyleClass().add("post-card");

            Label titleLbl = new Label(title);
            titleLbl.getStyleClass().add("post-author");
            titleLbl.setWrapText(true);

            activityCard.getChildren().addAll(titleLbl);
            VBox.setMargin(activityCard, new Insets(10));

            activitiesContainer.getChildren().add(activityCard);
        }

        scrollPane.setContent(activitiesContainer);

        AnchorPane.setTopAnchor(scrollPane, 0.0);
        if (currentUser instanceof Teacher) {
            AnchorPane.setBottomAnchor(scrollPane, 83.0);
        } else {
            AnchorPane.setBottomAnchor(scrollPane, 10.0);
        }
        AnchorPane.setLeftAnchor(scrollPane, 80.0);
        AnchorPane.setRightAnchor(scrollPane, 30.0);

        contentArea.getChildren().add(scrollPane);
    }

    @FXML
    private void displayFiles() {
        contentArea.getChildren().removeIf(node -> (node != fileOptionsPane && node != discussionOptionsPane && node != activityOptionsPane));
        fileOptionsPane.setVisible(true);
        activityOptionsPane.setVisible(false);
        discussionOptionsPane.setVisible(false);
        scrollPane = makeScrollPane();

        GridPane filesGrid = new GridPane();
        filesGrid.setPadding(new Insets(40, 40, 40, 60));
        filesGrid.setHgap(15);
        filesGrid.setVgap(15);
        filesGrid.setMaxWidth(980);
        filesGrid.setStyle("-fx-background-color: transparent;");

        int column = 0;
        int row = 0;
        for (File f : files) {
            String fileName = getFileName(f);
            VBox fileCard = new VBox();
            fileCard.setPrefWidth(205.5);
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

        scrollPane.setContent(filesGrid);

        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 83.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 30.0);

        contentArea.getChildren().add(scrollPane);
    }


    private ScrollPane makeScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefWidth(860.0);
        scrollPane.setPrefHeight(570.0);
        scrollPane.setPadding(new Insets(10));
        scrollPane.setStyle("-fx-background-color: transparent;");
        return scrollPane;
    }

    private void navButtonSelection(Button selectedButton) {
        navButtons.forEach(button -> button.getStyleClass().remove("selected"));

        selectedButton.getStyleClass().add("selected");
    }

    @FXML
    private void displayUploadPrompt() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File to Upload");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(contentArea.getScene().getWindow());
        if (selectedFile != null) {
            FileUploadService fileUploadService = new FileUploadService();
            fileUploadService.handleFileUpload(selectedFile);
            displayFiles();
        }
    }

    @FXML
    private void displayRemovePrompt() {
        contentArea.getChildren().removeIf(node -> node != fileOptionsPane);
        fileOptionsPane.setVisible(false);


    }

    private String getFileName(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            fileName = fileName.substring(0, dotIndex);
        }
        return fileName;
    }
}