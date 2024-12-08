package lms.content;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class Task implements Comparable<Task> {

    public String title;
    public String description;

    /* The status of the task has three different modes:
     *      1 - Pending
     *      2 - Overdue
     *      3 - Finished
     *  By default, the newly created task will be set to 0.
     */
    public Integer status;
    public Date deadline;

    /* The purpose of the key is to make the Task shareable to
     * other users. It is shareable in the sense that it is
     * cloned when shared, however, I'm also considering of
     * sharing it by making it a group task. When finished,
     * every member of that task sees it as finished as well.
     */
    public String key;

    // See util.TaskBuilder
    public Task(String title, String description, Integer status, Date deadline, String key) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
        this.key = key;
    }

    /* Default sorting is done through lexicographical sorting.
     * However, this will be changed to sort according to the
     * deadline.
     */
    @Override
    public int compareTo(@NotNull Task o) {
        return title.compareTo(o.title);
    }

    /*
     *  GETTERS AND SETTERS:
     */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getKey() {
        return key;
    }
}
