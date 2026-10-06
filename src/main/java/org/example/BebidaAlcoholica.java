package org.example;

public class BebidaAlcoholica extends Bebida implements ConsumoResponsable {
    private double gradosAlcohol;
    private boolean certificada;
    private boolean ventaRestringida; // por defecto en Java los boolean inician en false

    public BebidaAlcoholica(String nombre, int volumen, int stock, double gradosAlcohol, boolean certificada) {
        super(nombre, volumen, stock); // Llama al constructor del padre
        setGradosAlcohol(gradosAlcohol);
        this.certificada = certificada;
    }

    public void setGradosAlcohol(double gradosAlcohol) {
        if (gradosAlcohol < 0.5 || gradosAlcohol > 45) {
            throw new IllegalArgumentException("Los grados de alcohol deben estar entre 0.5 y 45.");
        }
        this.gradosAlcohol = gradosAlcohol;
    }

    // --- CONTRATO ConsumoResponsable ---
    @Override
    public boolean isVentaRestringida() {
        return this.ventaRestringida;
    }

    @Override
    public void setVentaRestringida(boolean restringida) {
        this.ventaRestringida = restringida;
    }

    @Override
    public boolean superaLimite(int cantidad) {
        return cantidad > MAX_UNIDADES_POR_CLIENTE;
    }

    // --- SOBRESCRITURA DE MÉTODOS ABSTRACTOS ---
    @Override
    public int calcularPrecio() {
        int precioBase = 3500;
        if (!certificada) {
            precioBase += (int) (precioBase * 0.20); // Recargo del 20%
        }
        return precioBase;
    }

    @Override
    public String obtenerFicha() {
        return "Tipo: Bebida Alcoholica | Nombre: " + getNombre() + " | Volumen: " + getVolumen() + " ml | Stock: " + getStock() +
                " | Grados: " + gradosAlcohol + " | Certificada: " + (certificada ? "Si" : "No") +
                "\n  Venta restringida: " + (ventaRestringida ? "Si" : "No") + " | Precio: $" + calcularPrecio();
    }
}