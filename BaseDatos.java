import java.util.*;

public class BaseDatos {
    private List<Tarefa> tarefas;
    private List<Xogador> xogadores = new ArrayList<>();
    private List<Impostor> impostores = new ArrayList<>();
    private Mapa mapa;
    private int numTarefas;

    public BaseDatos(){
        this.tarefas = new ArrayList<>();
        this.xogadores = new ArrayList<>();
        inicializarTarefas();
    }

    private void inicializarTarefas() {
        tarefas.add(new Tarefa("Recoller suministros", "Entrada do instituto", "Recoller suministros da máquina de comida da entrada para sobrevivir ao apocalipsis."));
        tarefas.add(new Tarefa("Encher de papel o baño", "Baño", "Buscar agua no baño."));
        tarefas.add(new Tarefa("Reparar a radio", "Aula de Conferencias", "Reparar a radio para poder poñer música en alto en todo o instituto."));
        tarefas.add(new Tarefa("Espiar a Catalina", "Cafeteria Arca da Vella", "Vixiar a Catalina para entrar a clase antes que ela"));
        tarefas.add(new Tarefa("Barricar as portas", "Entrada do instituto", "Barricar as portas para evitar que entren los chivatos."));
        tarefas.add(new Tarefa("Barricar o garaxe", "Garaxe", "Colocar barricada para non deixar entrar aos profesores no instituto."));
        tarefas.add(new Tarefa("Pinchar rodas dos coches", "Garaxe", "Pinchar as rodas do coche do director"));
        tarefas.add(new Tarefa("Buscar exames de Lozano ", "Clase 01", "Hackear o ordenador do profesor para buscar os exames finais en non ter que ir a xuño"));
        tarefas.add(new Tarefa("Recoller suministros", "Panaderia Mimos", "Recoller tremenda empanadilla de polo e tremenda napolitana de chocolate para ó peito"));
        tarefas.add(new Tarefa("Pintar jraffiti na parede", "Baño", "Facer un ghraffiti to ghuapo na parede do baño"));
        tarefas.add(new Tarefa("Pintar jraffiti na parede", "Conxerseria", "Facer un ghraffiti to ghuapo na parede"));
        tarefas.add(new Tarefa("Pintar jraffiti na parede", "Biblioteca", "Desordenar e romper os libros da biblioteca"));
        tarefas.add(new Tarefa("Tirar petardos", "Atico", "Tirar petardos no tellado de colexio para asustar a xente"));


    }

    public void crearTarefa(String descripcion, String nome, String habitacion, List<Tarefa> tarefasXerales) {
        Tarefa nuevaMision = new Tarefa(nome, habitacion, descripcion);
        tarefasXerales.add(nuevaMision);
        this.tarefas.add(nuevaMision);
    }

    public List<Tarefa> mostrarTarefas(){
        return tarefas;
    }

    public void borrarTarefa(int id){
        if (id >= 0 && id < tarefas.size()) {
            tarefas.remove(id);
            for (int i = 0; i < tarefas.size(); i++) {
                tarefas.get(i).setId(i);
            }
        }
    }

    public List<Xogador> mostrarXogadores(){
        return xogadores;
    }

    public List<Impostor> mostarImpostores(){
        return impostores;
    }

    public void anadirXogador(String alias){
        if (alias.charAt(0) != '@') {
            return;
        } else if (alias.length() > 4) {
            return;
        } else {
            Xogador nuevoXogador = new Xogador(alias);   
            xogadores.add(nuevoXogador);
            nuevoXogador.setAlias(alias);
        } 
    }

    public void borrarXogador(String alias){
        Xogador xogadorBorrar = null;
        for (Xogador xogador : xogadores) {
            if (xogador.getAlias().equals(alias)) {
                xogadorBorrar = xogador;
                break;
            }
        }
        if (xogadorBorrar != null) {
            xogadores.remove(xogadorBorrar);
        }
    }

    //metodo para comprobar si es impostor
    public int getNumImpostores() {
        int count = 0;
        for (Xogador x : xogadores) {
            if (x.isImpostor()) {
                count++;
            }
        }
        return count;
    }

    //metodo para comprobar si es tripulante
    public int getNumTripulantes() {
        int count = 0;
        for (Xogador x : xogadores) {
            if (!x.isImpostor()) {
                count++;
            }
        }
        return count;
    }

    public int getNumTarefas() {
        return numTarefas;
    }

    //metodo para ver el mapa
    public Mapa getMapa() {
        return mapa;
    }
}
