package cl.dsy1102.fonda.model;

/**
 * Se lanza cuando una venta no cumple las reglas de consumo responsable.
 */
public class VentaRechazadaException extends Exception {

    public VentaRechazadaException(String mensaje) {
        super(mensaje);
    }
}
