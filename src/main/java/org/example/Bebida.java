package org.example;

public abstract class Bebida {
    private String  nombre;
    private int     volumenML;
    private int     stock;

    public Bebida(String nombre, int volumenML, int stock) {
        this.nombre = nombre;
        this.volumenML = volumenML;
        this.stock = stock;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVolumenML(){

        return volumenML;
    }

    public int getStock(){
        return stock;
    }

    public void setNombre(String nombre) throws IllegalArgumentException {
        if (nombre == null || getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre no puede quedar nulo o vacio");
        }else{
            this.nombre = nombre;
        }
    }



    public void setVolumenML(int volumenML)throws IllegalArgumentException{
        if (100 < volumenML && volumenML< 3000 ){
            this.volumenML = volumenML;
        }
        throw new IllegalArgumentException("Los ML ingresados tiene que estar entre 100 y 3000");

    }

    public void setStock(int stock) throws IllegalArgumentException{
        if(stock > 0)
            this.stock = stock;
        else{
            throw new IllegalArgumentException("El valor ingresado tiene que ser mayor a 0");
        }
    }

    //metodos

    public abstract double calcularPrecio();
    public abstract String obtenerDetalle();

    @Override
    public String toString() {
        return "--- Detalle del Producto ---\n" +
                "Nombre: " + nombre + "\n" +
                "Volumen: " + volumenML + " ml\n" +
                "Stock disponible: " + stock;
    }
}
