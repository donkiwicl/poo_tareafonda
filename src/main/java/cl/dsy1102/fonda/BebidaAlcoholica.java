package cl.dsy1102.fonda;

public class BebidaAlcoholica extends Bebida implements ConsumoResponsable{
    public static final int LIMITE_UNIDADES_POR_CLIENTE = 3;
    private double gradosAlcohol;
    private boolean certificada;
    private boolean ventaRestringida;

    public BebidaAlcoholica(String nombre, int volumenMl, int stock, double gradosAlcohol, boolean certificada) {
        super(nombre, volumenMl, stock);
        this.gradosAlcohol = gradosAlcohol;
        this.certificada = certificada;
        this.ventaRestringida = false;
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
        String textoSalida = super.obtenerDetalle() + "\nGrados: " + gradosAlcohol + "\n";
        if (certificada == true) {
            textoSalida += "Certificada";
        } else {
            textoSalida += "No certificada";
        }
        if (ventaRestringida == true){
            textoSalida += "\nVenta restringida";
        }
        return textoSalida;
        }
    //ok


    @Override
    public void venderBebidaActual(int unidades) {
        int venta = unidades;
        if (unidades<1){
            throw new VentaNoRealizada("ERROR: No se puede realizar una venta con numeros negativos");
        }
        if ((this.getStock() - unidades) < 0 ){
            throw new VentaNoRealizada("ERROR: No se puede realizar una venta con numeros negativos");
        }
        if (this.ventaRestringida){
            throw new VentaNoRealizada("ERROR la venta de este producto se encuentra restringida");
        }
        if (superaLimite(unidades)){
            throw new VentaNoRealizada("ERROR: Lo sentimos. Ha superado el limite de venta por cliente.");
        }
        this.setStock(this.getStock() - unidades);
        System.out.println("Venta realizada");
    }

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
        if (unidades <= LIMITE_UNIDADES_POR_CLIENTE){
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
