package util;

import lms.todolist.Task;

import java.util.Date;

/** BUILDER PATTERN
 *  <p>
 *  This class is responsible for simplifying the
 *  creation of the Task class.
 * */

public class TaskBuilder {

    private String title;
    private String description;
    private Integer status;
    public Date deadline;
    private String key;

    public TaskBuilder(String title) {
        if (title == null || title.isEmpty())
            title = "New Task";
        this.title = title;
    }

    public TaskBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public TaskBuilder setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public TaskBuilder setDeadline(Date deadline) {
        this.deadline = deadline;
        return this;
    }

    public TaskBuilder setKey(String key) {
        this.key = key;
        return this;
    }

    public Task create() {
        return new Task(title, description, status, deadline, key);
    }
}
