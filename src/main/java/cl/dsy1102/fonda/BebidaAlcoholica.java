package cl.dsy1102.fonda;

public class BebidaAlcoholica extends Bebida implements ConsumoResponsable{
    public static final int LIMITE_UNIDADES_POR_CLIENTE = 3;
    private double gradosAlcohol;
    private boolean certificada;
    private boolean ventaRestringida = false;

    public BebidaAlcoholica(String nombre, int volumenMl, int stock, double gradosAlcohol, boolean certificada) {
        super(nombre, volumenMl, stock);
        setGradosAlcohol(gradosAlcohol);
        setCertificada(certificada);
    }


    //Padre
    @Override
    public Double calcularPrecio() {
        if (isCertificada()){
            return 3500.0;
        }else {
            return 3500.0 * 1.2;
        }
    }

    @Override
    public String obtenerDetalle() {
        return "Tipo: Bebida Alcoholica | Nombre: " + getNombre()
                + " | Volumen: " + getVolumenML()+ " ml"
                + " | Stock: " + getStock()
                + " | Grados: " + gradosAlcohol
                + " | Certificada: " + (certificada ? "Si" : "No")
                + "\n  Venta restringida: " + (ventaRestringida ? "Si" : "No")
                + " | Precio: $" + String.format("%.0f", calcularPrecio());
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
        return unidades > LIMITE_UNIDADES_POR_CLIENTE;
    }

    //getters y setters
    public double getGradosAlcohol() {
        return gradosAlcohol;
    }

    public void setGradosAlcohol(double gradosAlcohol) {
        if(gradosAlcohol < 0.5 || gradosAlcohol >45){
            throw new IllegalArgumentException("Debe encontrarse en el rango entre 0.5 y 45");
        }
        this.gradosAlcohol = gradosAlcohol;
    }

    public boolean isCertificada(){
        return certificada;
    }
    public void setCertificada(boolean certificada) {
        this.certificada = certificada;
    }

}
