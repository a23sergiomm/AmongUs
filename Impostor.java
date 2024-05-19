import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.Duration;

public class Impostor extends Xogador {
    private List<Xogador> eliminados;
    private List<Xogador> jugadores;
    private List<Impostor> impostores;
    private Instant ultimoUso;
    private static Instant inicioPartida;
    private List<Tarefa> tarefas;

    public Impostor(String alias) {
        super(alias);
        this.eliminados = new ArrayList<>();
        this.jugadores = new ArrayList<>();
        this.impostores = new ArrayList<>();
        this.ultimoUso = Instant.now();
    }

    public static void iniciarPartida() {
        inicioPartida = Instant.now();
    }

    public void eliminarJugador(Xogador jugador) {
        Instant ahora = Instant.now();
        Duration duracionDesdeInicioPartida = Duration.between(inicioPartida, ahora);
        if (duracionDesdeInicioPartida.getSeconds() < 6) {
            System.out.println((duracionDesdeInicioPartida.getSeconds()));
            return;
        }

        Duration duracionDesdeUltimoUso = Duration.between(ultimoUso, ahora);
        if (duracionDesdeUltimoUso.getSeconds() < 6) {
            System.out.println((duracionDesdeUltimoUso.getSeconds())+ "segundazos neno");
            return;
        }

        jugadores.remove(jugador);
        if (jugador instanceof Impostor) {
            impostores.remove(jugador);
            eliminados.add(jugador);
        }

        ultimoUso = ahora;
    }

    public void realizarAccion(BaseDatos base) {
        Random rand = new Random();

        if (rand.nextBoolean()) {
            // Lógica para realizar una tarea falsa (puedes implementarla según tu estructura de datos)
            if (!tarefas.isEmpty()) {
                Tarefa tareaFalsa = tarefas.remove(0);
                // Puedes agregar lógica adicional si es necesario
            }
        } else {
            // Lógica para intentar sabotear o realizar otra acción maliciosa
            // Puedes agregar lógica adicional si es necesario
        }
    }

    @Override
    public boolean isImpostor() {
        return true;
    }
}