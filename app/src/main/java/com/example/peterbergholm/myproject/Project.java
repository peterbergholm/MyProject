package com.example.peterbergholm.myproject;

import java.util.Date;

public class Project {
    public long userId;
    public long projectId;
    public String projectName;
    public String projectOwner;
    public String projectDescription;


    public Project(long currentProjectId, long currentUserId, String newProjectName, String newProjectOwner, String newProjectDescription){
        projectId = currentProjectId;
        userId = currentUserId;
        projectName = newProjectName;
        projectOwner = newProjectOwner;
        projectDescription= newProjectDescription;
    }

    public  void setProjectId(long mProjectId) {
        projectId = mProjectId;
    }
    public long getProjectId() {
        return projectId;
    }
}
