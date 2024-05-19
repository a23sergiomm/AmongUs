//Clase para las tareas
public class Tarefa {
    private String nome;
    private String habitacion;
    private String desc;
    private int id;
    private static int nextId = 0;
    private boolean completed;
   

    public Tarefa(String nome, String habitacion, String desc) {
        this.nome = nome;
        this.habitacion = habitacion;
        this.desc = desc;
        this.id = nextId++;
    }

    // Getters e setters
    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getHabitacion(){
        return habitacion;
    }

    public String getDesc(){
        return desc;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}