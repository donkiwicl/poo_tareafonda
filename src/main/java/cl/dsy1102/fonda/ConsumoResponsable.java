package cl.dsy1102.fonda;

public interface ConsumoResponsable{
    static boolean tieneVentaRest(){
        return false;
    }
    static void restringirVenta(){}

    static boolean superaLimVent(){
        return false;
    }
}
