package controllers;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import database.Database;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import lms.calendar.CustomEntry;
import lms.course.Course;
import lms.usertype.User;
import lms.course.Activity;
import lms.course.Discussion;
import lms.usertype.Student;
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

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InnerCoursePageController {
    @FXML
    private Label courseCode, courseDesc, courseTeacher, submissionNameLbl;
    @FXML
    private Button studentSubmissionsReturnBtn, postsBtn, removeDoneBtn, addActivity, addActivityBtn, activityCancelBtn, removeActivityBtn, addSubmissionBtn, submissionCancelBtn;
    @FXML
    private Button doneBtn, addBtn, cancelBtn, activitiesBtn, filesBtn, returnBtn, uploadFileBtn, deleteFileBtn, addPostBtn, removePostBtn;
    @FXML
    private AnchorPane contentArea, removeActivityPane, addActivityPane, removePostPane, addPostPane, studentSubmissionsPane;
    @FXML
    private AnchorPane submissionPane, submissionContainerPane, fileOptionsPane, discussionOptionsPane, activityOptionsPane;
    @FXML
    private VBox postsVBox, removePostVBox, removeActivityVBox, studentSubmissionsVBox;
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
    private ArrayList<Activity> activities;
    private ArrayList<File> files;
    private ArrayList<Button> navButtons;

    private Activity currentActivity;

    public void setCourse(Course course) {
        this.course = course;
    }
    public void setCurrentUser(User currentUser) {this.currentUser = currentUser;}

    @FXML
    public void initialize() {
        courseCode.setText(course.getCode());
        courseDesc.setText(course.getDescription());
        courseTeacher.setText(course.getTeacher());
        navButtons = new ArrayList<>(Arrays.asList(postsBtn, activitiesBtn, filesBtn));

        posts = course.getDiscussions();
        activities = course.getActivities();

        String courseDir = course.getCourseDir();
        if (!(courseDir == null || courseDir.isEmpty())) {
            files = getFiles(courseDir);
        }

        returnBtn.setOnAction(event -> PageNavigationService.navigateToPage(returnBtn, "course"));

        /* Posts buttons and other functionalities */
        postsBtn.setOnAction(event -> {
            navButtonSelection(postsBtn);
            displayPosts();
        });

        addPostBtn.setOnAction(event -> displayAddPostBox());

        addBtn.setOnAction(event -> {
            String postDesc = postDescription.getText();
            if (!postDesc.isEmpty() && posts != null) {
                createNewPost(postDesc);
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
            displayFiles(courseDir);
        });

        uploadFileBtn.setOnAction(event -> {
            displayUploadPrompt();
            DatabaseService.update();
            displayFiles(courseDir);
        });
        deleteFileBtn.setOnAction(event -> {
            displayRemovePrompt();
            displayFiles(courseDir);
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
        removeDoneBtn.setOnAction(event -> hideRemoveActivityBox());
        activityCancelBtn.setOnAction(event -> hideAddActivityBox());
        addActivity.setOnAction(event -> {
            createNewActivity(activityTitle.getText(), activityDetails.getText());
            hideAddActivityBox();
        });
        submissionContainerPane.setVisible(false);

        FileSubmissionService fileSubmissionService = new FileSubmissionService();

        addSubmissionBtn.setOnAction(event -> {
            String filename = String.valueOf(fileSubmissionService.handleUserSubmission(addSubmissionBtn.getScene().getWindow()));
            if (filename != null) {
                submissionPane.setVisible(true);
                submissionNameLbl.setText(filename);

                File file = new File(filename);
                currentActivity.attachSubmission((Student) currentUser, file);

            } else {
                submissionNameLbl.setText("No file selected.");
            }
        });

        studentSubmissionsPane.setVisible(false);
        studentSubmissionsReturnBtn.setOnAction(event -> {
            contentArea.setEffect(null);
            studentSubmissionsPane.setVisible(false);
        });

        submissionCancelBtn.setOnAction(event -> {
            contentArea.setEffect(null);
            submissionContainerPane.setVisible(false);
            submissionPane.setVisible(false);
            submissionNameLbl.setText("");
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
        activityTitle.setText("");
        activityDetails.setText("");
    }

    private void displayRemoveActivityBox() {
        contentArea.setEffect(new BoxBlur());
        removeActivityPane.setVisible(true);
        removeActivityVBox.getChildren().clear();

        int index = 0;
        for (Activity activity: activities) {
            String title = activity.getTitle();
            VBox activityCard = new VBox();
            activityCard.setMinHeight(50);
            activityCard.setPrefHeight(50);
            activityCard.setPrefWidth(400);
            activityCard.setMaxHeight(activityCard.getPrefHeight());
            activityCard.getStyleClass().add("post-card");
            activityCard.setCursor(Cursor.HAND);
            int finalIndex = index;
            activityCard.setOnMouseClicked(event -> removeActivity(finalIndex));

            Label titleLbl = new Label(title);
            titleLbl.getStyleClass().add("post-author");
            titleLbl.setWrapText(true);

            activityCard.getChildren().addAll(titleLbl);
            VBox.setMargin(activityCard, new Insets(10));

            index++;
            removeActivityVBox.getChildren().add(activityCard);
        }
    }

    private void hideRemoveActivityBox() {
        contentArea.setEffect(null);
        removeActivityPane.setVisible(false);
    }

    private void createNewActivity(String title, String details) {
        Activity activity = new Activity(title, details);
        course.addActivity(activity);
        displayActivities();
        // add to student calendar
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextWeek = now.plus(1, ChronoUnit.WEEKS);
        Entry<String> entry = new Entry<>(title);
        entry.setInterval(nextWeek);
        entry.setCalendar(new Calendar("Activities"));
        CustomEntry customEntry = new CustomEntry(entry);
        DatabaseService.addEntry(customEntry);
        currentUser.addEntry(entry.getId());
        System.out.println("Entry added: " + entry.getId());
        ArrayList<String> students = course.getStudents();
        try {
            for (String student : students) {
                User user = Database.userDatabase.get(student);
                user.addEntry(entry.getId());
                System.out.println("Added to " + user.getUsername());
            }
        }
        catch (Exception e) {
            System.out.println("Error while adding calendars to students");
        }
    }

    private void createNewPost(String details) {
        Discussion newDiscussion = new Discussion("", details, currentUser);
        course.addDiscussion(details);
        displayPosts();
    }

    @FXML
    private void displayPosts() {
        contentArea.getChildren().removeIf(node -> (node != fileOptionsPane && node != discussionOptionsPane && node != activityOptionsPane));
        fileOptionsPane.setVisible(false);
        activityOptionsPane.setVisible(false);
        scrollPane = makeScrollPane();

        if (currentUser  instanceof Teacher) {
            discussionOptionsPane.setVisible(true);
        }

        if (posts == null) return;
        VBox postsContainer = new VBox();
        postsContainer.setPrefWidth(812.0);
        postsContainer.setStyle("-fx-background-color: transparent;");

        for (String postContent : posts) {
            VBox postCard = new VBox();
            postCard.setMinHeight(100);
            postCard.setPrefWidth(250);
            postCard.getStyleClass().add("post-card");

            Label postAuthor = new Label(currentUser .getFirstName() + " " + currentUser .getLastName());
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
        if (currentUser  instanceof Teacher) {
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

        if (activities == null) return;

        VBox activitiesContainer = new VBox();
        activitiesContainer.setPrefWidth(812.0);
        activitiesContainer.setStyle("-fx-background-color: transparent;");

        for (Activity activity: activities) {
            String title = activity.getTitle();
            VBox activityCard = new VBox();
            activityCard.setMinHeight(60);
            activityCard.setPrefWidth(700);
            activityCard.getStyleClass().add("post-card");
            activityCard.setCursor(Cursor.HAND);

            if (currentUser instanceof Teacher) {
                activityCard.setOnMouseClicked(event -> {
                    studentSubmissionsPane.setVisible(true);
                    ArrayList<String> students = course.getStudents();
                    ArrayList<Activity> activities = course.getActivities();

                    studentSubmissionsVBox.getChildren().clear();

                    for (String studentUsername: students) {
                        Student student = (Student) Database.userDatabase.get(studentUsername);

                        VBox studentCard = new VBox();
                        studentCard.setMinHeight(80);
                        studentCard.setPrefHeight(80);
                        studentCard.setPrefWidth(400);
                        studentCard.getStyleClass().add("post-card");
                        studentCard.setCursor(Cursor.HAND);

                        Label studentNameLabel = new Label(student.getFirstName() + " " + student.getLastName());
                        studentNameLabel.getStyleClass().add("post-author");
                        studentNameLabel.setWrapText(true);

                        Label submissionStatusLabel = new Label("No submission");

                        for (Activity a : activities) {
                            if (a.hasSubmissionForStudent(student)) {
                                if (a.getSubmission() != null) {
                                    submissionStatusLabel.setText("Submission: " + a.getSubmission().getAttachment());
                                    submissionStatusLabel.getStyleClass().add("submission-status");
                                }
                                break;
                            }
                        }

                        studentCard.getChildren().addAll(studentNameLabel, submissionStatusLabel);
                        VBox.setMargin(studentCard, new Insets(6));

                        studentSubmissionsVBox.getChildren().add(studentCard);
                    }
                });
            } else if (currentUser instanceof Student) {

                activityCard.setOnMouseClicked(event -> {
                    contentArea.setEffect(new BoxBlur());
                    submissionContainerPane.setVisible(true);
                    currentActivity = activity;
                    if (activity.getSubmission() != null) {
                        submissionPane.setVisible(true);
                        submissionNameLbl.setText(activity.getSubmission().getAttachment());
                    }
                });
            }

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

    private ArrayList<File> getFiles(String baseDir) {
        ArrayList<File> courseFiles = new ArrayList<>();
        Path path = Paths.get(baseDir);

        try (Stream<Path> stream = Files.list(path)) {
            List<Path> paths = stream.toList();
            for (Path filePath : paths) {
                courseFiles.add(filePath.toFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return courseFiles;
    }

    @FXML
    private void displayFiles(String courseDir) {
        files = getFiles(courseDir);

        posts = course.getDiscussions();
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
            posts.remove(index);
            course.setDiscussions(posts);
            hideRemovePostBox();
            displayPosts();
        }
    }

    private void removeActivity(int index) {
        if (index >= 0 && index < activities.size()) {
            activities.remove(index);
            course.setActivities(activities);
            displayRemoveActivityBox();
            displayActivities();
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
            fileUploadService.handleFileUpload(selectedFile, course.getCode());
        }
    }

    @FXML
    private void displayRemovePrompt() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File to Delete");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));

        File initialDirectory = new File(StringService.convertFrom(
                Objects.requireNonNull(DatabaseService.class.getResource("/course-files/" + course.getCode()  + "/"))
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