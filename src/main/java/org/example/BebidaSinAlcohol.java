package org.example;

public class BebidaSinAlcohol extends Bebida {
    private int azucarPorLitro;

    public BebidaSinAlcohol(String nombre, int volumenML, int stock, int azucar){
        super(nombre, volumenML, stock);
        this.azucarPorLitro = azucarPorLitro;
    }

    // Setter y Getters

    public int getAzucar() {
        return azucarPorLitro;
    }

    public void setAzucar(int azucarPorLitro) {
        this.azucarPorLitro = azucarPorLitro;
    }

    //Overrides de metodos abstractos

    @Override
    public double calcularPrecio() {
        double precioBase = 2000;
        if (getAzucar() <= 80) return precioBase;
        else return precioBase * 1.10;

    }

    @Override
    public String obtenerDetalle(){
        return "Tipo: Bebida Alcoholica | Nombre: " + getNombre()
                + "| Volumen: " + getVolumenML()
                + "| Stock: " + getStock()
                + "| Azucar" + getAzucar() + " g/L "
                + "| Precio: " + calcularPrecio();
    }





}
