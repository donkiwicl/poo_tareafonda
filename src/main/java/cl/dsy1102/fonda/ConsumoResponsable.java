package cl.dsy1102.fonda;

public interface ConsumoResponsable {

    public static final int LIMITE_UNIDADES_POR_CLIENTE = 3;

    boolean tieneVentaRestringida();
    void restringirVenta();
    boolean superaLimite(int unidades);

}

