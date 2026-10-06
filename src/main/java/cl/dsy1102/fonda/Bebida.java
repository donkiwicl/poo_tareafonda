package cl.dsy1102.fonda;

//Creación de clase abstracta
public abstract class Bebida {
    protected String nombre;
    protected int volumenMl;
    protected int stock;

    //Constructor
    public Bebida(String nombre, int volumenMl, int stock) {
        this.nombre = nombre;
        this.volumenMl = volumenMl;
        this.stock = stock;
    }

    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws IllegalArgumentException {
        if (nombre == null || nombre.isEmpty()){
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public int getVolumenMl() {
        return volumenMl;
    }

    public void setVolumenMl(int volumenMl) throws IllegalArgumentException {
        if (getVolumenMl() < 100 || getVolumenMl() > 3000) {
            throw new IllegalArgumentException("El volumen de la bebida no puede ser menor a 100 ml ni mayor a 3000 ml.");
        }
            this.volumenMl = volumenMl;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) throws IllegalArgumentException{
            if (stock < 0) {
                throw new IllegalArgumentException("El stock no puede ser menor que 0.");
            }
        this.stock = stock;
    }

    //Métodos Abstractos
    public abstract double calcularPrecio();

    public abstract String obtenerDetalle();


    //To String:
    public String toString() {
        return "Nombre: " + getNombre() + " | Volumen: " + getVolumenMl() + " ml";
    }
}
