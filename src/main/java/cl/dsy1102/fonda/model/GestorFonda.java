package cl.dsy1102.fonda.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Administra la coleccion de bebidas y aplica las reglas de venta.
 *
 * Las operaciones de EA1 informan por consola. La aplicacion grafica usa
 * directamente {@link #venderBebida(Bebida, int)}, que retorna el mensaje
 * o lanza una excepcion en vez de imprimir.
 */
public class GestorFonda {

    private final List<Bebida> bebidas;

    public GestorFonda() {
        this(new ArrayList<>());
    }

    public GestorFonda(List<Bebida> bebidas) {
        this.bebidas = bebidas;
    }

    public void registrar(Bebida bebida) {
        bebidas.add(bebida);
        System.out.println(bebida.getNombre() + " (" + bebida.getClass().getSimpleName() + ") registrada correctamente.");
    }

    public List<Bebida> buscarPorNombre(String nombre) {
        List<Bebida> encontradas = new ArrayList<>();
        for (Bebida bebida : bebidas) {
            if (bebida.getNombre().equalsIgnoreCase(nombre)) {
                encontradas.add(bebida);
            }
        }
        return encontradas;
    }

    public void vender(String nombre, int unidades) {
        List<Bebida> encontradas = buscarPorNombre(nombre);
        if (encontradas.isEmpty()) {
            System.out.println("Venta rechazada: no existe la bebida " + nombre + ".");
            return;
        }
        try {
            System.out.println(venderBebida(encontradas.get(0), unidades));
        } catch (VentaRechazadaException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Valida la venta y retorna el mensaje de venta autorizada con el total.
     * El control de consumo se resuelve por la interfaz, no por el tipo concreto.
     */
    public String venderBebida(Bebida bebida, int unidades) throws VentaRechazadaException {
        if (unidades <= 0) {
            throw new VentaRechazadaException("Venta rechazada: las unidades deben ser mayores que cero.");
        }
        if (bebida instanceof ConsumoResponsable controlada) {
            if (controlada.tieneVentaRestringida()) {
                throw new VentaRechazadaException("Venta rechazada: " + bebida.getNombre() + " tiene la venta restringida.");
            }
            if (controlada.superaLimite(unidades)) {
                throw new VentaRechazadaException("Venta rechazada: " + unidades + " unidades de " + bebida.getNombre()
                        + " superan el limite de " + BebidaAlcoholica.LIMITE_UNIDADES_POR_CLIENTE + " por cliente.");
            }
        }
        double total = bebida.calcularPrecio() * unidades;
        return "Venta autorizada: " + unidades + " x " + bebida.getNombre() + " | Total: $" + String.format("%.0f", total);
    }

    public List<Bebida> obtenerTodas() {
        return new ArrayList<>(bebidas);
    }
}
