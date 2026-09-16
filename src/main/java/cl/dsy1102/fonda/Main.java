package cl.dsy1102.fonda;

/**
 * Punto de entrada de la Tarea Fiestas Patrias - Fonda San Belarmino.
 *
 * Revisa el enunciado en README.md. Debes crear, en este mismo paquete,
 * las clases del diagrama: Bebida, BebidaAlcoholica, BebidaSinAlcohol,
 * la interfaz ConsumoResponsable y la clase GestorFonda.
 */
public class Main {

    public static void main(String[] args) {
        BebidaAlcoholica chicha = new BebidaAlcoholica("Chicha", 1000, 40, 12.0,
                false);
        BebidaAlcoholica piscoSour = new BebidaAlcoholica("Pisco Sour", 500, 25, 18.0,
                true);
        BebidaSinAlcohol chichaSinAlcohol = new BebidaSinAlcohol("Chicha", 1000, 60,
                95);
        BebidaSinAlcohol moteConHuesillo = new BebidaSinAlcohol("Mote con Huesillo", 400, 50,
                70);

        chicha.restringirVenta();
        GestorFonda gestor = new GestorFonda();
        gestor.registrar(chicha);
        gestor.registrar(piscoSour);
        gestor.registrar(chichaSinAlcohol);
        gestor.registrar(moteConHuesillo);

        System.out.println("busqueda por nombre");
        for (Bebida bebida: gestor.buscarPorNombre("chicha")){
            System.out.println(bebida.obtenerDetalle());

        }

        System.out.println();
        System.out.println("Vender");
        gestor.vender("mote con Huesillo",7);
        gestor.vender("Pisco Sour", 2);
        gestor.vender("Pisco Sour", 5);
        gestor.vender("Chicha", 1);

        System.out.println();
        System.out.println("Lista bebida");
        for (Bebida bebida: gestor.obtenerTodas()){
            System.out.println(bebida.toString());
            System.out.println("--");
        }

    }
}
