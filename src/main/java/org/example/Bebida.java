package org.example;

public abstract class Bebida {
    private String nombre;
    private int volumen;
    private int stock;

    public Bebida(String nombre, int volumen, int stock) {
        // Delegamos a los setters para aprovechar las validaciones
        setNombre(nombre);
        setVolumen(volumen);
        setStock(stock);
    }

    // --- SETTERS CON VALIDACIONES (Punto 4 de las instrucciones) ---
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío.");
        }
        this.nombre = nombre;
    }

    public void setVolumen(int volumen) {
        if (volumen < 100 || volumen > 3000) {
            throw new IllegalArgumentException("El volumen debe encontrarse en el rango entre 100 y 3.000 mililitros.");
        }
        this.volumen = volumen;
    }

    public void setStock(int stock) {
        if (stock <= 0) {
            throw new IllegalArgumentException("El stock debe ser un valor mayor que cero.");
        }
        this.stock = stock;
    }

    // --- GETTERS ---
    public String getNombre() { return nombre; }
    public int getVolumen() { return volumen; }
    public int getStock() { return stock; }

    // --- MÉTODOS ABSTRACTOS ---
    // Obligamos a las clases hijas a definir cómo calculan su precio y su ficha
    public abstract int calcularPrecio();
    public abstract String obtenerFicha();

    // --- toString RESUMIDO ---
    @Override
    public String toString() {
        return "Nombre: " + nombre + " | Volumen: " + volumen + " ml";
    }
}