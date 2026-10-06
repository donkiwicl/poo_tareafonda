package org.example;

public class BebidaSinAlcohol extends Bebida {
    private int azucar; // gramos por litro

    public BebidaSinAlcohol(String nombre, int volumen, int stock, int azucar) {
        super(nombre, volumen, stock);
        this.azucar = azucar;
    }

    @Override
    public int calcularPrecio() {
        int precioBase = 2000;
        if (azucar > 80) {
            precioBase += (int) (precioBase * 0.10); // Recargo del 10%
        }
        return precioBase;
    }

    @Override
    public String obtenerFicha() {
        return "Tipo: Bebida Sin Alcohol | Nombre: " + getNombre() + " | Volumen: " + getVolumen() + " ml | Stock: " + getStock() +
                " | Azucar: " + azucar + " g/L | Precio: $" + calcularPrecio();
    }
}