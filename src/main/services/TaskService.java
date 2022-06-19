package main.services;


import main.models.Task;

public class TaskService {
    private Task task;
    private final static TaskService INSTANCE = new TaskService();

    private TaskService() {}

    public static TaskService getInstance() {
        return INSTANCE;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}
