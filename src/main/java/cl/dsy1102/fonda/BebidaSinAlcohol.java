package cl.dsy1102.fonda;

public class BebidaSinAlcohol extends Bebida {

    private int azucarPorLitro;

    public BebidaSinAlcohol(
            String nombre,
            int volumenML,
            int stock,
            int azucarPorLitro) {

        super(nombre, volumenML, stock);

        setAzucarPorLitro(azucarPorLitro);
    }

    public int getAzucarPorLitro() {
        return azucarPorLitro;
    }

    public void setAzucarPorLitro(int azucarPorLitro) {
        this.azucarPorLitro = azucarPorLitro;
    }

    @Override
    public double calcularPrecio() {

        double precio = 2000;

        if (azucarPorLitro > 80) {
            precio *= 1.10;
        }

        return precio;
    }

    @Override
    public String obtenerDetalle() {

        return "Tipo: Bebida Sin Alcohol" +
                " | Nombre: " + getNombre() +
                " | Volumen: " + getVolumenML() + " ml" +
                " | Stock: " + getStock() +
                " | Azucar: " + azucarPorLitro + " g/L" +
                " | Precio: $" + (int) calcularPrecio();
    }
}