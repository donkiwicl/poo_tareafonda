package cl.dsy1102.fonda;

public class BebidaAlcoholica extends Bebida implements ConsumoResponsable{
    public static final int LIMITE_UNIDADES_POR_CLIENTE = 3;
    private double gradosAlcohol;
    private boolean certificada;
    private boolean ventaRestringida;

    public BebidaAlcoholica(String nombre, int volumenMl, int stock, double gradosAlcohol, boolean certificada, boolean ventaRestringida) {
        super(nombre, volumenMl, stock);
        this.gradosAlcohol = gradosAlcohol;
        this.certificada = certificada;
        this.ventaRestringida = ventaRestringida;
    }

    public boolean isCertificada(){
        return certificada;
    } //ok


    @Override
    public Double calcularPrecio() {
        if (isCertificada()){
            return 3500.0;
        }else {
            return 3500.0 * 1.2;
        }
    }//ok

    @Override
    public String obtenerDetalle() {
        String textoSalida = super.obtenerDetalle() + "Grados: " + gradosAlcohol + "\n";
        if (certificada == true) {
            return textoSalida + "Certificada";
        } else {
            return textoSalida + "No certificada";
        }
    }//ok


    //interfaz
    @Override
    public boolean tieneVentaRestringida() {
        return ventaRestringida;
    }

    @Override
    public void restringirVenta() {
        ventaRestringida = true;

    }

    @Override
    public boolean superaLimite(int unidades) {
        if (unidades > LIMITE_UNIDADES_POR_CLIENTE){
            return false;
        } else {
            return true;
        }

    }

    //getters y setters
    public double getGradosAlcohol() {
        return gradosAlcohol;
    }

    public void setGradosAlcohol(double gradosAlcohol) {
        if(0.5 < gradosAlcohol && gradosAlcohol < 45.0){
            this.gradosAlcohol = gradosAlcohol;
        } else {
            throw new IllegalArgumentException("Debe encontrarse en el rango entre 0,5 y 45.");
        }
    }

    public void setCertificada(boolean certificada) {
        this.certificada = certificada;
    }



}
