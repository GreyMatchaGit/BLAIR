package lms;

import lms.content.Quizzler;
import lms.content.TodoList;

public class LearningManagementSystem {
    private static LearningManagementSystem instance = null; // For the singleton creational DP, ensures nga usa ra ka lms across the project
    private User currentUser ;
    private TodoList todoList;
    private Quizzler quizzler;

    private LearningManagementSystem() {
        todoList = new TodoList();
    }

    public static LearningManagementSystem getInstance() {

        if (instance == null) {
            instance = new LearningManagementSystem();
        }

        return instance;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        this.todoList.initialize(user);
        this.quizzler = new Quizzler(user);
    }

    public User getCurrentUser () {
        return currentUser;
    }

    public TodoList getTodoList() {
        return todoList;
    }

    public Quizzler getQuizzler() { return quizzler; }
}
