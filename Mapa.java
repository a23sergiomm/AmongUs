import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class Mapa {
    private String[][] mapa;
    private List<String> habitaciones;
    private Map<String, Tarefa> roomTasks;

    public Mapa(List<String> habitaciones, List<Tarefa> tarefas) {
        this.habitaciones = habitaciones; // Añade esta línea
        mapa = new String[4][4];
        roomTasks = new HashMap<>();
        // Asignar tareas a habitaciones
        for (Tarefa tarefa : tarefas) {
            if (!roomTasks.containsKey(tarefa.getHabitacion())) {
                roomTasks.put(tarefa.getHabitacion(), tarefa);
            }
        }
        // Inicializar el mapa con los nombres de las habitaciones
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i * 4 + j < habitaciones.size()) {
                    mapa[i][j] = habitaciones.get(i * 4 + j);
                } else {
                    mapa[i][j] = null;
                }
            }
        }
    }

    // Método para imprimir los nombres de las habitaciones
    public void imprimirHabitacion(int index) {
        if (index >= 0 && index < habitaciones.size()) {
            habitaciones.get(index);
        } else {
            return;
        }
    }

    // Método para moverse a una habitación
    public void mover(String habitacion, Xogador base) {
        Queue<Tarefa> tarefas = base.getTarefas();
        for (Tarefa t : tarefas) {
            if (t.getHabitacion().equals(habitacion)) {
                // Permitir al usuario realizar la tarea
            }
        }
    }

    public Tarefa getTaskForRoom(String room) {
        return roomTasks.get(room);
    }

    public String[][] getMapaArray() {
        return mapa;
    }
}