package main.services;

import main.models.Project;

public class ProjectService {
    private Project project;
    private final static ProjectService INSTANCE = new ProjectService();

    private ProjectService() {}

    public static ProjectService getInstance() {
        return INSTANCE;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }
}
