package controllers;

import database.Database;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lms.LearningManagementSystem;
import lms.usertype.User;
import lms.todolist.Task;
import lms.todolist.TodoList;
import services.StringService;
import util.TaskBuilder;

public class TodoListController {

    @FXML
    ScrollPane pendingPane, completedPane;

    @FXML
    AnchorPane taskContent, mainContent;

    @FXML
    Button addTaskBtn;

    @FXML
    AnchorPane pendingContent, completedContent;

    Integer pendingCount, completedCount;

    LearningManagementSystem lms;
    User currentUser;
    TodoList todoList;

    String lighterColor = "#EAEAEA";
    String lightColor = "#CDCDCD";
    String pendingColor = "#975050";
    String lightPendingColor = "#BA6C6C";
    String mediumColor = "#343A40";
    String darkerColor = "#535A60";

    @FXML
    public void initialize() {

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

        initializeTasks();
    }

    public void initializeTasks() {

        pendingCount = 0; completedCount = 0;
        pendingContent.getChildren().clear();
        completedContent.getChildren().clear();

        for (Task task : todoList.getTasks()) {

            if (task.getStatus() == 1) {
                addTask(pendingContent, task);
                ++pendingCount;
            } else {
                addTask(completedContent, task);
                ++completedCount;
            }
        }

        addTask(pendingContent, "test", "test");
    }

    public void openAddTaskPane() {

        taskContent.getChildren().clear();

        Font font = new Font("Product Sans", 20);

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
        title.setFill(Color.WHITE);

        Button cancelButton = new Button();

        cancelButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background-radius: 25;"
        );

        cancelButton.setLayoutY(10);
        cancelButton.setLayoutX(10);
        cancelButton.setText("Cancel");
        cancelButton.setFont(font);
        cancelButton.setTextFill(Color.WHITE);
        cancelButton.setCursor(Cursor.HAND);

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
        addButton.setTextFill(Color.WHITE);
        addButton.setCursor(Cursor.HAND);
        setButtonHoverEffect(addButton, darkerColor, mediumColor);

        TextField taskTitle = new TextField("");
        taskTitle.setPromptText("Task name");
        taskTitle.setStyle("-fx-text-fill: white;");
        taskTitle.setBackground(null);
        taskTitle.setPrefWidth(background.getWidth() - 40);
        taskTitle.setPrefHeight(50);
        taskTitle.setFont(font);
        taskTitle.setLayoutX(20);
        taskTitle.setLayoutY(60);
        taskTitle.setFocusTraversable(false);

        TextArea taskDescription = new TextArea("");
        taskDescription.setPromptText("Description");
        taskDescription.setBackground(null);
        taskDescription.setFont(new Font("Product Sans", 16));
        taskDescription.setPrefWidth(background.getWidth() - 40);
        taskDescription.setMaxWidth(taskDescription.getPrefWidth());
        taskDescription.setWrapText(true);
        taskDescription.setLayoutX(25);
        taskDescription.setLayoutY(taskTitle.getLayoutY() + taskTitle.getPrefHeight());
        taskDescription.setStyle("-fx-background-color: white;" +
                "-fx-background-radius: 20; " +
                "-fx-border-color: transparent; " +
                "-fx-border-width: 0; " +
                "-fx-border-style: solid; " +
                "-fx-padding: 10;");

        addButton.setOnMouseClicked(mouseEvent -> {
            addTask(pendingContent, taskTitle.getText(), taskDescription.getText());
            taskContent.setVisible(false);
            mainContent.setEffect(null);
            addTaskBtn.setVisible(true);
            ++pendingCount;
        });


        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setRadius(20);
        dropShadow.setColor(Color.color(0, 0, 0, 0.8));

        taskContent.setEffect(dropShadow);
        taskContent.getChildren().addAll(background, title, cancelButton, addButton, taskTitle, taskDescription);
        taskContent.setVisible(true);
    }

    public void openEditTaskPane(Task task, Text title) {

        mainContent.setEffect(new BoxBlur());
        taskContent.getChildren().clear();

        Font font = new Font("Product Sans", 20);

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
        paneTitle.setFill(Color.WHITE);

        Button cancelButton = new Button();

        cancelButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background-radius: 25;"
        );

        cancelButton.setLayoutY(10);
        cancelButton.setLayoutX(10);
        cancelButton.setText("Cancel");
        cancelButton.setTextFill(Color.WHITE);
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
        doneButton.setTextFill(Color.WHITE);
        doneButton.setFont(font);

        setButtonHoverEffect(doneButton, darkerColor, mediumColor);

        TextField taskTitle = new TextField(task.getTitle());
        taskTitle.setPromptText("Task name");
        taskTitle.setStyle("-fx-text-fill: white;");
        taskTitle.setBackground(null);
        taskTitle.setPrefWidth(background.getWidth() - 40);
        taskTitle.setPrefHeight(50);
        taskTitle.setFont(font);
        taskTitle.setLayoutX(20);
        taskTitle.setLayoutY(60);

        TextArea taskDescription = new TextArea(task.getDescription());
        taskDescription.setPromptText("Description");
        taskDescription.setBackground(null);
        taskDescription.setFont(new Font("Product Sans", 16));
        taskDescription.setPrefWidth(background.getWidth() - 40);
        taskDescription.setMaxWidth(taskDescription.getPrefWidth());
        taskDescription.setWrapText(true);
        taskDescription.setLayoutX(25);
        taskDescription.setLayoutY(taskTitle.getLayoutY() + taskTitle.getPrefHeight());
        taskDescription.setStyle("-fx-background-color: white;" +
                "-fx-background-radius: 20; " +
                "-fx-border-color: transparent; " +
                "-fx-border-width: 0; " +
                "-fx-border-style: solid; " +
                "-fx-padding: 10;");

        doneButton.setOnMouseClicked(mouseEvent -> {

            String taskTitleString = StringService.trim(task.getTitle(), 30);

            title.setText(taskTitleString);
            task.setTitle(taskTitle.getText());
            taskContent.setVisible(false);
            addTaskBtn.setVisible(true);
            mainContent.setEffect(null);

            initializeTasks();
        });

        taskContent.getChildren().addAll(background, paneTitle, cancelButton, doneButton, taskTitle, taskDescription);
        taskContent.setVisible(true);
    }

    // Pending Contents
    public void addTask(AnchorPane pane, String title, String description) {

        Task newTask = new TaskBuilder(title)
                .setDescription(description)
                .setStatus(1)
                .setKey(StringService.generateKey(title + pendingCount.toString()))
                .create();
        todoList.addTask(newTask);

        LearningManagementSystem.getInstance().getNotificationSystem().addNotification(title, description);

        AnchorPane taskBoxPane = new AnchorPane();
        taskBoxPane.setStyle("-fx-background-color: transparent;");
        taskBoxPane.setPrefWidth(400);
        taskBoxPane.setPrefHeight(40);
        taskBoxPane.setLayoutY(pendingCount * 50);

        Button removeTask = createRemoveButton(newTask);
        Button checkBox = createCheckBox(newTask);
        Button taskBox = new Button();

        taskBox.setStyle(
            String.format("-fx-background-color: %s;", lightColor) +
            "-fx-background-radius: 20;"
        );
        taskBox.setCursor(Cursor.HAND);
        taskBox.setPrefWidth(pane.getPrefWidth());
        taskBox.setPrefHeight(40);

        setButtonHoverEffect(taskBox, lighterColor, lightColor);

        Text taskBoxTitle = new Text(newTask.getTitle());
        taskBoxTitle.setFont(new Font("Product Sans", 15));
        taskBoxTitle.setFill(Color.valueOf("#232323"));
        taskBoxTitle.setLayoutY(25);
        taskBoxTitle.setLayoutX(40);

        taskBoxTitle.setText(StringService.trim(taskBoxTitle.getText(), 30));

        taskBox.setOnMouseClicked(_ -> {
            openEditTaskPane(newTask, taskBoxTitle);
        });

        taskBoxPane.getChildren().addAll(taskBox, taskBoxTitle, checkBox, removeTask);
        pane.getChildren().add(taskBoxPane);

        pendingPane.setContent(pane);
    }

    // Completed Contents
    public void addTask(AnchorPane pane, Task task) {

        Integer count;
        ScrollPane parentPane;

        if (pane.equals(pendingContent)) {
            count = pendingCount;
            parentPane = pendingPane;
        } else {
            count = completedCount;
            parentPane = completedPane;
        }

        AnchorPane taskBoxPane = new AnchorPane();
        taskBoxPane.setStyle("-fx-background-color: transparent;");
        taskBoxPane.setPrefWidth(400);
        taskBoxPane.setPrefHeight(40);
        taskBoxPane.setLayoutY(count * 50);

        Button removeTask = createRemoveButton(task);
        Button checkBox = createCheckBox(task);
        Button taskBox = new Button();

        taskBox.setStyle(
            String.format("-fx-background-color: %s;", lightColor) +
            "-fx-background-radius: 20;"
        );
        taskBox.setCursor(Cursor.HAND);
        taskBox.setPrefWidth(pane.getPrefWidth());
        taskBox.setPrefHeight(40);

        setButtonHoverEffect(taskBox, lighterColor, lightColor);

        Text taskBoxTitle = new Text(task.getTitle());
        taskBoxTitle.setFont(new Font("Product Sans", 15));
        taskBoxTitle.setFill(Color.valueOf("#232323"));
        taskBoxTitle.setLayoutY(25);
        taskBoxTitle.setLayoutX(40);

        taskBoxTitle.setText(StringService.trim(taskBoxTitle.getText(), 30));

        taskBox.setOnMouseClicked(_ -> openEditTaskPane(task, taskBoxTitle));

        taskBoxPane.getChildren().addAll(taskBox, taskBoxTitle, checkBox, removeTask);
        pane.getChildren().add(taskBoxPane);

        parentPane.setContent(pane);
    }

    private Button createRemoveButton(Task task) {

        Button removeButton = new Button();
        removeButton.setCursor(Cursor.HAND);
        removeButton.setStyle("-fx-background-color: #975050; -fx-text-fill: white; -fx-font-weight: bold;");
        removeButton.setMaxHeight(20);
        removeButton.setMaxWidth(20);
        removeButton.setLayoutX(340);
        removeButton.setLayoutY(9);
        removeButton.setText("X");

        setButtonHoverEffect(removeButton, "#BA6C6C", "#975050");

        removeButton.setOnMouseClicked(_ -> {
            removeTask(task);
            initializeTasks();
        });

        return removeButton;
    }

    Button createCheckBox(Task task) {

        Button checkBox = new Button();

        checkBox.setCursor(Cursor.HAND);
        checkBox.setLayoutX(10);
        checkBox.setLayoutY(10);
        checkBox.setPrefHeight(20);
        checkBox.setPrefWidth(20);
        checkBox.setMinHeight(0);
        checkBox.setStyle(
            String.format("-fx-background-color: %s;", "#ffffff") +
            "-fx-background-radius: 100;"
        );

        checkBox.setOnMouseClicked(_ -> {
            todoList.getTasks().addLast(
                    removeTask(task)
            );
            if (task.getStatus() == 1) {
                task.setStatus(2);
            } else {
                task.setStatus(1);
            }
            initializeTasks();
        });

        setButtonHoverEffect(checkBox, "#FFC107", "#ffffff");


        return checkBox;
    }

    public Task removeTask(Task task) {
        Database.taskDatabase.remove(task.getKey());
        todoList.getTasks().remove(task);
        initializeTasks();
        return task;
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