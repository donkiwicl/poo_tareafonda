package cl.dsy1102.fonda;

public class BebidaAlcoholica extends Bebida implements ConsumoResponsable {
    public static final int Limite_Unidad_X_Cliente = 3;
    private double gradosAlcohol;
    private boolean certificada;
    private boolean ventaRestringida =  false;

    public BebidaAlcoholica(String nombre, int volumenML, int stock, double gradosAlcohol, boolean certificada) {
        super(nombre, volumenML, stock);
        setGradosAlcohol(gradosAlcohol);
        setCertificada(certificada);
    }

    public double getGradosAlcohol() {
        return this.gradosAlcohol;
    }

    public void setGradosAlcohol(double gradosAlcohol) {
        if (gradosAlcohol < 0.5 || gradosAlcohol > 45) {
            throw new IllegalArgumentException("El grado de alcohol no puede quedar menos de 0.5 ni superar los 45 grados de alcohol." );
        }
        this.gradosAlcohol = gradosAlcohol;
    }

    public boolean isCertificada() {
        return this.certificada;
    }

    public void setCertificada(boolean certificada) {
        this.certificada = certificada;
    }

    public void RestringirVenta(){
        this.ventaRestringida = false;
    }

    @Override
    public boolean tieneVentaRest() {
        return this.ventaRestringida;
    }

    @Override
    public void restringirVenta() {
        this.ventaRestringida = true;
    }

    @Override
    public boolean superaLimVent(int unidades) {
        return unidades > Limite_Unidad_X_Cliente;
    }

    @Override
    public double calcularPrecio() {
        double precio = 3500;
        if (this.certificada) {
            return precio;
        } else {
            precio = precio * 1.2;
            return precio;
        }
    }

    @Override
    public String toString() {
        String respuesta = super.toString();
        respuesta +=         "\nGrados de Alcohol: " + this.gradosAlcohol;
        if (this.certificada) {
            respuesta +=         "\nCertificada: Certificado";
        }else{
            respuesta +=         "\nCertificada: No certificado";
        }
        if (ventaRestringida == true) {
            respuesta +=         "\nVenta restringida: Si";
        }else{
            respuesta +=          "\nVenta restringida: No";
        }
        respuesta += "\nPrecio: $" +  String.format("%.0f", this.calcularPrecio());
        return respuesta;
    }
}
