package work.worklist.domain.work;

import lombok.Data;

@Data
public class Project {
    int projectID;
    String projectName;

    public String getProjectName() {
        return projectName;
    }

    public Project(int projectID, String projectName){
        this.projectID = projectID;
        this.projectName = projectName;
    }

}
