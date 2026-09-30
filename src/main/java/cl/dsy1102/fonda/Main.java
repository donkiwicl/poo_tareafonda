package cl.dsy1102.fonda;

public class Main {
    public static void main(String[] args) {
        GestorFonda gestor = new GestorFonda();

        BebidaAlcoholica chichaAlcoholica = new BebidaAlcoholica("Chicha", 1000, 40, 12.0, false, true);
        BebidaAlcoholica piscoSour = new BebidaAlcoholica("Pisco Sour", 500, 25,  18.0, true, false);

        BebidaSinAlcohol chichaSinAlcohol = new BebidaSinAlcohol("Chicha", 1000, 60, 95);
        BebidaSinAlcohol moteConHuesillo = new BebidaSinAlcohol("Mote con Huesillo", 400, 50, 70);

        chichaAlcoholica.restringirVenta();

        gestor.registrarBebida(chichaAlcoholica);
        gestor.registrarBebida(piscoSour);
        gestor.registrarBebida(chichaSinAlcohol);
        gestor.registrarBebida(moteConHuesillo);

        System.out.println("\n");

        gestor.buscarPorNombre("Chicha");


        System.out.println("\n=== VENTAS ===");
        gestor.venderBebida("Pisco Sour", 2);
        gestor.venderBebida("Pisco Sour", 5);
        gestor.venderBebida("Chicha", 1);
        gestor.venderBebida("Mote con Huesillo", 6);

        System.out.println("\n");

        gestor.listarBebidas();
    }
}