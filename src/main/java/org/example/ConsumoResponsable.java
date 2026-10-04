package org.example;

public interface ConsumoResponsable {
    boolean tieneVentaRestringida();
    void restringirVenta();
    boolean superaLimite(int unidades);


}
