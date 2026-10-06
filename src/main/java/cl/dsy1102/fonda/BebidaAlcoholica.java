package cl.dsy1102.fonda;

public class BebidaAlcoholica extends Bebida implements ConsumoResponsable {
    public static final int LIMITE_UNIDADES_POR_CLIENTE = 3;

    private double gradosAlcohol;
    private boolean certificada;
    private boolean ventaRestringida;

    //Constructor
    public BebidaAlcoholica(String nombre, int volumenMl, int stock, double gradosAlcohol, boolean certificada, boolean ventaRestringida) {
        super(nombre, volumenMl, stock);
        this.gradosAlcohol = gradosAlcohol;
        this.certificada = certificada;
        this.ventaRestringida = ventaRestringida;
    }


    //Getters y Setters
    public double getGradosAlcohol() {
        return gradosAlcohol;
    }

    public void setGradosAlcohol(double gradosAlcohol) throws IllegalArgumentException {
        if (getGradosAlcohol() < 0.5 || getGradosAlcohol() > 45) {
            throw new IllegalArgumentException("La bebida alcohólica no puede tener menos que 0.5 grados ni más de 45 grados de alcohol.");
        }
        this.gradosAlcohol = gradosAlcohol;
    }

    public boolean isCertificada() {
        return certificada;
    }

    public void setCertificada(boolean certificada) {
        this.certificada = certificada;
    }

    public boolean isVentaRestringida() {
        return ventaRestringida;
    }

    public void setVentaRestringida(boolean ventaRestringida) {
        this.ventaRestringida = ventaRestringida;
    }

    //Métodos heredados de clase abstracta
    @Override
    public double calcularPrecio() {
        double precioBase = 3500;
        if (isCertificada()) {
            return precioBase;
        } else {
            return precioBase * 1.20;
        }
    }

    @Override
    public String obtenerDetalle() {
        return "Tipo: Bebida Alcohólica | " +
                "Nombre: " + getNombre() + " | " +
                "Volumen: " + getVolumenMl() + " ml. | " +
                "Stock: " + getStock() + " | " +
                "Grados Alcohol: " + getGradosAlcohol() + " g/L | " +
                "Certificado: " + isCertificada() + " | " +
                "Venta Restringida: " + isVentaRestringida() + " | " +
                "Precio: $" + (int) calcularPrecio();
    }

    //Métodos de interfaz
    @Override
    public boolean tieneVentaRestringida() {
        if (isVentaRestringida()) {
            return isVentaRestringida();
        } else {
            return false;
        }
    }

    @Override
    public void restringirVenta() {
        setVentaRestringida(true);
    }

    @Override
    public boolean superaLimite(int unidades) {
        if (unidades > LIMITE_UNIDADES_POR_CLIENTE) {
            return true;}
        else{
            return false;}
    }
}
