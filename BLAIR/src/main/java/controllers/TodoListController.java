package controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lms.LearningManagementSystem;
import lms.User;
import lms.content.todolist.Task;
import lms.content.todolist.TodoList;
import util.TaskBuilder;

public class TodoListController {

    @FXML
    ScrollPane unfinishedPane, pendingPane, completedPane;

    @FXML
    AnchorPane taskContent, mainContent;

    @FXML
    Button addTaskBtn;

    LearningManagementSystem lms;
    User currentUser;
    TodoList todoList;

    // Todo: Change me
    String lightColor = "";
    String mediumColor = "#ca6362";
    String darkerColor = "#e1564c";

    @FXML
    public void initialize() {

        unfinishedPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pendingPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        completedPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        lms = LearningManagementSystem.getInstance();
        currentUser = lms.getCurrentUser();
        todoList = lms.getTodoList();

        setButtonHoverEffect(addTaskBtn, darkerColor, mediumColor);
        addTaskBtn.setOnMouseClicked(mouseEvent -> {
            openAddTaskPane();
            addTaskBtn.setVisible(false);
            mainContent.setEffect(new BoxBlur());
        });
    }

    @FXML
    AnchorPane pendingContent;

    public void openAddTaskPane() {

        taskContent.getChildren().clear();

        Font font = new Font("Product Sans", 24);

        Rectangle background = new Rectangle(
                800,
                450,
                Paint.valueOf(mediumColor)
        );

        background.setArcHeight(50);
        background.setArcWidth(50);

        Text title = new Text(
                background.getWidth() / 2.0 - 50,
                40,
                "Add Task"
        );

        title.setFont(font);

        Button cancelButton = new Button();

        cancelButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background-radius: 25;"
        );

        cancelButton.setLayoutY(10);
        cancelButton.setLayoutX(10);
        cancelButton.setText("Cancel");
        cancelButton.setFont(font);

        setButtonHoverEffect(cancelButton, darkerColor, mediumColor);

        cancelButton.setOnMouseClicked(mouseEvent -> {
            taskContent.setVisible(false);
            addTaskBtn.setVisible(true);
            mainContent.setEffect(null);
        });

        Button addButton = new Button();

        addButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background-radius: 25;"
        );

        addButton.setLayoutY(10);
        addButton.setLayoutX(120);
        addButton.setText("Add");
        addButton.setFont(font);

        setButtonHoverEffect(addButton, darkerColor, mediumColor);

        TextField taskTitle = new TextField("");
        taskTitle.setPromptText("Enter title of Task bitch");
        taskTitle.setBackground(null);
        taskTitle.setPrefWidth(background.getWidth() - 40);
        taskTitle.setPrefHeight(50);
        taskTitle.setFont(font);
        taskTitle.setLayoutX(20);
        taskTitle.setLayoutY(60);

        TextField taskDescription = new TextField("");
        taskDescription.setPromptText("Enter the description of task");
        taskDescription.setBackground(null);
        taskDescription.setFont(new Font("Product Sans", 16));
        taskDescription.setPrefWidth(background.getWidth() - 40);
        taskDescription.setPrefHeight(30);
        taskDescription.setLayoutX(25);
        taskDescription.setLayoutY(taskTitle.getLayoutY() + taskTitle.getPrefHeight());

        addButton.setOnMouseClicked(mouseEvent -> {
            addTask(pendingContent, taskTitle, taskDescription);
        });

        taskContent.getChildren().addAll(background, title, cancelButton, addButton, taskTitle, taskDescription);
        taskContent.setVisible(true);
    }

    public void openEditTaskPane(Task task, Text title) {

        taskContent.getChildren().clear();

        Font font = new Font("Product Sans", 24);

        Rectangle background = new Rectangle(
                800,
                450,
                Paint.valueOf(mediumColor)
        );

        background.setArcHeight(50);
        background.setArcWidth(50);

        Text paneTitle = new Text(
                background.getWidth() / 2.0 - 50,
                40,
                "Edit Task"
        );

        paneTitle.setFont(font);

        Button cancelButton = new Button();

        cancelButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background-radius: 25;"
        );

        cancelButton.setLayoutY(10);
        cancelButton.setLayoutX(10);
        cancelButton.setText("Cancel");
        cancelButton.setFont(font);

        setButtonHoverEffect(cancelButton, darkerColor, mediumColor);

        cancelButton.setOnMouseClicked(mouseEvent -> {
            taskContent.setVisible(false);
            addTaskBtn.setVisible(true);
            mainContent.setEffect(null);
        });

        Button doneButton = new Button();

        doneButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-background-radius: 25;"
        );

        doneButton.setLayoutY(10);
        doneButton.setLayoutX(120);
        doneButton.setText("Done");
        doneButton.setFont(font);

        setButtonHoverEffect(doneButton, darkerColor, mediumColor);

        TextField taskTitle = new TextField(title.getText());
        taskTitle.setPromptText("Enter title of Task bitch");
        taskTitle.setBackground(null);
        taskTitle.setPrefWidth(background.getWidth() - 40);
        taskTitle.setPrefHeight(50);
        taskTitle.setFont(font);
        taskTitle.setLayoutX(20);
        taskTitle.setLayoutY(60);

        TextField taskDescription = new TextField(task.getDescription());
        taskDescription.setPromptText("Enter the description of task");
        taskDescription.setBackground(null);
        taskDescription.setFont(new Font("Product Sans", 16));
        taskDescription.setPrefWidth(background.getWidth() - 40);
        taskDescription.setPrefHeight(30);
        taskDescription.setLayoutX(25);
        taskDescription.setLayoutY(taskTitle.getLayoutY() + taskTitle.getPrefHeight());

        doneButton.setOnMouseClicked(mouseEvent -> {
            title.setText(taskTitle.getText());
            task.setTitle(taskTitle.getText());
        });

        taskContent.getChildren().addAll(background, paneTitle, cancelButton, doneButton, taskTitle, taskDescription);
        taskContent.setVisible(true);
    }

    public void addTask(AnchorPane pane, TextField title, TextField description) {
        Task newTask = new TaskBuilder(title.getText())
                .setDescription(description.getText())
                .create();
        todoList.addTask(newTask);

        AnchorPane taskBoxPane = new AnchorPane();
        taskBoxPane.setPrefWidth(380);
        taskBoxPane.setLayoutY((todoList.getTasks().size() - 1) * 80);

        Button taskBox = new Button();

        taskBox.setStyle(
                String.format("-fx-background-color: %s;", mediumColor) +
                "-fx-background-radius: 10;"
        );
        taskBox.setCursor(Cursor.HAND);
        taskBox.setPrefWidth(pane.getPrefWidth() - 40);
        taskBox.setPrefHeight(70);

        setButtonHoverEffect(taskBox, darkerColor, mediumColor);

        Text taskBoxTitle = new Text(newTask.getTitle());
        taskBoxTitle.setFont(new Font("Product Sans", 20));
        taskBoxTitle.setStyle("-fx-text-fill: #ffffff;");
        taskBoxTitle.setLayoutY(30);
        taskBoxTitle.setLayoutX(10);

        taskBox.setOnMouseClicked(_ -> {
            openEditTaskPane(newTask, taskBoxTitle);
        });

        taskBoxPane.getChildren().addAll(taskBox, taskBoxTitle);
        pane.getChildren().add(taskBoxPane);

        pendingPane.setContent(pane);
    }

    public void setButtonHoverEffect(Node button, String onColor, String offColor) {

        String onBackgroundColor = String.format("-fx-background-color: %s;", onColor);
        String offBackgroundColor = String.format("-fx-background-color: %s;", offColor);
        String previousStyle = button.getStyle();

        button.setOnMouseEntered(mouseEvent -> {
            button.setStyle(
                    previousStyle +
                    onBackgroundColor
            );
        });

        button.setOnMouseExited(mouseEvent -> {
            button.setStyle(
                    previousStyle +
                    offBackgroundColor
            );
        });
    }
}