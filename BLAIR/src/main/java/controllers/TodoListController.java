package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import lms.LearningManagementSystem;
import lms.User;
import lms.content.todolist.TodoList;

public class TodoListController {

    @FXML
    ScrollPane unfinishedPane, pendingPane, completedPane;

    @FXML
    AnchorPane addTaskPane;

    @FXML
    Button addTaskBtn;

    LearningManagementSystem lms;
    User currentUser;
    TodoList todoList;


    @FXML
    public void initialize() {

        lms = LearningManagementSystem.getInstance();
        currentUser = lms.getCurrentUser();
        todoList = lms.getTodoList();


        addTaskBtn.setOnMouseEntered( mouseEvent ->
                addTaskBtn.setStyle("-fx-background-color: #e1564c;")
        );

        addTaskBtn.setOnMouseExited( mouseEvent ->
                addTaskBtn.setStyle("-fx-background-color: #ff483b;")
        );

        addTaskBtn.setOnMouseClicked(mouseEvent -> {
            openAddTaskPane();
        });
    }

    public void openAddTaskPane() {
        addTaskPane.getChildren().clear();

        Rectangle background = new Rectangle(
                800,
                450,
                Paint.valueOf("#ff483b")
        );
        background.setArcHeight(50);
        background.setArcWidth(50);

        addTaskPane.getChildren().add(background);
    }
}
