package cl.dsy1102.fonda;


import java.util.ArrayList;
import java.util.List;

public class GestorFonda {
    private List<Bebida> bebidas;

    public GestorFonda() {
        this.bebidas = new ArrayList<>();
    }

    public void registrarBebida(Bebida bebida) {
        bebidas.add(bebida);
        System.out.println(bebida.getNombre() + " (" + bebida.getClass().getSimpleName() + ") registrada correctamente.");
    }

    public void buscarPorNombre(String nombre) {
        System.out.println("=== BUSQUEDA POR NOMBRE: " + nombre +" ===");
        for (Bebida b : bebidas) {
            if (b.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println(b.obtenerDetalle());
                System.out.println("---");
            }
        }
    }

    public void venderBebida(String nombre, int cantidad) {
        for (Bebida b : bebidas) {
            if (b.getNombre().equalsIgnoreCase(nombre)) {

                if (b instanceof ConsumoResponsable) {
                    ConsumoResponsable control = (ConsumoResponsable) b;

                    if (control.tieneVentaRestringida()) {
                        System.out.println("Venta rechazada: " + nombre + " tiene la venta restringida.");
                        return;
                    }

                    if (control.superaLimite(cantidad)) {
                        int limite = (b instanceof BebidaAlcoholica)
                                ? ((BebidaAlcoholica) b).getLimiteUnidadesPorCliente()
                                : ConsumoResponsable.maximoUnidades;

                        System.out.println("Venta rechazada: " + cantidad + " unidades de " + nombre +
                                " superan el límite permitido de " + limite + " por cliente.");
                        return;
                    }
                }

                b.setStock(b.getStock() - cantidad);
                double total = b.calcularPrecio() * cantidad;
                System.out.println("Venta autorizada: " + cantidad + " x " + nombre +
                        " | Total: $" + (int) total );
                return;
            }
        }
        System.out.println("Bebida no encontrada: " + nombre);
    }

    public void listarBebidas() {
        System.out.println("=== LISTADO DE BEBIDAS ===");
        if (bebidas.isEmpty()) {
            System.out.println("No hay bebidas registradas.");
            return;
        }
        for (Bebida b : bebidas) {
            System.out.println(b.toString());
        }
    }
}