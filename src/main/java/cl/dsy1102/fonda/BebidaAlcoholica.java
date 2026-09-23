package cl.dsy1102.fonda;

public class BebidaAlcoholica extends Bebida implements ConsumoResponsable {
    public static final int LIMITE_UNIDADES_POR_CLIENTE = 3;
    private static final double PRECIO_BASE = 3500.0;
    private static final double RECARGO_SIN_CERTIFICACION = 1.20;
    private double gradosAlcohol;
    private boolean certificada;
    private boolean ventaRestringida;

    public BebidaAlcoholica(String nombre, int volumenML, int stock, double gradosAlcohol, boolean certificada) {
        super(nombre, volumenML, stock);
        setGradosAlcohol(gradosAlcohol);
        setCertificada(certificada);
    }

    public double getGradosAlcohol() {
        return gradosAlcohol;
    }

    public void setGradosAlcohol(double gradosAlcohol) {
        if (gradosAlcohol < 0.5 || gradosAlcohol > 45) {
            throw new IllegalArgumentException("Los grados de alcohol deben estar entre 0.5 y 45.");
        }
        this.gradosAlcohol = gradosAlcohol;
    }

    public boolean isCertificada() {
        return certificada;
    }

    public void setCertificada(boolean certificada) {
        this.certificada = certificada;
    }

    @Override
    public double calcularPrecio() {
        double precio = PRECIO_BASE;
        if (!isCertificada()) {
            precio *= RECARGO_SIN_CERTIFICACION;
        }
        return precio;
    }

    @Override
    public String obtenerDetalle() {
        return "Tipo: Bebida Alcoholica | Nombre: " + getNombre()
                + " | Volumen: " + getVolumenML() + " ml"
                + " | Stock: " + getStock()
                + " | Grados: " + getGradosAlcohol()
                + " | Certificada: " + (isCertificada() ? "Si" : "No")
                + "\n  Venta restringida: " + (tieneVentaRestringida() ? "Si" : "No")
                + " | Precio: $" + (int) calcularPrecio();
    }

    @Override
    public boolean tieneVentaRestringida() {
        return ventaRestringida;
    }

    @Override
    public void restringirVenta() {
        this.ventaRestringida = true;
    }

    @Override
    public boolean superaLimite(int unidades) {
        return unidades > LIMITE_UNIDADES_POR_CLIENTE;
    }
}