package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lms.LearningManagementSystem;
import lms.User;
import lms.content.todolist.Task;
import lms.content.todolist.TodoList;
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

    // Todo: Change me
    String lightColor = "";
    String mediumColor = "#ca6362";
    String darkerColor = "#e1564c";

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
    }

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
        taskTitle.setPromptText("Task name");
        taskTitle.setBackground(null);
        taskTitle.setPrefWidth(background.getWidth() - 40);
        taskTitle.setPrefHeight(50);
        taskTitle.setFont(font);
        taskTitle.setLayoutX(20);
        taskTitle.setLayoutY(60);
        taskTitle.setFocusTraversable(false);

        TextField taskDescription = new TextField("");
        taskDescription.setPromptText("Description");
        taskDescription.setBackground(null);
        taskDescription.setFont(new Font("Product Sans", 16));
        taskDescription.setPrefWidth(background.getWidth() - 40);
        taskDescription.setPrefHeight(30);
        taskDescription.setLayoutX(25);
        taskDescription.setLayoutY(taskTitle.getLayoutY() + taskTitle.getPrefHeight());

        addButton.setOnMouseClicked(mouseEvent -> {
            addTask(pendingContent, taskTitle.getText(), taskDescription.getText());
            taskContent.setVisible(false);
            mainContent.setEffect(null);
            addTaskBtn.setVisible(true);
            ++pendingCount;
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

        TextField taskTitle = new TextField(task.getTitle());
        taskTitle.setPromptText("Task name");
        taskTitle.setBackground(null);
        taskTitle.setPrefWidth(background.getWidth() - 40);
        taskTitle.setPrefHeight(50);
        taskTitle.setFont(font);
        taskTitle.setLayoutX(20);
        taskTitle.setLayoutY(60);

        TextField taskDescription = new TextField(task.getDescription());
        taskDescription.setPromptText("Description");
        taskDescription.setBackground(null);
        taskDescription.setFont(new Font("Product Sans", 16));
        taskDescription.setPrefWidth(background.getWidth() - 40);
        taskDescription.setPrefHeight(30);
        taskDescription.setLayoutX(25);
        taskDescription.setLayoutY(taskTitle.getLayoutY() + taskTitle.getPrefHeight());

        doneButton.setOnMouseClicked(mouseEvent -> {

            String taskTitleString = StringService.trim(task.getTitle(), 30);

            title.setText(taskTitleString);
            task.setTitle(taskTitle.getText());
            taskContent.setVisible(false);
            addTaskBtn.setVisible(true);
            initializeTasks();
        });

        taskContent.getChildren().addAll(background, paneTitle, cancelButton, doneButton, taskTitle, taskDescription);
        taskContent.setVisible(true);
    }

    public void addTask(AnchorPane pane, String title, String description) {

        Task newTask = new TaskBuilder(title)
                .setDescription(description)
                .setStatus(1)
                .create();
        todoList.addTask(newTask);

        AnchorPane taskBoxPane = new AnchorPane();
        taskBoxPane.setPrefWidth(380);
        taskBoxPane.setLayoutY(pendingCount * 80);

        Button removeTask = createRemoveButton(newTask);
        Button checkBox = createCheckBox(newTask);
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
        taskBoxTitle.setLayoutX(40);

        taskBoxTitle.setText(StringService.trim(taskBoxTitle.getText(), 30));

        taskBox.setOnMouseClicked(_ -> {
            openEditTaskPane(newTask, taskBoxTitle);
        });

        taskBoxPane.getChildren().addAll(taskBox, taskBoxTitle, checkBox, removeTask);
        pane.getChildren().add(taskBoxPane);

        pendingPane.setContent(pane);
    }

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
        taskBoxPane.setPrefWidth(380);
        taskBoxPane.setLayoutY(count * 80);

        Button removeTask = createRemoveButton(task);
        Button checkBox = createCheckBox(task);
        Button taskBox = new Button();

        taskBox.setStyle(
            String.format("-fx-background-color: %s;", mediumColor) +
            "-fx-background-radius: 10;"
        );
        taskBox.setCursor(Cursor.HAND);
        taskBox.setPrefWidth(pane.getPrefWidth() - 40);
        taskBox.setPrefHeight(70);

        setButtonHoverEffect(taskBox, darkerColor, mediumColor);

        Text taskBoxTitle = new Text(task.getTitle());
        taskBoxTitle.setFont(new Font("Product Sans", 20));
        taskBoxTitle.setStyle("-fx-text-fill: #ffffff;");
        taskBoxTitle.setLayoutY(30);
        taskBoxTitle.setLayoutX(40);

        taskBoxTitle.setText(StringService.trim(taskBoxTitle.getText(), 30));

        taskBox.setOnMouseClicked(_ -> openEditTaskPane(task, taskBoxTitle));

        taskBoxPane.getChildren().addAll(taskBox, taskBoxTitle, checkBox, removeTask);
        pane.getChildren().add(taskBoxPane);

        parentPane.setContent(pane);
    }

    private Button createRemoveButton(Task task) {

        Button removeButton = new Button();
        removeButton.setLayoutX(305);
        removeButton.setLayoutY(22);

        removeButton.setOnMouseClicked(_ -> {
            removeTask(task);
            initializeTasks();
        });

        return removeButton;
    }

    Button createCheckBox(Task task) {

        Button checkBox = new Button();

        checkBox.setLayoutX(10);
        checkBox.setLayoutY(12);
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

        setButtonHoverEffect(checkBox, "#000000", "#ffffff");


        return checkBox;
    }

    public Task removeTask(Task task) {
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