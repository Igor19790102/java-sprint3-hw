
public class SingleTask extends Task {
    private Status status;
    private final Type type;


    public SingleTask(int id, String name, String description, Status status, Type type) {
        super(id, name, description);
        this.status = status;
        this.type = type;
    }

    @Override
    public Status getStatus() {
        return status;
    }


    @Override
    public Type getType() {
        return Type.SINGLE;
    }

    @Override
    public String toString() {
        return "SingleTask{" +
                "id = " + getId() +
                ", name = '" + getName() + '\'' +
                ", description = '" + getDescription() + '\'' +
                ", status = '" + getStatus() + '\'' +
                " type = '" + getType() + '\'' +
                '}' + '\n';
    }

    public static class ToCreate {
        private String name;
        private String description;

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

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
