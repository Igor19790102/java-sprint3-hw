public class SubTask extends Task {
    private  Status status;
    private final Type type;

    public SubTask(int id, String name, String description, Status status, Type type) {
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
        return Type.SUB;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description = '" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                " type=" + getType() +
                '}';
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
