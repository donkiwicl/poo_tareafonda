package cl.dsy1102.fonda;

public abstract class Bebida {

    protected String nombre;
    protected int volumenML;
    protected int stock;





    public Bebida(String nombre, int volumenML, int stock) {
        this.setNombre(nombre);
        this.setVolumenML(volumenML);
        this.setStock(stock);
    }

    public Bebida() {
        this("Bebida", 500, 100);
    }





    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws IllegalArgumentException {
        if (nombre == null) {
            throw new IllegalArgumentException("Nombre es null");
        } else if (nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre está vacío");
        } else {
            this.nombre = nombre;
        }
    }

    public int getVolumenML() {
        return volumenML;
    }

    public void setVolumenML(int volumenML) throws IllegalArgumentException {
        if (volumenML < 100) {
            throw new IllegalArgumentException("Volumen menor a 100 mL");
        } else if (volumenML > 3000) {
            throw new IllegalArgumentException("Volumen es mayor a 3.000 mL");
        } else {
            this.volumenML = volumenML;
        }
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) throws IllegalArgumentException {
        if (stock <= 0) {
            throw new IllegalArgumentException("Stock es menor a cero");
        } else {
            this.stock = stock;
        }
    }





    public abstract double calcularPrecio();

    public abstract String obtenerDetalle();

    public abstract String obtenerTipo();





    @Override
    public String toString() {
        return "Nombre: " + this.nombre + " | Volumen: " + this.volumenML + " mL";
    }

}