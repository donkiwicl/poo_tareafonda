package cl.dsy1102.fonda;

public abstract class Bebida {
    private String nombre;
    private int volumenMl;
    private int stock;

    public Bebida(String nombre, int volumenMl, int stock) {
        this.nombre = nombre;
        this.volumenMl = volumenMl;
        this.stock = stock;

        //Falta aca poner todas las validaciones
        // nombre	No puede ser nulo ni vacío.
        // volumenDebe encontrarse en el rango entre 100 y 3.000 mililitros.
        //stock	Debe ser un valor mayor que cero.


    }


    public abstract Double calcularPrecio ();
    public String obtenerDetalle(){
        return nombre +"\n"+volumenMl+"Ml\n"+"Stock: " + stock;
    }

    @Override
    public String toString() {
        return nombre + volumenMl;
    }

    //Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVolumenMl() {
        return volumenMl;
    }

    public void setVolumenMl(int volumenMl) {
        this.volumenMl = volumenMl;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
