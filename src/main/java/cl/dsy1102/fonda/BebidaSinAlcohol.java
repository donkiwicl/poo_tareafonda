package cl.dsy1102.fonda;

public class BebidaSinAlcohol extends Bebida {
    private int azucarPorLitro;

    public BebidaSinAlcohol(String nombre, int volumenMl, int stock, int azucarPorLitro) {
        super(nombre, volumenMl, stock);
        this.azucarPorLitro = azucarPorLitro;
    }

    @Override
    public Double calcularPrecio() {
        if (azucarPorLitro <= 80){
            return 2000.0;
        } else {
          return 2200.0;
        }
    }

    @Override
    public String obtenerDetalle() {
        return super.obtenerDetalle() + "azucar * L : " + azucarPorLitro;
    }


    //getter y setters
    public int getAzucarPorLitro() {
        return azucarPorLitro;
    }

    public void setAzucarPorLitro(int azucarPorLitro) {
        this.azucarPorLitro = azucarPorLitro;
    }
}
