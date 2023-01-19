import java.util.Scanner;

public class CommandManager {
    int id;
    String name = "";
    String description = "";
    int status;
    TaskManager taskManager = new TaskManager();
    SingleTask.ToCreate singleTaskToCreate = new SingleTask.ToCreate(name);
    EpicTask.ToCreate epicTaskToCreate = new EpicTask.ToCreate(name);
    SubTask.ToCreate subTaskToCreate = new SubTask.ToCreate(name);

    Scanner scanner = new Scanner(System.in);

    public void printMenuAndHandleCommandInfinitely() {
        while (true) {
            printMenu();
            int command = scanner.nextInt();
            if (command == 1) {
                while (true) {
                    System.out.println("1 - Простая задача");
                    System.out.println("2 - Эпик");
                    System.out.println("0 - Выход");
                    command = scanner.nextInt();
                    if (command == 1) {
                        System.out.println("Введите название задачи");
                        name = scanner.next();
                        System.out.println("Введите описание задачи");
                        description = scanner.next();
                        taskManager.saveNewTask(name, description, singleTaskToCreate);
                        System.out.println(taskManager.getAllTasks());
                    } else if (command == 2) {
                        while (true) {
                            System.out.println("1 - Создать эпик");
                            System.out.println("2 - Создать подзадачу эпика");
                            System.out.println("0 - Выход");
                            command = scanner.nextInt();
                            if (command == 1) {
                                System.out.println("Введите название эпика");
                                name = scanner.next();
                                System.out.println("Введите описание эпика");
                                description = scanner.next();

                                taskManager.saveNewEpicTask(name, description, epicTaskToCreate);
                                System.out.println(taskManager.getAllTasks());
                            } else if (command == 2) {
                                System.out.println("Введите id эпика");
                                id = scanner.nextInt();
                                System.out.println("Введите название подзадачи");
                                name = scanner.next();
                                System.out.println("Введите описание подзадачи");
                                description = scanner.next();
                                taskManager.saveNewSubTask(id, name, description, subTaskToCreate);
                                System.out.println(taskManager.getAllTasks());
                            } else if (command == 0) {
                                System.out.println("Выход");
                                break;
                            }
                        }
                    } else if (command == 0) {
                        System.out.println("Выход");
                        break;
                    } else {
                        System.out.println("Извините, такой команды нет...");
                    }
                }
            } else if (command == 2) {
                if (checkNullValueSingleTask()) {
                    continue;
                }
                if (checkNullValueEpicTask()) {
                    continue;
                }
                System.out.println(taskManager.getAllTasks());
                System.out.println("Введите id обновляемой задачи");
                id = scanner.nextInt();
                System.out.println("Введите название новой задачи");
                name = scanner.next();
                if (id == taskManager.singleTask.getId() || !(id == taskManager.epicTask.getId())) {
                    System.out.println("Выберите статус задачи");
                    System.out.println("1 - IN_PROGRESS");
                    System.out.println("2 - DONE");
                    status = scanner.nextInt();
                }

                taskManager.update(id, name, description, singleTaskToCreate, epicTaskToCreate, subTaskToCreate, status);
                System.out.println(taskManager.getAllTasks());
            } else if (command == 3) {
                while (true) {
                    System.out.println("1 - Получение списка всех задач");
                    System.out.println("2 - Получение задачи по идентификатору");
                    System.out.println("3 - Получение списка всех подзадач определённого эпика");
                    System.out.println("0 - Выход");
                    command = scanner.nextInt();
                    if (command == 1) {
                        System.out.println(taskManager.getAllTasks());
                    } else if (command == 2) {
                        System.out.println("Введите id задачи");
                        id = scanner.nextInt();
                        System.out.println(taskManager.getTaskByIds(id));
                    } else if (command == 3) {
                        System.out.println("Введите id эпика");
                        id = scanner.nextInt();
                        System.out.println(taskManager.getEpicTasksByIds(id));
                    } else if (command == 0) {
                        System.out.println("Выход");
                        break;
                    }
                }
            } else if (command == 4) {
                while (true) {
                    System.out.println("1 - Удаление всех задач");
                    System.out.println("2 - Удаление по идентификатору");
                    System.out.println("0 - Выход");
                    command = scanner.nextInt();
                    if (command == 1) {
                        taskManager.removeAllTasks();
                    } else if (command == 2) {
                        System.out.println("Введите id удаляемой задачи");
                        System.out.println(taskManager.getAllTasks());
                        id = scanner.nextInt();
                        taskManager.removeTasksById(id, taskManager.epicTask);
                        System.out.println(taskManager.getAllTasks());
                    } else if (command == 0) {
                        System.out.println("Выход");
                        break;
                    }
                }
            } else if (command == 0) {
                System.out.println("Выход");
                break;
            } else {
                System.out.println("Извините, такой команды нет.");
            }
        }
    }

    public static void printMenu() {
        System.out.println("Что вы хотите сделать? ");
        System.out.println("1 - Создание задачи");
        System.out.println("2 - Обновление задачи");
        System.out.println("3 - Получение списка задач");
        System.out.println("4 - Удаление задачи");
        System.out.println("0 - Выход");
    }

    private boolean checkNullValueSingleTask() {
        if (taskManager.singleTask == null) {
            System.out.println("Сначала необходимо создать задачу!" + "\n");
            return true;
        } else
            return false;
    }

    private boolean checkNullValueEpicTask() {
        if (taskManager.epicTask == null) {
            System.out.println("Сначала необходимо создать эпик!" + "\n");
            return true;
        } else
            return false;
    }
}
