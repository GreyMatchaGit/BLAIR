package lms;

import lms.content.todolist.TodoList;

public class LearningManagementSystem {
    private static LearningManagementSystem instance = null; // For the singleton creational DP, ensures nga usa ra ka lms across the project
    private User currentUser ;
    private TodoList todoList;

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
    }

    public User getCurrentUser () {
        return currentUser;
    }

    public TodoList getTodoList() {
        return todoList;
    }
}
