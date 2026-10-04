package org.example;

public class BebidaAlcoholica extends Bebida implements ConsumoResponsable{
    public static final int LimUniclientes = 3;
    private double gradosAlcohol;
    private boolean certificada;
    private boolean ventaRestringida;



    // Constructor
    public BebidaAlcoholica (String nombre, int volumenML, int stock, double gradosAlcohol,
                             boolean certificada) {
        super(nombre, volumenML, stock);
        this.gradosAlcohol = gradosAlcohol;
        this.certificada = certificada;
        this.ventaRestringida = false;
    }
    //Setters y Getters
    public double getGradosAlcohol() {
        return gradosAlcohol;
    }

    public void setGradosAlcohol(double gradosAlcohol) throws IllegalArgumentException {
        if (gradosAlcohol > 45 || gradosAlcohol < 0.5) {
            throw new IllegalArgumentException("Error, solo puede ingresar valores del grado de alcohol entre 0.5° y 45°");
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

    //Overrides de abs

    @Override
    public double calcularPrecio() {
        double precioBase = 3500;
        if (this.certificada) return precioBase;
        else return precioBase * 1.20;
    }
    @Override
    public String obtenerDetalle() {
        String textoCertificada;
        if (isCertificada() == true) {
            textoCertificada = "Si";
        } else {
            textoCertificada = "No";
        }

        String textoVentaRestringida;
        if (tieneVentaRestringida() == true) {
            textoVentaRestringida = "Si";
        } else {
            textoVentaRestringida = "No";
        }
        return "Tipo: Bebida Alcoholica | Nombre: " + getNombre()
                + "| Volumen: " + getVolumenML()
                + "| Stock: " + getStock()
                + "| Grados: " + getGradosAlcohol()
                + "| Certificada: " + textoCertificada
                + "| Venta Restringida: " + textoVentaRestringida
                + "| Precio: " + calcularPrecio();
    }

    //Overrides de callback

    @Override
    public boolean tieneVentaRestringida() {
        return ventaRestringida;
    }



    @Override
    public void restringirVenta() {
        setVentaRestringida(true);
    }

    @Override
    public boolean superaLimite(int unidades) {
        return unidades > LimUniclientes;
    }
}

