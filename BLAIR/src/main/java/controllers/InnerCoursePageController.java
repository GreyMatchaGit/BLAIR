package controllers;

import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import lms.Course;
import lms.User;
import lms.content.Activity;
import lms.usertype.Teacher;
import services.*;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.File;
import java.util.*;

public class InnerCoursePageController {
    @FXML
    private Label courseCode, courseDesc, courseTeacher;
    @FXML
    private Button postsBtn, addActivity, addActivityBtn, activityCancelBtn, removeActivityBtn, doneBtn, addBtn, cancelBtn, activitiesBtn, filesBtn, returnBtn, uploadFileBtn, deleteFileBtn, addPostBtn, removePostBtn;
    @FXML
    private AnchorPane contentArea, removeActivityPane, addActivityPane, removePostPane, addPostPane, fileOptionsPane, discussionOptionsPane, activityOptionsPane;
    @FXML
    private VBox postsVBox, removePostVBox, removeActivityVBox;
    @FXML
    private HBox innerCourseContentBox;
    @FXML
    private GridPane filesGrid;
    @FXML
    private TextArea postDescription, activityDetails;
    @FXML
    private TextField activityTitle;

    private ScrollPane scrollPane;
    private Course course;
    private User currentUser;
    private ArrayList<String> posts;
    private ArrayList<Activity> activities; // List of the activity titles
    private ArrayList<File> files;
    private ArrayList<Button> navButtons;


    public void setCourse(Course course) {
        this.course = course;
    }
    public void setCurrentUser(User currentUser) {this.currentUser = currentUser;}


    // TODO: Replace later with the actual grabbing of the user's files from the database
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

    // TODO: Replace later with the actual grabbing of the user's posts from the database
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
        courseTeacher.setText(course.getTeacher());
        navButtons = new ArrayList<>(Arrays.asList(postsBtn, activitiesBtn, filesBtn));

        // TODO: Replace with the actual initializers later
        posts = new ArrayList<>();
        files = new ArrayList<>();
        activities = new ArrayList<>();

        returnBtn.setOnAction(event -> PageNavigationService.navigateToPage(returnBtn, "course"));

        /* Posts buttons and other functionalities */
        postsBtn.setOnAction(event -> {
            navButtonSelection(postsBtn);
            displayPosts();
        });

        addPostBtn.setOnAction(event -> displayAddPostBox());

        addBtn.setOnAction(event -> {
            String postDesc = postDescription.getText();
            if (!postDesc.isEmpty()) {
                // TODO: Replace with actual adding of post to the database later
                posts.add(postDescription.getText());
                hideAddPostBox();
                displayPosts();
            } else {
                postDescription.setText("Post description is empty.");
                postDescription.getStyleClass().add("error-text");
            }
        });

        removePostBtn.setOnAction(event -> {
            displayRemovePostBox();
            displayPosts();
        });

        postDescription.setOnMouseClicked(event -> {
            postDescription.setText("");
            postDescription.getStyleClass().remove("error-text");
        });
        cancelBtn.setOnAction(event -> hideAddPostBox());

        doneBtn.setOnAction(event -> hideRemovePostBox());


        /* Files buttons and other functionalities */
        filesBtn.setOnAction(event -> {
            navButtonSelection(filesBtn);
            displayFiles();
        });

        uploadFileBtn.setOnAction(event -> {
            displayUploadPrompt();
            displayFiles();
        });
        deleteFileBtn.setOnAction(event -> {
            displayRemovePrompt();
            displayFiles();
        });


        /* Activities buttons and other functionalities */
        activitiesBtn.setOnAction(event -> {
            navButtonSelection(activitiesBtn);
            displayActivities();
        });

        addActivityBtn.setOnAction(event -> {
            displayAddActivityBox();
            displayActivities();
        });
        removeActivityBtn.setOnAction(event -> {
            displayRemoveActivityBox();
            displayActivities();
        });

        activityCancelBtn.setOnAction(event -> hideAddActivityBox());
        addActivity.setOnAction(event -> {
            // TODO: Settle how activities are being handled
            hideAddActivityBox();
        });

        activityDetails.setOnMouseClicked(event -> {
            activityDetails.setText("");
            activityDetails.getStyleClass().remove("error-text");
        });

        postsBtn.getStyleClass().add("selected");
        displayPosts();
    }

    private void displayAddPostBox() {
        contentArea.setEffect(new BoxBlur());
        addPostPane.setVisible(true);
    }

    private void hideAddPostBox() {
        contentArea.setEffect(null);
        addPostPane.setVisible(false);
        postDescription.setText("");
    }

    private void displayRemovePostBox() {
        contentArea.setEffect(new BoxBlur());
        removePostPane.setVisible(true);
        removePostVBox.getChildren().clear();

        int index = 0;
        for (String postContent : posts) {
            VBox postCard = new VBox();
            postCard.setMinHeight(50);
            postCard.setPrefWidth(450);
            postCard.setMaxHeight(postCard.getPrefHeight());
            postCard.getStyleClass().add("remove-post-card");
            postCard.setCursor(Cursor.HAND);
            int tempIndex = index;
            postCard.setOnMouseClicked(event -> removePost(tempIndex));

            Label postContentLabel = new Label(postContent);
            postContentLabel.getStyleClass().add("remove-post-content");
            postContentLabel.setMaxWidth(400);
            postContentLabel.setWrapText(true);

            postCard.getChildren().add(postContentLabel);
            VBox.setMargin(postCard, new Insets(10));

            index++;
            removePostVBox.getChildren().add(postCard);
        }

    }

    private void hideRemovePostBox() {
        contentArea.setEffect(null);
        removePostPane.setVisible(false);
    }

    private void displayAddActivityBox() {
        contentArea.setEffect(new BoxBlur());
        addActivityPane.setVisible(true);
    }

    private void hideAddActivityBox() {
        contentArea.setEffect(null);
        addActivityPane.setVisible(false);
        activityDetails.setText("");
    }

    private void displayRemoveActivityBox() {
        contentArea.setEffect(new BoxBlur());
        removeActivityPane.setVisible(true);
        removeActivityVBox.getChildren().clear();

        int index = 0;
        // TODO: Settle how activities are being handled
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

//        for (String title: activities) {
//            VBox activityCard = new VBox();
//            activityCard.setMinHeight(100);
//            activityCard.setPrefWidth(700);
//            activityCard.getStyleClass().add("post-card");
//
//            Label titleLbl = new Label(title);
//            titleLbl.getStyleClass().add("post-author");
//            titleLbl.setWrapText(true);
//
//            activityCard.getChildren().addAll(titleLbl);
//            VBox.setMargin(activityCard, new Insets(10));
//
//            activitiesContainer.getChildren().add(activityCard);
//        }

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

    private void removePost(int index) {
        if (index >= 0 && index < posts.size()) {
            // TODO: Replace with actual removing from the post database
            posts.remove(index);
            hideRemovePostBox();
            displayPosts();
        }
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File to Delete");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));

        File initialDirectory = new File(StringService.convertFrom(
                Objects.requireNonNull(DatabaseService.class.getResource("/course-files/"))
        ));
        if (initialDirectory.exists() && initialDirectory.isDirectory()) {
            fileChooser.setInitialDirectory(initialDirectory);
        }

        File selectedFile = fileChooser.showOpenDialog(contentArea.getScene().getWindow());
        if (selectedFile != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to delete this file?");
            alert.setContentText("File: " + selectedFile.getAbsolutePath());

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    FileDeleteService fileDeleteService = new FileDeleteService();
                    boolean deleted = fileDeleteService.handleFileDelete(selectedFile.getAbsolutePath());

                    if (!deleted) {
                        System.out.println("File deletion unsuccessful");
                    }
                }
            });
        }
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