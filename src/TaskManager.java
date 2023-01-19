import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private final TaskIdGenerator taskIdGenerator;
    private final HashMap<Integer, Task> taskById;
    EpicTask epicTask;
    SubTask subTask;
    SingleTask singleTask;

    public TaskManager() {
        this.taskIdGenerator = new TaskIdGenerator();
        this.taskById = new HashMap<>();
    }

    public void saveNewTask(String name, String description, SingleTask.ToCreate singleTaskToCreate) {
        singleTaskToCreate.setName(name);
        singleTaskToCreate.setDescription(description);
        int nextFreeId = taskIdGenerator.getNextFreeId();
        SingleTask singleTask = new SingleTask(
                nextFreeId,
                singleTaskToCreate.getName(),
                singleTaskToCreate.getDescription(),
                Status.NEW,
                Type.SINGLE

        );
        taskById.put(singleTask.getId(), singleTask);
        this.singleTask = singleTask;
    }

    public void saveNewEpicTask(String name, String description, EpicTask.ToCreate epicTaskToCreate) {
        epicTaskToCreate.setName(name);
        int nextFreeId = taskIdGenerator.getNextFreeId();
        EpicTask epicTask = new EpicTask(
                nextFreeId,
                epicTaskToCreate.getName(),
                description,
                Status.NEW,
                Type.EPIC
        );
        taskById.put(epicTask.getId(), epicTask);
        this.epicTask = epicTask;
    }

    public void saveNewSubTask(int id, String name, String description, SubTask.ToCreate subTaskToCreate) {
        subTaskToCreate.setName(name);
        int nextFreeId = taskIdGenerator.getNextFreeId();
        SubTask subTask = new SubTask(
                nextFreeId,
                subTaskToCreate.getName(),
                description,
                Status.NEW,
                Type.SUB
        );
        EpicTask epicTask = (EpicTask) taskById.get(id);
        epicTask.getSubTasks().add(subTask);
    }

    public void update(int id, String name, String description, SingleTask.ToCreate singleTaskToCreate, EpicTask.ToCreate epicTaskToCreate, SubTask.ToCreate subTaskToCreate, int stat) {
        Status newTaskStatus = Status.NEW;
        Status newEpicStatus = Status.NEW;
        if (taskById.containsKey(id) && taskById.get(id).getType() == Type.SINGLE) {
            if (stat == 1) {
                newTaskStatus = Status.IN_PROGRESS;
            } else if (stat == 2) {
                newTaskStatus = Status.DONE;
            }
            singleTaskToCreate.setName(name);
            SingleTask singleTask = new SingleTask(
                    id,
                    singleTaskToCreate.getName(),
                    description,
                    newTaskStatus,
                    Type.SINGLE
            );
            taskById.replace(singleTask.getId(), singleTask);

        } else if (taskById.containsKey(id) && taskById.get(id).getType() == Type.EPIC) {
            if (epicTask.getSubTasks().isEmpty()) {
                newEpicStatus = Status.NEW;
            } else if (taskById.containsKey(id)) {

                List<Task> list = new ArrayList<>();
                int count = 0;
                for (SubTask task : epicTask.getSubTasks()) {
                    if (task.getStatus() == Status.DONE) {
                        list.add(task);
                        count++;
                    }
                }
                if (list.size() == count && count != 0) {
                    newEpicStatus = Status.DONE;
                } else {
                    newEpicStatus = Status.IN_PROGRESS;

                }
            }
            List<SubTask> updatedSubTasks;
            updatedSubTasks = epicTask.getSubTasks();
            epicTaskToCreate.setName(name);
            EpicTask epicTask = new EpicTask(
                    id,
                    epicTaskToCreate.getName(),
                    description,
                    newEpicStatus,
                    Type.EPIC
            );
            taskById.replace(epicTask.getId(), epicTask);
            epicTask.setSubTasks(updatedSubTasks);


        } else if (!epicTask.getSubTasks().isEmpty()) {
            List<SubTask> subTasks = epicTask.getSubTasks();
            for (int i = 0; i < subTasks.size(); i++) {
                SubTask subT = subTasks.get(i);
                if (subT.getId() == id) {
                    if (stat == 1) {
                        newTaskStatus = Status.IN_PROGRESS;
                    } else if (stat == 2) {
                        newTaskStatus = Status.DONE;
                    }
                    epicTask.getSubTasks().removeIf(task -> task.getId() == id);
                    subTaskToCreate.setName(name);
                    SubTask subTask = new SubTask(
                            id,
                            subTaskToCreate.getName(),
                            description,
                            newTaskStatus,
                            Type.SUB
                    );
                    epicTask.getSubTasks().add(subTask);
                }
            }
        }
    }

    public void removeAllTasks() {
        int a = taskById.keySet().size();
        for (int i = 0; i < a; i++) {
            taskById.remove(i);
        }
    }

    public void removeTasksById(int id, EpicTask epicTask) {
        taskById.remove(id);
        epicTask.getSubTasks().removeIf(task -> task.getId() == id);
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : taskById.values()) {
            tasks.add(task);
        }
        return tasks;
    }

    public ArrayList<Task> getTaskByIds(int id) {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!taskById.containsKey(id) && !epicTask.getSubTasks().contains(subTask)) {
            System.out.println("Задачи с таким идентификатором не существует!");
        } else if (taskById.containsKey(id) && (taskById.get(id).getType() == Type.EPIC || taskById.get(id).getType() == Type.SINGLE)) {
            tasks.add(taskById.get(id));
        }
        if (taskById.containsValue(epicTask)) {

            for (SubTask task : epicTask.getSubTasks()) {
                if (task.getId() == id) {
                    tasks.add(task);
                }
            }
        }

        return tasks;
    }

    public ArrayList<Task> getEpicTasksByIds(int id) {
        ArrayList<Task> tasks = new ArrayList<>();
        if (taskById.containsKey(id)) {
            epicTask = (EpicTask) taskById.get(id);
            for (SubTask task : epicTask.getSubTasks()) {
                tasks.add(task);
            }
        } else {
            System.out.println("Эпик с таким идентификатором не существует!");
        }
        return tasks;
    }

    public static final class TaskIdGenerator {
        private int nextFreeId = 0;

        public int getNextFreeId() {
            return nextFreeId++;
        }
    }
}
