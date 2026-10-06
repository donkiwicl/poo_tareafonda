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
        GestorFonda gestor = new GestorFonda();
        // TODO 1: instanciar las cuatro bebidas con los datos del enunciado.
        Bebida chicha = new BebidaAlcoholica("Chicha", 1000, 40, 12.0, false, false);
        Bebida piscoSour = new BebidaAlcoholica("Pisco Sour", 500, 25, 18.0, true, false);
        Bebida chichaSinAlcohol = new BebidaSinAlcohol("Chicha", 1000, 60, 95);
        Bebida moteConHuesillo = new BebidaSinAlcohol("Mote con Huesillo", 400, 50, 70);
        // TODO 2: marcar la bebida alcoholica 'Chicha' con la venta restringida.
        ((ConsumoResponsable) chicha).restringirVenta();
        // TODO 3: registrarlas todas en el gestor.  //  /*Registrar bebidas */
        gestor.agregarBebida(chicha);
        gestor.agregarBebida(piscoSour);
        gestor.agregarBebida(chichaSinAlcohol);
        gestor.agregarBebida(moteConHuesillo);

        System.out.println("BUSQUEDA POR NOMBRE: Chicha");
        for (Bebida bebida : gestor.buscarPorNombre("Chicha")) {
            System.out.println(bebida.obtenerDetalle());
        }
        // TODO 4: solicitar las cuatro ventas indicadas en el enunciado.
        System.out.println("VENTA");
        gestor.vender("Pisco Sour", 2);
        gestor.vender("Pisco Sour", 5);
        gestor.vender("Chicha", 1);
        gestor.vender("Mote con Huesillo", 6);

        // TODO 5: buscar por nombre "Chicha" y listar todas las bebidas.
        System.out.println("Lista de bebidas");
        gestor.mostrarBebidas();
    }
}
