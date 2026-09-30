package cl.dsy1102.fonda;

import cl.dsy1102.fonda.model.Bebida;
import cl.dsy1102.fonda.model.BebidaAlcoholica;
import cl.dsy1102.fonda.model.BebidaSinAlcohol;
import cl.dsy1102.fonda.model.GestorFonda;

/**
 * Programa de consola de la Tarea Fiestas Patrias - Fonda San Belarmino (EA1).
 *
 * Revisa el enunciado en docs/enunciado-ea1.md. Ejecuta con: mvn compile exec:java
 */
public class Main {

    public static void main(String[] args) {
        BebidaAlcoholica chicha = new BebidaAlcoholica("Chicha", 1000, 40, 12.0, false);
        chicha.restringirVenta();
        BebidaAlcoholica piscoSour = new BebidaAlcoholica("Pisco Sour", 500, 25, 18.0, true);
        BebidaSinAlcohol chichaSinAlcohol = new BebidaSinAlcohol("Chicha", 1000, 60, 95);
        BebidaSinAlcohol moteConHuesillo = new BebidaSinAlcohol("Mote con Huesillo", 400, 50, 70);

        GestorFonda gestor = new GestorFonda();
        gestor.registrar(chicha);
        gestor.registrar(piscoSour);
        gestor.registrar(chichaSinAlcohol);
        gestor.registrar(moteConHuesillo);

        System.out.println("\n=== BUSQUEDA POR NOMBRE: \"Chicha\" ===");
        for (Bebida bebida : gestor.buscarPorNombre("Chicha")) {
            System.out.println(bebida.obtenerDetalle());
            System.out.println("---");
        }

        System.out.println("\n=== VENTAS ===");
        gestor.vender("Pisco Sour", 2);
        gestor.vender("Pisco Sour", 5);
        gestor.vender("Chicha", 1);
        gestor.vender("Mote con Huesillo", 6);

        System.out.println("\n=== LISTADO DE BEBIDAS ===");
        for (Bebida bebida : gestor.obtenerTodas()) {
            System.out.println(bebida);
        }
    }
}
