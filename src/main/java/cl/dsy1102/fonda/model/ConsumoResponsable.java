package cl.dsy1102.fonda.model;

/**
 * Contrato de los productos sujetos al control de consumo responsable.
 */
public interface ConsumoResponsable {

    boolean tieneVentaRestringida();

    void restringirVenta();

    boolean superaLimite(int unidades);
}
