package cl.dsy1102.fonda;

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

        try {
            Bebida b1 = new BebidaAlcoholica("Chicha", 1000, 40, 12.0, false);
            Bebida b2 = new BebidaAlcoholica("Pisco Sour", 500, 25, 18.0, true);
            Bebida b3 = new BebidaSinAlcohol("Chicha", 1000, 60, 95);
            Bebida b4 = new BebidaSinAlcohol("Mote con Huesillo", 400, 50, 70);

            ((ConsumoResponsable) b1).restringirVenta();

            GestorFonda gestor = new GestorFonda();
            gestor.registrar(b1);
            gestor.registrar(b2);
            gestor.registrar(b3);
            gestor.registrar(b4);

            System.out.println();

            List<Bebida> bebidasEncontradas = gestor.buscarPorNombre("Chicha");
            System.out.println("=== BUSQUEDA POR NOMBRE: \"Chicha\" ===");
            for (Bebida bebida : bebidasEncontradas) {
                System.out.println(bebida.obtenerDetalle());
                System.out.println("---");
            }

            System.out.println();

            System.out.println("=== VENTAS ===");
            gestor.vender("Pisco Sour", 2);
            gestor.vender("Pisco Sour", 5);
            gestor.vender("Chicha", 1);
            gestor.vender("Mote con Huesillo", 6);

            System.out.println();

            System.out.println("=== LISTADO DE BEBIDAS ===");
            for (Bebida bebida : gestor.obtenerTodas()) {
                System.out.println(bebida);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }
}
