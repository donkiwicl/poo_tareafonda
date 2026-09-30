package cl.dsy1102.fonda;

public class BebidaAlcoholica extends Bebida implements ConsumoResponsable {
    private static final int LIMITE_UNIDADES_POR_CLIENTE = 3;
    private double gradosAlcohol;
    private boolean certificada;
    private boolean ventaRestringida;


    public BebidaAlcoholica(String nombre, int volumenML, int stock, double gradosAlcohol, boolean certificada, boolean ventaRestringida) {
        super(nombre, volumenML, stock);
        this.gradosAlcohol = gradosAlcohol;
        this.certificada = certificada;
        this.ventaRestringida = ventaRestringida;

    }

    public static int getLimiteUnidadesPorCliente() {
        return LIMITE_UNIDADES_POR_CLIENTE;
    }

    public double getGradosAlcohol() {

        return gradosAlcohol;
    }

    public void setGradosAlcohol(double gradosAlcohol) {
        if (gradosAlcohol < 0.5 || gradosAlcohol > 45)
            throw new IllegalArgumentException("El grado de alchol debe estar entre 0,5 y 45 grados.");
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


    @Override

    public double calcularPrecio() {
        double precioBase = 3500.0;
        if (!certificada) {
            return precioBase * 1.20;
        }
        return precioBase;
    }


    @Override

    public String obtenerDetalle() {
        return "Tipo: Bebida Alcoholica | Nombre: " + getNombre() +
                " | Volumen: " + getVolumenML() + " ml | Stock: " + getStock() +
                " | Grados: " + gradosAlcohol + " | Certificada: " + (certificada ? "Si" : "No") +
                "\n  Venta restringida: " + (ventaRestringida ? "Si" : "No") +
                " | Precio: $" + (int) calcularPrecio();
    }

    @Override

    public boolean tieneVentaRestringida(){
        return ventaRestringida;
    }

    @Override

    public void restringirVenta(){
        ventaRestringida=true;
    }

    @Override

    public boolean superaLimite(int unidades){
        return unidades >  LIMITE_UNIDADES_POR_CLIENTE;
    }
}
