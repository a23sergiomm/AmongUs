import java.util.*;

public class Xogador {
    private String alias;
    private Queue<Tarefa> tarefas;
    private static List<Xogador> xogadores = new ArrayList<>();
    private int misionesPendientes;

    public Xogador(String alias) {
        this.alias = alias;
        this.tarefas = new LinkedList<>();
        this.misionesPendientes = 10; // Supoñemos que o contador de misions inicializa en 10
        xogadores.add(this);
    }

    public void setAlias(String alias) {
        if (alias.charAt(0) != '@') {
            return;
        } else if (alias.length() > 4) {
            return;
        } else {
            this.alias = alias;
        }
    }

    public String getAlias() {
        return alias;
    }

    public Queue<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(Queue<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    public void realizarMision() {
        if (misionesPendientes > 0) {
            misionesPendientes--;
        }
    }

    public boolean misionesCompletas() {
        for(Tarefa t : tarefas){
            if(!t.isCompleted()){
                return false;
            }
        }
        return true;
    }

    public boolean isImpostor() {
        return false;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Tarefa t : tarefas) {
            sb.append("Nombre: ").append(t.getNome());
            sb.append(", Habitacion: ").append(t.getHabitacion());
            sb.append(", Descripcion: ").append(t.getDesc());
            sb.append("\n");
        }
        return sb.toString();
    }
}
