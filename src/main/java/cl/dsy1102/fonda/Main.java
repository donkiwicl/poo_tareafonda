//Use IA para guiarme y aprender como hacerlo asi que hay conceptos que no hemos visto en clases

package cl.dsy1102.fonda;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        GestorFonda gestor = new GestorFonda();

        Bebida chichaAlcoholica =
                new BebidaAlcoholica(
                        "Chicha",
                        1000,
                        40,
                        12.0,
                        false,
                        true
                );

        Bebida piscoSour =
                new BebidaAlcoholica(
                        "Pisco Sour",
                        500,
                        25,
                        18.0,
                        true,
                        false
                );

        Bebida chichaSinAlcohol =
                new BebidaSinAlcohol(
                        "Chicha",
                        1000,
                        60,
                        95
                );

        Bebida moteConHuesillo =
                new BebidaSinAlcohol(
                        "Mote con Huesillo",
                        400,
                        50,
                        70
                );

        gestor.registrar(chichaAlcoholica);
        gestor.registrar(piscoSour);
        gestor.registrar(chichaSinAlcohol);
        gestor.registrar(moteConHuesillo);

        System.out.println();

        System.out.println(
                "=== BUSQUEDA POR NOMBRE: \"Chicha\" ==="
        );

        List<Bebida> resultados =
                gestor.buscarPorNombre("Chicha");

        for (Bebida bebida : resultados) {
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
    }
}