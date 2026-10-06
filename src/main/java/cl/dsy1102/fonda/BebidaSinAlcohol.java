package cl.dsy1102.fonda;

public class BebidaSinAlcohol extends Bebida {

    private int azucarPorLitro;





    public BebidaSinAlcohol(String nombre, int volumenML, int stock, int azucarPorLitro) {
        super(nombre, volumenML, stock);
        this.setAzucarPorLitro(azucarPorLitro);
    }

    public BebidaSinAlcohol() {
        this("BebidaSinAlcohol", 500, 100, 100);
    }





    public int getAzucarPorLitro() {
        return azucarPorLitro;
    }

    public void setAzucarPorLitro(int azucarPorLitro) throws IllegalArgumentException {
        if (azucarPorLitro < 0) {
            throw new IllegalArgumentException("Azúcar por litro es menor a cero");
        } else {
            this.azucarPorLitro = azucarPorLitro;
        }
    }





    @Override
    public double calcularPrecio() {
        return 2000 * (this.getAzucarPorLitro() > 80 ? 1.1 : 1.0);
    }

    @Override
    public String obtenerDetalle() {
        return "Nombre: " + this.getNombre() +
                " | Volumen: " + this.getVolumenML() + " mL" +
                " | Stock: " + this.getStock() +
                " | Azúcar: " + this.getAzucarPorLitro() + " g/L" +
                " | Precio: $" + this.calcularPrecio();
    }

    @Override
    public String obtenerTipo() {
        return "BebidaSinAlcohol";
    }

}
