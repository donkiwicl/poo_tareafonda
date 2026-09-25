package cl.dsy1102.fonda;

public interface ConsumoResponsable {

    boolean tieneVentaRestringida();

    void restringirVenta();

    boolean superaLimite(int unidades);
}
