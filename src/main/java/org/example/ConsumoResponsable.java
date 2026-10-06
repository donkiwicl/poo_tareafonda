package org.example;

public interface ConsumoResponsable {
    // En las interfaces, los atributos son por defecto public static final (constantes)
    int MAX_UNIDADES_POR_CLIENTE = 3;

    boolean isVentaRestringida();
    void setVentaRestringida(boolean restringida); // o sin parámetros si solo la activa
    boolean superaLimite(int cantidad);
}