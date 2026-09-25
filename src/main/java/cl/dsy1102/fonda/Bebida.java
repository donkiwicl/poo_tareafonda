package cl.dsy1102.fonda;

public abstract class Bebida {

    private String nombre;
    private int volumenML;
    private int stock;

    public Bebida(String nombre, int volumenML, int stock){
        this.nombre=nombre;
        this.volumenML=volumenML;
        this.stock=stock;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre==null || nombre.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre no debe estar vacio");
        }
        this.nombre = nombre;
    }

    public int getVolumenML() {
        return volumenML;
    }

    public void setVolumenML(int volumenML) {
        if(volumenML<100 || volumenML>3000){
            throw new IllegalArgumentException("El volumen debe estar entre 100 y 3000 ML");
        }

        this.volumenML = volumenML;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public abstract double calcularPrecio();
    public abstract String obtenerDetalle();


    @Override
    public String toString(){
        return "Nombre: "+nombre+
                "Volumen "+volumenML+ " ML";
    }

}
