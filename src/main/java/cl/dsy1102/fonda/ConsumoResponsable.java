package cl.dsy1102.fonda;

public interface ConsumoResponsable{
    boolean tieneVentaRest();

    void restringirVenta();

    boolean superaLimVent(int unidades);
}
