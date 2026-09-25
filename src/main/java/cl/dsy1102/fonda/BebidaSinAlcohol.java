package cl.dsy1102.fonda;

public class BebidaSinAlcohol extends Bebida {
    private int azucarXLitro;

    public BebidaSinAlcohol(String nombre, int volumenML, int stock, int azucarXLitro) {
        super(nombre, volumenML, stock);
        this.azucarXLitro = azucarXLitro;
    }

    public int getAzucarXLitro() {
        return azucarXLitro;
    }

    public void setAzucarXLitro(int azucarXLitro) {
        this.azucarXLitro = azucarXLitro;
    }

    @Override
    public double calcularPrecio() {
        double precio = 2000;
        if (azucarXLitro > 80) {
            precio =  precio * 1.1;
            return precio;
        }else{
            return precio;
        }
    }

    @Override
    public String toString() {
        String respuesta = super.toString();

        return respuesta;
    }
}
