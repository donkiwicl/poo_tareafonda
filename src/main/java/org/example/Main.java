package org.example;

public class Main {
    public static void main(String[] args) {
        GestorFonda gestor = new GestorFonda();

        // TODO 1: instanciar las cuatro bebidas con los datos del enunciado.

        Bebida chicha = new BebidaAlcoholica("Chicha", 1000, 40, 12,
                false);
        Bebida piscoSour = new BebidaAlcoholica("psicoSour", 500, 25, 18, true);

        Bebida chichaSinAlcohol = new BebidaSinAlcohol("chicha", 1000, 60, 95);

        Bebida moteHuesillo = new BebidaSinAlcohol("motehuesillo", 400, 50, 70);

        // TODO 2: marcar la bebida alcoholica 'Chicha' con la venta restringida.

        ((ConsumoResponsable) chicha).restringirVenta();

        // TODO 3: registrarlas todas en el gestor.  //  /*Registrar bebidas */

        gestor.registrarBebida(chicha);
        gestor.registrarBebida(piscoSour);
        gestor.registrarBebida(chichaSinAlcohol);
        gestor.registrarBebida(moteHuesillo);

        System.out.println("=== BUSQUEDA POR NOMBRE: \"Chicha\" ===");
        for (Bebida bebida : gestor.buscarBebidas("Chicha")) {
            System.out.println(bebida.obtenerDetalle());}



        // TODO 4: solicitar las cuatro ventas indicadas en el enunciado.

        System.out.println("=== VENTAS ===");
        gestor.ventaBebidas("piscoSour", 2);
        gestor.ventaBebidas("piscoSour", 5);
        gestor.ventaBebidas("chicha", 1);
        gestor.ventaBebidas("moteHuesillo", 6);

        // TODO 5: buscar por nombre "Chicha" y listar todas las bebidas.

        System.out.println("=== LISTADO DE BEBIDAS ===");

        gestor.mostrarBebidas();





    }
}
