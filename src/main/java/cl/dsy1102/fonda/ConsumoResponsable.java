package cl.dsy1102.fonda;

public interface ConsumoResponsable {
    int maximoUnidades=3;
    boolean tieneVentaRestringida();
    void restringirVenta();
    boolean superaLimite(int unidades);
}
