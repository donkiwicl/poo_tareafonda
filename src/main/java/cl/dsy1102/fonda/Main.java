package cl.dsy1102.fonda;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        // TODO 1: instanciar las cuatro bebidas con los datos del enunciado.
        System.out.println("ToDo1 = Instanciar bebidas (no tiene output en consola)");
        BebidaAlcoholica chicha = new BebidaAlcoholica("Chicha", 1000 , 40 , 12.0, false);
        BebidaAlcoholica piscoSour = new BebidaAlcoholica("Pisco Sour", 500 , 25 , 18.0, true);
        BebidaSinAlcohol chichaSinAlcohol = new BebidaSinAlcohol("Chicha", 1000, 60, 95);
        BebidaSinAlcohol moteConHuesillo = new BebidaSinAlcohol("Mote con Huesillo", 400, 50, 70);



        // TODO 2: marcar la bebida alcoholica 'Chicha' con la venta restringida.
        chicha.restringirVenta();
        System.out.println("ToDo2 = Chicha restringida");
        System.out.println(chicha.obtenerDetalle() + "\n");

        // TODO 3: registrarlas todas en el gestor.
        System.out.println("Todo3 = Agregar bebida");
        GestorFonda gestorFonda = new GestorFonda();
        gestorFonda.registrar(chicha);
        gestorFonda.registrar(piscoSour);
        gestorFonda.registrar(chichaSinAlcohol);
        gestorFonda.registrar(moteConHuesillo);

        // TODO 4: solicitar las cuatro ventas indicadas en el enunciado.
        System.out.println("\nTodo4 = Realizar Ventas");
        vender(gestorFonda, "Pisco Sour", 2);
        vender(gestorFonda, "Pisco Sour", 5);
        vender(gestorFonda, "Chicha", 1);
        vender(gestorFonda, "Mote con Huesillo", 6);



        // TODO 5: buscar por nombre "Chicha" y listar todas las bebidas.
        System.out.println("\nTodo5 = Buscar Chicha y todas las Bebidas");
        System.out.println("=== LISTADO DE BEBIDAS ===");
        System.out.println(gestorFonda.obtenerTodas());
        System.out.println("\n=== BUSQUEDA POR NOMBRE: \"Chicha\" ===");
        for (Bebida bebida : gestorFonda.buscarPorNombre("Chicha")) {
            System.out.println(bebida.obtenerDetalle());
        }
    }
    private static void vender(GestorFonda gestor, String nombre, int unidades) {
        try {
            gestor.vender(nombre, unidades);
        } catch (VentaNoRealizada e) {
            System.out.println("Venta rechazada: " + e.getMessage());
        } catch (ProductoNoEncontrado e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
