package org.example;

public class Main {
    public static void main(String[] args) {
        GestorFonda gestor = new GestorFonda();

        // 1. Instanciar bebidas con los datos de la tabla
        BebidaAlcoholica chichaAlc = new BebidaAlcoholica("Chicha", 1000, 40, 12.0, false);
        BebidaAlcoholica piscoSour = new BebidaAlcoholica("Pisco Sour", 500, 25, 18.0, true);
        BebidaSinAlcohol chichaSinAlc = new BebidaSinAlcohol("Chicha", 1000, 60, 95);
        BebidaSinAlcohol moteConHuesillo = new BebidaSinAlcohol("Mote con Huesillo", 400, 50, 70);

        // 2. Marcar la chicha alcohólica como restringida
        chichaAlc.setVentaRestringida(true);

        // 3. Registrar bebidas
        gestor.registrarBebida(chichaAlc);
        gestor.registrarBebida(piscoSour);
        gestor.registrarBebida(chichaSinAlc);
        gestor.registrarBebida(moteConHuesillo);

        // 4. Buscar por nombre
        gestor.buscarPorNombre("Chicha");

        // 5. Solicitar ventas
        System.out.println("\n=== VENTAS ===");
        gestor.vender("Pisco Sour", 2);
        gestor.vender("Pisco Sour", 5);
        gestor.vender("Chicha", 1);
        gestor.vender("Mote con Huesillo", 6);

        // 6. Listado de bebidas
        gestor.listarBebidas();
    }
}