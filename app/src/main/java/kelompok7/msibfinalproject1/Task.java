package kelompok7.msibfinalproject1;

public class Task {

    int id;
    String taskName;

    public Task(String taskName){
        this.taskName = taskName;
    }
    public Task(){
        super();
        this.id = id;
        this.taskName = taskName;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

}
