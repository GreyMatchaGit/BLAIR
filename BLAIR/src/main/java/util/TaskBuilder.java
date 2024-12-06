package util;

import lms.content.todolist.Task;

import java.util.Date;

public class TaskBuilder {

    public String title;
    public String description;
    public Integer status;
    public Date deadline;

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

    public Task create() {
        return new Task(title, description, status, deadline);
    }
}
