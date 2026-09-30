package cl.dsy1102.fonda;

public abstract class Bebida {
    protected String nombre;
    protected int volumenML;
    protected int stock;

    public Bebida(String nombre, int volumenML, int stock) {
        setNombre(nombre);
        setVolumenML(volumenML);
        setStock(stock);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        this.nombre = nombre;
    }

    public int getVolumenML() {
        return volumenML;
    }

    public void setVolumenML(int volumenML) {
        if (volumenML < 100 || volumenML > 3000) {
            throw new IllegalArgumentException("El volumen debe ser un valor entre 100 y 3000 ml");
        }
        this.volumenML = volumenML;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock <= 0) {
            throw new IllegalArgumentException("El stock debe ser mayor que 0");
        }
        this.stock = stock;
    }

    public double calcularPrecio(){
        return 0.0;
    }

    public String obtenerDetalle(){
        return toString();
    }

    public String toString(){
        String respuesta = "=============================";
        respuesta += "\nTipo: "+this.getClass().getSimpleName();
        respuesta +=         "\nNombre: " + this.nombre;
        respuesta +=         "\nVolumen: " + this.volumenML;
        respuesta +=         "\nStock: " + this.stock;
        return respuesta;
    }
}
