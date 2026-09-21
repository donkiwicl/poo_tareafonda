package cl.dsy1102.fonda;

public class BebidaAlcoholica extends Bebida implements ConsumoResponsable {
    private int Limite;
    private double gradosAlcohol;
    private boolean certificada;
    private boolean ventaRestringida;

    public BebidaAlcoholica(String nombre, int volumenML, int stock, int limite, double gradosAlcohol, boolean certificada) {
        super(nombre, volumenML, stock);
        this.Limite = limite;
        this.gradosAlcohol = gradosAlcohol;
        this.certificada = certificada;
        this.ventaRestringida = false;
    }

    public int getLimite() {
        return Limite;
    }

    public void setLimite(int limite) {
        Limite = limite;
    }

    public double getGradosAlcohol() {
        return gradosAlcohol;
    }

    public void setGradosAlcohol(double gradosAlcohol) {
        this.gradosAlcohol = gradosAlcohol;
    }

    public boolean isCertificada() {
        return certificada;
    }

    public void setCertificada(boolean certificada) {
        this.certificada = certificada;
    }

    public void RestringirVenta(){
        this.ventaRestringida = true;
    }

    public int superaLimite(int unidades){
        return unidades;
    }

    @Override
    public double calcularPrecio() {
        return super.calcularPrecio();
    }

    @Override
    public String toString() {
        String respuesta = super.toString();
        respuesta = respuesta + "\nLimite: " + Limite;
        return  respuesta;
    }
}
