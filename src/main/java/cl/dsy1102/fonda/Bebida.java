package cl.dsy1102.fonda;
// Clase
public abstract class Bebida {
    //Atributo de Identificacion
    protected String nombre;
    protected int volumenMl;
    //Atributos de Estado
    protected int stock;
//Constructor
    public Bebida(String nombre, int volumenMl, int stock) {
        this.nombre = nombre;
        this.volumenMl = volumenMl;
        this.stock = stock;
    }
//Getter y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws  IllegalArgumentException {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        this.nombre = nombre;
    }

    public int getVolumenMl() {
        return volumenMl;
    }

    public void setVolumenMl(int volumenMl) throws  IllegalArgumentException {
        if (volumenMl < 100 || volumenMl > 3000) {
            throw new IllegalArgumentException("El volumen debe estar entre 100 y 3000 ml");
        }
        this.volumenMl = volumenMl;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) throws  IllegalArgumentException {
        if (stock <= 0) {
            throw new IllegalArgumentException("El stock debe ser mayor que cero");
        }
        this.stock = stock;
    }

    public abstract double calcularPrecio();

    public abstract String obtenerDetalle();


    public String toString() {
        return "Nombre: " + nombre + " | Volumen: " + volumenMl + " ml";
    }
}
