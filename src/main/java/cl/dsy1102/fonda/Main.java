package cl.dsy1102.fonda;

import java.util.ArrayList;
import java.util.List;

/**
 * Punto de entrada de la Tarea Fiestas Patrias - Fonda San Belarmino.
 *
 * Revisa el enunciado en README.md. Debes crear, en este mismo paquete,
 * las clases del diagrama: Bebida, BebidaAlcoholica, BebidaSinAlcohol,
 * la interfaz ConsumoResponsable y la clase GestorFonda.
 */
public class Main {

    public static void main(String[] args) {

        // TODO 1: instanciar las cuatro bebidas con los datos del enunciado.
        List<Bebida> bebidas = new ArrayList<>();
        bebidas.add(new BebidaAlcoholica("Chicha", 1000, 40, 12.0, false));
        bebidas.add(new BebidaAlcoholica("Pisco Sour", 500, 25, 18.0, true));
        bebidas.add(new BebidaSinAlcohol("Chicha", 1000, 60, 95));
        bebidas.add(new BebidaSinAlcohol("Mote con Huesillo", 400, 50, 70));
        GestorFonda gestor =  new GestorFonda();

        // TODO 2: marcar la bebida alcoholica 'Chicha' con la venta restringida.
        restriccion(bebidas);
        // TODO 3: registrarlas todas en el gestor.
        registrar(gestor, bebidas);

        // TODO 4: solicitar las cuatro ventas indicadas en el enunciado y busqueda de "Chicha"
        System.out.println("\n");
        busqueda(gestor, "Chicha");

        System.out.println("\n====== Ventas ======");
        venta(gestor, "Pisco Sour", 2);
        venta(gestor, "Pisco Sour", 5);
        venta(gestor, "Chicha", 2);
        venta(gestor, "Mote con Huesillo", 6);

        // TODO 5: listar todas las bebidas.
        System.out.println("\n====== Listado Bebidas ======");
        gestor.obtenerTodas();
    }

    // metodos auxiliares
    public static void registrar(GestorFonda gestor, List<Bebida> bebidas) {

        for (Bebida bebida : bebidas) {
            System.out.println(bebida.getNombre() + " (" + bebida.getClass().getSimpleName() + ")" + " registrada correctamente.");
            gestor.registrar(bebida);
        }
    }

    public static void restriccion(List<Bebida> bebidas) {
        for  (Bebida bebida : bebidas) {
            if (bebida.getNombre().equals("Chicha") && bebida instanceof BebidaAlcoholica) {
                ((BebidaAlcoholica) bebida).restringirVenta();
            }
        }
    }

    public static void busqueda(GestorFonda gestor, String nombre) {
        System.out.println("=== Buscando el nombre: " + nombre + "===");

        List<Bebida> resultados = gestor.buscarXNombre(nombre);
        for (Bebida bebida : resultados) {
            System.out.println(bebida.obtenerDetalle());
        }

        System.out.println("Resultados encontrados");
    }

    public static void venta(GestorFonda gestor, String nombre, int unidad) {
        gestor.vender(nombre, unidad);
    }
}