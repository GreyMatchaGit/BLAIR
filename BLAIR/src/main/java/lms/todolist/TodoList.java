package lms.todolist;

import database.Database;
import lms.usertype.User;
import lms.usertype.Student;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TodoList {

    private final ArrayList<Task> tasks;

    public TodoList() {
        tasks = new ArrayList<>();
    }

    @NotNull
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTask(@NotNull Task task) {
        tasks.add(task);
    }

    public void initialize(@NotNull User user) {

        ArrayList<String> keys = ((Student) user).getTasks();

        for (String key : keys) {
            Task task = Database.taskDatabase.get(key);
            if (task != null)
                tasks.add(task);
        }
    }

    public void saveTodoList(@NotNull User user) {

        ArrayList<String> currentTasks = new ArrayList<>();

        for (Task task : tasks) {

            Database.taskDatabase.put(task.getKey(), task);
            currentTasks.add(task.getKey());
        }

        tasks.clear();
        ((Student) user).setTasks(currentTasks);
    }

    public void removeAllTasks() {
        tasks.clear();
    }
}