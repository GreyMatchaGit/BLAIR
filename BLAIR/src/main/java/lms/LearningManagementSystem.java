package lms;

import lms.quizzler.Quizzler;
import lms.todolist.TodoList;
import lms.usertype.User;

/**
 *  SINGLETON PATTERN <p>
 *  This class is the core of the application logic.
 *  The entire application itself is a Learning Management System,
 *  therefore, it is logical to only have one
 *  Learning Management System.
 */

public class LearningManagementSystem {

    private static LearningManagementSystem instance = null; // For the singleton creational DP, ensures nga usa ra ka lms across the project
    private User currentUser ;
    private TodoList todoList;
    private Quizzler quizzler;

    private LearningManagementSystem() {
        todoList = new TodoList();
        quizzler = new Quizzler();
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
        this.quizzler.initialize(user);
    }

    public User getCurrentUser () {
        return currentUser;
    }

    public TodoList getTodoList() {
        return todoList;
    }

    public Quizzler getQuizzler() { return quizzler; }
}
