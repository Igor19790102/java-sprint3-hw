import java.util.ArrayList;
import java.util.List;

public class EpicTask extends Task {
    private List<SubTask> subTasks;
    private  Status status;
    private final Type type;

    public EpicTask(int id, String name, String description, Status status,Type type) {
        super(id, name, description);
        this.subTasks = new ArrayList<>();
        this.status = status;
        this.type = type;
    }
    @Override
    public Status getStatus() {
        return status;
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    @Override
    public Type getType() {
        return Type.EPIC;
    }


    @Override
    public String toString() {
        return "EpicTask{" +
                "id=" + getId() + "," +
                " name=" + getName() + "," +
                ", description = '" + getDescription() + '\'' +
                " status=" + getStatus() + "," +
                " type=" + getType() +
                " subtasks=" + subTasks +
                '}' + '\n';
    }

    public static class ToCreate {
        private String name;

        public ToCreate(String name) {
            this.name = name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
