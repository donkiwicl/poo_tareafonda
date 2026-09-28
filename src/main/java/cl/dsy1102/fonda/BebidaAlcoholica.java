package cl.dsy1102.fonda;

public class BebidaAlcoholica extends Bebida implements ConsumoResponsable {

    public static final int LIMITE_UNIDADES_POR_CLIENTE = 3;

    private double gradosAlcohol;
    private boolean certificada;
    private boolean ventaRestringida;

    public BebidaAlcoholica(String nombre, int volumenML, int stock, double gradosAlcohol, boolean certificada) {
        super(nombre, volumenML, stock);
        this.gradosAlcohol = gradosAlcohol;
        this.certificada = certificada;
    }

    public double getGradosAlcohol() {
        return gradosAlcohol;
    }

    public void setGradosAlcohol(double gradosAlcohol) throws IllegalArgumentException {
        if (gradosAlcohol < 0.5) {
            throw new IllegalArgumentException("Grado de alcohol menor a 0.5");
        } else if (gradosAlcohol > 45.0) {
            throw new IllegalArgumentException("Grado de alcohol menor a 45");
        } else {
            this.gradosAlcohol = gradosAlcohol;
        }
    }

    public boolean isCertificada() {
        return certificada;
    }

    public void setCertificada(boolean certificada) {
        this.certificada = certificada;
    }

    @Override
    public double calcularPrecio() {
        return 3500.0 * (this.isCertificada() ? 1.0 : 1.2);
    }

    @Override
    public String obtenerDetalle() {
        return "Nombre: " + this.getNombre() +
                " | Volumen: " + this.getVolumenML() + " mL" +
                " | Stock: " + this.getStock() +
                " | Grados Alcohol: " + this.getGradosAlcohol() +
                " | Certificada: " + (this.isCertificada() ? "Sí" : "No") +
                " | Venta restringida: " + (this.tieneVentaRestringida() ? "Sí" : "No") +
                " | Precio: $" + this.calcularPrecio();
    }

    @Override
    public boolean tieneVentaRestringida() {
        return this.ventaRestringida;
    }

    @Override
    public void restringirVenta() {
        this.ventaRestringida = true;
    }

    @Override
    public boolean superaLimite(int unidades) {
        if (unidades > BebidaAlcoholica.LIMITE_UNIDADES_POR_CLIENTE) {
            return true;
        } else {
            return false;
        }
    }

}
