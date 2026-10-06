package org.example;

import java.util.ArrayList;
import java.util.List;

public class GestorFonda {
    private List<Bebida> bebidas;

    public GestorFonda() {
        this.bebidas = new ArrayList<>();
    }

    public void registrarBebida(Bebida bebida) {
        bebidas.add(bebida);
        String tipo = (bebida instanceof BebidaAlcoholica) ? "BebidaAlcoholica" : "BebidaSinAlcohol";
        System.out.println(bebida.getNombre() + " (" + tipo + ") registrada correctamente.");
    }

    public void buscarPorNombre(String nombre) {
        System.out.println("\n=== BUSQUEDA POR NOMBRE: \"" + nombre + "\" ===");
        for (Bebida b : bebidas) {
            if (b.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println(b.obtenerFicha());
                System.out.println("---");
            }
        }
    }

    public void vender(String nombre, int cantidad) {
        for (Bebida b : bebidas) {
            if (b.getNombre().equalsIgnoreCase(nombre)) {
                // Evaluamos el contrato de consumo responsable (Polimorfismo a través de Interfaz)
                if (b instanceof ConsumoResponsable) {
                    ConsumoResponsable cr = (ConsumoResponsable) b;
                    if (cr.isVentaRestringida()) {
                        System.out.println("Venta rechazada: " + b.getNombre() + " tiene la venta restringida.");
                        return; // Termina la venta
                    }
                    if (cr.superaLimite(cantidad)) {
                        System.out.println("Venta rechazada: " + cantidad + " unidades de " + b.getNombre() +
                                " superan el limite de " + ConsumoResponsable.MAX_UNIDADES_POR_CLIENTE + " por cliente.");
                        return; // Termina la venta
                    }
                }

                // Si pasa las validaciones o no es restringida, se procesa la venta
                int totalPago = b.calcularPrecio() * cantidad;
                System.out.println("Venta autorizada: " + cantidad + " x " + b.getNombre() + " | Total: $" + totalPago);
                return; // Solo vendemos el primer match que encuentre para ese nombre en este diseño simple
            }
        }
    }

    public void listarBebidas() {
        System.out.println("\n=== LISTADO DE BEBIDAS ===");
        for (Bebida b : bebidas) {
            System.out.println(b.toString()); // Usa el método toString sobreescrito
        }
    }
}