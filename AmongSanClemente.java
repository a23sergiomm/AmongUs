import java.util.*;

interface MenuComponente {
    void display();

    void execute();
}

class ExitMenuException extends RuntimeException {
}

class MenuItem implements MenuComponente {
    private String name;
    private Runnable action;

    public MenuItem(String name, Runnable action) {
        this.name = name;
        this.action = action;
    }

    @Override
    public void display() {
        System.out.println(name);
    }

    @Override
    public void execute() {
        action.run();
    }
}

class Menu implements MenuComponente {
    private String name;
    private List<MenuComponente> menuComponentes = new ArrayList<>();

    public Menu(String name) {
        this.name = name;
    }

    public void add(MenuComponente menuComponent) {
        menuComponentes.add(menuComponent);
    }

    @Override
    public void display() {
        System.out.println(name);
        for (int i = 0; i < menuComponentes.size(); i++) {
            System.out.print(i + ".");
            menuComponentes.get(i).display();
        }
    }

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                display();
                int opcion;
                do {
                    System.out.print("Escolle unha opción: ");
                    while (!sc.hasNextInt()) {
                        System.out.println("Eso no es un número. Inténtalo de nuevo.");
                        sc.next(); // descarta la entrada incorrecta
                    }
                    opcion = sc.nextInt();
                    if (opcion < 0 || opcion > menuComponentes.size() - 1) {
                        System.out.println("Opción non válida. Escolla de novo.");
                    }
                } while (opcion < 0 || opcion > menuComponentes.size() - 1);

                menuComponentes.get(opcion).execute();
            } catch (ExitMenuException e) {
                break;
            }
        }
    }
}

public class AmongSanClemente {
    static boolean juegoIniciado = false; // Nuevo booleano para controlar si el juego ha sido iniciado
    static Scanner sc = new Scanner(System.in);

    // Método para añadir tarea
    public static void anadirTarefas(BaseDatos base) {
        System.out.println("Introduce el nombre de la tarea:");
        String nombre = sc.nextLine();

        System.out.println("Introduce la habitación de la tarea:");
        String habitacion = sc.nextLine();

        System.out.println("Introduce la descripción de la tarea:");
        String desc = sc.nextLine();

        Tarefa tarefa = new Tarefa(nombre, habitacion, desc);
        List<Tarefa> tarefasXerales = new ArrayList<>();
        tarefasXerales.add(tarefa);

        base.crearTarefa(nombre, habitacion, desc, tarefasXerales); // Usar el método crearTarefa de la instancia de
                                                                    // Xogador
    }

    // Método para ver las tareas
    public static void verTarefas(BaseDatos base) {
        List<Tarefa> tarefas = base.mostrarTarefas();
        for (Tarefa t : tarefas) {
            System.out.println("-----------" + t.getId() + "----------");
            System.out.println("Nome: " + t.getNome());
            System.out.println("Habitacion: " + t.getHabitacion());
            System.out.println("Descricion: " + t.getDesc());
            System.out.println("------------------------");
        }
    }

    // Método para borrar tarea
    public static void borrarTarefa(BaseDatos base) {
        System.out.println("Introduce o id da tarea a borrar:");
        int id = sc.nextInt();
        sc.nextLine();
        base.borrarTarefa(id);
    }

    // Método para agregar un jugador
    public static void anadirXogador(BaseDatos base) {
        int opcion;
        do {
            System.out.println("Tas no menu de engadir xogador, elixe unha opcion");
            System.out.println("1.Continuar");
            System.out.println("2.Volver");
            System.out.println("Introduce unha opcion do menu: ");
            while (!sc.hasNextInt()) {
                System.out.println("Eso no es un número. Inténtalo de nuevo.");
                sc.next(); // descarta la entrada incorrecta
            }
            opcion = sc.nextInt();
        } while (opcion != 1 && opcion != 2);

        if (opcion == 1) {
            System.out.println(
                    "Introduce o alias do xogador que vas a agregar a lista de xogadores tendo en conta que o alias ten que ter este patron '@usr':");
            String alias = sc.next();
            if (alias.charAt(0) != '@') {
                System.out.println("Ten que empesar por un @ pailann..");
                return;
            } else if (alias.length() > 4) {
                System.out.println("Acordate do patron rapasss '@usr' ");
            } else {
                base.anadirXogador(alias);
                System.out.println("O xogador de alias " + alias + " foi incorporado á lista de Xogadores");
            }

        } else if (opcion == 2) {
            throw new ExitMenuException();
        }
    }

    // Método para ver los jugadores
    public static void verXogadores(BaseDatos base) {
        List<Xogador> xogadores = base.mostrarXogadores();
        System.out.println("-------XOGADORES------");
        for (Xogador x : xogadores) {
            System.out.println("-------------");
            System.out.println("Nome: " + x.getAlias());
        }
    }

    // Método para borrar jugador
    public static void borrarXogador(BaseDatos base) {
        System.out.println("Introduce o nome do xogador que queres borrar:");
        String alias = sc.next();
        base.borrarXogador(alias);
    }

    // Método para verificar que los jugadores tengan las misiones completadas
    private static boolean misionesCompletas(List<Xogador> xogadoresList) {
        for (Xogador x : xogadoresList) {
            if (!x.misionesCompletas()) {
                return false;
            }
        }
        return true;
    }

    public static void verMisiosnes(BaseDatos base) {
        List<Xogador> xogadoresList = base.mostrarXogadores();
        List<Tarefa> tarefas = base.mostrarTarefas();
        Random rand = new Random();

        // while(base.getNumImpostores() < base.getNumTripulantes() &&
        // !misionesCompletas(xogadoresList)){
        for (Xogador x : xogadoresList) {
            Queue<Tarefa> tarefasAsignadas = new LinkedList<>();
            for (int i = 0; i < 10; i++) {
                int randomIndex = rand.nextInt(tarefas.size());
                tarefasAsignadas.add(tarefas.get(randomIndex));
            }
            x.setTarefas(tarefasAsignadas);
            System.out.println("-------------");
            System.out.println("Nome: " + x.getAlias());

            // indicar si el jugador es impostor
            if (x.isImpostor()) {
                System.out.println("Eres un impostor.");
            } else {
                System.out.println("No eres un impostor.");
            }

            System.out.println("Tuas tarefas asignadas son:");
            for (Tarefa t : tarefasAsignadas) {
                System.out.println("-----------" + t.getId() + "----------");
                System.out.println("Nome: " + t.getNome());
                System.out.println("Habitacion: " + t.getHabitacion());
                System.out.println("Descricion: " + t.getDesc());
                System.out.println("------------------------");
            }
            System.out.println("AQUI EMPESAN AS MISIONS DO SEGUINTE XOGADOR");
            System.out.println("-------------------------------------------");
        }
        // }

    }

    // Método para imprimir el mapa
    public static void verMapa(Mapa mapa) {
        String[][] mapaImprimir = mapa.getMapaArray(); // Asume que tienes un getter para el array mapa
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mapaImprimir[i][j] != null) {
                    System.out.print("|" + mapaImprimir[i][j] + "|");
                } else {
                    System.out.print("|  |"); // Imprime espacios si la habitación es null
                }
            }
            System.out.println();
        }
    }


    public static void xogar(BaseDatos base) {
        juegoIniciado = true;
        List<Xogador> xogadoresList = base.mostrarXogadores();
        List<Impostor> impostorList = base.mostarImpostores();
    
        int tareasCompletadasTripulantes = 0;
        int tareasCompletadasImpostores = 0;
    
        while (tareasCompletadasTripulantes < base.getNumTarefas() || base.getNumImpostores() < base.getNumTripulantes()) {
            for (Xogador xogador : xogadoresList) {
                if (!xogador.isImpostor()) {
                    xogador.realizarMision();
                    tareasCompletadasTripulantes++;
                } else {
                    Impostor impostor = (Impostor) xogador;
                    if (new Random().nextBoolean()) {
                        impostor.realizarAccion(base);
                        tareasCompletadasImpostores++;
                    } else {
                        impostor.eliminarJugador(xogador);
                        System.out.println("¡Un tripulante ha sido eliminado por un impostor!");
                    }
                }
            }
    
            if (tareasCompletadasTripulantes == base.getNumTarefas() ||
                    base.getNumTripulantes() == base.getNumImpostores()) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        Menu mainMenu = new Menu("AMONG SANCLEMENTE:");
        Menu xogo = new Menu(" Xogar");
        Menu configuracionMenu = new Menu(" Configuracion");
        Menu tarefasMenu = new Menu("   Tarefas");
        Menu xogadores = new Menu("    Xogadores");
        BaseDatos base = new BaseDatos();
        // Declaramos misionesMapaMenu fuera del bloque de la opción "Xogar"
        Menu misionesMapaMenu = new Menu("  Misiones e Mapa");
        
        xogo.add(new MenuItem("     Xogar", () -> {
            juegoIniciado = false;
            if (juegoIniciado == false) { // Verificamos si misionesMapaMenu ya ha sido creado
                System.out.println("Opción Xogar seleccionada.");
    
                misionesMapaMenu.add(new MenuItem("Ver Misiones", () -> {
                    verMisiosnes(base);
                }));
    
                misionesMapaMenu.add(new MenuItem("Xogar", () -> {
                    System.out.println("Ejecutando método xogar()");
                    xogar(base);
                    System.out.println("Xogo iniciado");
                }));
    
                misionesMapaMenu.add(new MenuItem("Ver Mapa", () -> {
                    // Aquí va el código para ver el mapa
                    List<String> habitaciones = new ArrayList<>();
                    habitaciones.add("Entrada do instituto");
                    habitaciones.add("Clase 01");
                    habitaciones.add("Garaxe");
                    habitaciones.add("Baño");
                    habitaciones.add("Aula Conferencias");
                    habitaciones.add("Cafeteria Arca Vella");
                    habitaciones.add("Panaderia Mimos");
                    habitaciones.add("Conxerseria");
                    habitaciones.add("Biblioteca");
                    habitaciones.add("Ático");
                    habitaciones.add("Departamento de informatica");
    
                    // Crear una lista vacía de tareas
                    List<Tarefa> tarefas = new ArrayList<>();
    
                    // Crear un nuevo mapa con las habitaciones y tareas
                    Mapa mapa = new Mapa(habitaciones, tarefas);
    
                    // Imprimir el mapa
                    verMapa(mapa);
                }));
    
                misionesMapaMenu.add(new MenuItem("Sair", () -> {
                    throw new ExitMenuException();
                }));
    
                xogo.add(misionesMapaMenu);
            } else {
                System.out.println("Mostrando menú existente...");
            }
        }));
    
        xogo.add(new MenuItem("     Sair", () -> {
            throw new ExitMenuException();
        }));
    
        mainMenu.add(xogo);

        // menú de las tareas
        tarefasMenu.add(new MenuItem("      Engadir Tarefa", () -> {
            anadirTarefas(base);
        }));

        tarefasMenu.add(new MenuItem("      Borrar Tarefas", () -> {
            borrarTarefa(base);
        }));

        tarefasMenu.add(new MenuItem("      Ver Tarefas", () -> {
            verTarefas(base);
        }));

        tarefasMenu.add(new MenuItem("      Sair", () -> {
            // Aquí vai o código para sair
            throw new ExitMenuException();
        }));

        configuracionMenu.add(tarefasMenu);

        // menú de los jugadores
        xogadores.add(new MenuItem("      Engadir xogador", () -> {
            anadirXogador(base);
        }));

        xogadores.add(new MenuItem("      Borrar xogador", () -> {
            borrarXogador(base);
        }));

        xogadores.add(new MenuItem("      Ver xogadores", () -> {
            verXogadores(base);
        }));

        xogadores.add(new MenuItem("        Sair", () -> {
            throw new ExitMenuException();
        }));

        configuracionMenu.add(xogadores);
        configuracionMenu.add(new MenuItem("Sair", () -> {
            throw new ExitMenuException();
        }));

        mainMenu.add(configuracionMenu);

        mainMenu.add(new MenuItem("Sair", () -> {
            System.out.println("Saindo...");
            System.exit(0);
        }));

        mainMenu.execute();
    }

}
