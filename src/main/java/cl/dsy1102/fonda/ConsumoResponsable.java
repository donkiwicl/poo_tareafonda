package cl.dsy1102.fonda;

public interface ConsumoResponsable
{ public boolean tieneVentaRestringida();
    public void restringirVenta();
    public boolean superaLimite(int unidades);
}
