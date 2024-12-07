package lms.content.todolist;

import lms.User;
import org.jetbrains.annotations.NotNull;

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

    public void initialize(@NotNull User user) {
        // do something...
    }

    public void removeAllTasks() {
        tasks.clear();
    }
}