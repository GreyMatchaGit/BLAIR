package lms.content.todolist;

import database.Database;
import lms.User;
import lms.usertype.Student;
import org.jetbrains.annotations.NotNull;
import services.DatabaseService;
import services.UserService;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class TodoList {

    private ArrayList<Task> tasks;

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

    public void removeTask(@NotNull String key) {

        Task toRemove = null;
        for (Task task : tasks) {
            if (task.getKey().equals(key)) {
                toRemove = task;
                break;
            }
        }

        if (toRemove == null) throw new NoSuchElementException("Trying to remove non-existent task.");
        tasks.remove(toRemove); // Todo: Log(2N)... find a better method!
    }

    public void removeTask(@NotNull Task task) {
        tasks.remove(task);
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