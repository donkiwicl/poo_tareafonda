package cl.dsy1102.fonda;

public class BebidaAlcoholica extends Bebida
        implements ConsumoResponsable {

    public static final int LIMITE_UNIDADES_POR_CLIENTE = 3;

    private double gradosAlcohol;
    private boolean certificada;
    private boolean ventaRestringida;

    public BebidaAlcoholica(
            String nombre,
            int volumenML,
            int stock,
            double gradosAlcohol,
            boolean certificada,
            boolean ventaRestringida) {

        super(nombre, volumenML, stock);

        setGradosAlcohol(gradosAlcohol);
        setCertificada(certificada);
        setVentaRestringida(ventaRestringida);
    }

    public double getGradosAlcohol() {
        return gradosAlcohol;
    }

    public void setGradosAlcohol(double gradosAlcohol) {
        if (gradosAlcohol < 0.5 || gradosAlcohol > 45) {
            throw new IllegalArgumentException(
                    "Los grados de alcohol deben estar entre 0.5 y 45."
            );
        }

        this.gradosAlcohol = gradosAlcohol;
    }

    public boolean isCertificada() {
        return certificada;
    }

    public void setCertificada(boolean certificada) {
        this.certificada = certificada;
    }

    public boolean tieneVentaRestringida() {
        return ventaRestringida;
    }

    public void setVentaRestringida(boolean ventaRestringida) {
        this.ventaRestringida = ventaRestringida;
    }

    @Override
    public double calcularPrecio() {

        double precio = 3500;

        if (!certificada) {
            precio *= 1.20;
        }

        return precio;
    }

    @Override
    public String obtenerDetalle() {

        return "Tipo: Bebida Alcoholica" +
                " | Nombre: " + getNombre() +
                " | Volumen: " + getVolumenML() + " ml" +
                " | Stock: " + getStock() +
                " | Grados: " + gradosAlcohol +
                " | Certificada: " + (certificada ? "Si" : "No") +
                " | Venta restringida: " +
                (ventaRestringida ? "Si" : "No") +
                " | Precio: $" + (int) calcularPrecio();
    }

    @Override
    public void restringirVenta() {
        ventaRestringida = true;
    }

    @Override
    public boolean superaLimite(int unidades) {
        return unidades > LIMITE_UNIDADES_POR_CLIENTE;
    }
}