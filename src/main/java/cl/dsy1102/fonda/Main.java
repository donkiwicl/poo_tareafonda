package cl.dsy1102.fonda;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        // TODO 1: instanciar las cuatro bebidas con los datos del enunciado.
        System.out.println("Todo1 = Crear todas las bebidas");
        GestorFonda nuevaFonda = new GestorFonda();
        Bebida chicha = new BebidaAlcoholica("Chicha", 1000, 40, 12.0, false);
        Bebida piscoSour = new BebidaAlcoholica("Pisco Sour", 500, 25, 18.0, true);
        Bebida chichaSinAlcohol = new BebidaSinAlcohol("Chicha", 1000, 60, 95);
        Bebida moteConHuesillo = new BebidaSinAlcohol("Mote con Huesillo", 400, 50, 70);

        // TODO 2: marcar la bebida alcoholica 'Chicha' con la venta restringida.
        ((BebidaAlcoholica) chicha).restringirVenta();
        System.out.println("ToDo2 = Chicha restringida");
        System.out.println(chicha.obtenerDetalle() + "\n");

        // TODO 3: registrarlas todas en el gestor.
        System.out.println("Todo3 = Agregar bebida");
        nuevaFonda.registrar(chicha);
        nuevaFonda.registrar(piscoSour);
        nuevaFonda.registrar(chichaSinAlcohol);
        nuevaFonda.registrar(moteConHuesillo);

        // TODO 4: solicitar las cuatro ventas indicadas en el enunciado.
        System.out.println("\nTodo4 = Realizar Ventas");
        nuevaFonda.vender("Pisco Sour", 2);
        System.out.println(piscoSour.obtenerDetalle());
        try {
            nuevaFonda.vender("Pisco Sour", 5);
        } catch (VentaNoRealizada e){
            System.out.println(e.getMessage());
        }

        //Aca hay un error que no se como solucionar. Ya que la terea pide que ambas chichas se llamen igual.
        //Lo mas facil es cambiar nombre, o realizar la venta no por el gestor, sino por el objeto.
        //En este caso optare por realizar la venta por el objeto en si. Hare las 2 ventas.
        try {
            chicha.venderBebidaActual(1);
        } catch (VentaNoRealizada e){
            System.out.println(e.getMessage());
        }
        chichaSinAlcohol.venderBebidaActual(1);
        System.out.println(chichaSinAlcohol.obtenerDetalle());
        nuevaFonda.vender("Mote con Huesillo", 6);



        // TODO 5: buscar por nombre "Chicha" y listar todas las bebidas.
        System.out.println("\nTodo5 = Buscar Chicha y todas las Bebidas");
        System.out.println(nuevaFonda.buscarPorNombre("Chicha"));;
        System.out.println(nuevaFonda.obtenerTodas() + "\n");
    }
}
