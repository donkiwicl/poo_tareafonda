package cl.dsy1102.fonda;

public abstract class Bebida {
    private String nombre;
    private int volumenML;
    private int stock;

    public Bebida(String nombre, int volumenML, int stock) {
        setNombre(nombre);
        setVolumenML(volumenML);
        setStock(stock);
    }


    public abstract Double calcularPrecio ();
    public abstract String obtenerDetalle();

    @Override
    public String toString() {
        return nombre + " | Volumen: " + volumenML + " ml";
    }

    //Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()){
            throw new IllegalArgumentException("No puede ser nulo ni vacio");
        }
        this.nombre = nombre;
    }

    public int getVolumenML() {
        return volumenML;
    }

    public void setVolumenML(int volumenML) {
        if(volumenML < 100 || volumenML > 3000) {
            throw new IllegalArgumentException("Debe encontrarse en el rango entre 100 y 3000 mililitros.");
        }
        this.volumenML = volumenML;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock <= 0){
            throw new IllegalArgumentException("Debe ser un valor mayor que cero");
        }
        this.stock = stock;
    }
}
