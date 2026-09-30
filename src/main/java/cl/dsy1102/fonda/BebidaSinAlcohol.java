package cl.dsy1102.fonda;

public class BebidaSinAlcohol extends Bebida {
    private int azucarPorLitro;

    public BebidaSinAlcohol(String nombre,int volumenML,int stock,int azucarPorLitro){
        super(nombre,volumenML,stock);
        this.azucarPorLitro=azucarPorLitro;
    }

    public int getAzucarPorLitro(){
        return azucarPorLitro;
    }
    public void setAzucarPorLitro(int azucarPorLitro){
        if (azucarPorLitro < 0) {
            throw new IllegalArgumentException("La cantidad de azúcar no puede ser negativa.");
        }
        this.azucarPorLitro=azucarPorLitro;
    }

    @Override

    public double calcularPrecio() {
        double precioBase = 2000.0;
        if (azucarPorLitro > 80.0) {
            precioBase *= 1.10;
        }
        return precioBase;
    }


    @Override
    public String obtenerDetalle() {
        return "Tipo: Bebida Sin Alcohol | Nombre: " + getNombre() +
                " | Volumen: " + getVolumenML() + " ml | Stock: " + getStock() +
                " | Azucar: " + (int) azucarPorLitro + " g/L | Precio: $" + (int) calcularPrecio();
    }
}
