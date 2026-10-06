package cl.dsy1102.fonda;

public class BebidaSinAlcohol extends Bebida {
    private int azucarPorLitro;

    public BebidaSinAlcohol(String nombre, int volumenMl, int stock, int azucarPorLitro) {
        super(nombre, volumenMl, stock);
        setAzucarPorLitro(azucarPorLitro);
    }

    @Override
    public Double calcularPrecio() {
        if (azucarPorLitro > 80){
            return 2000*1.1;
        }
        return 2000.0;
    }

    @Override
    public String obtenerDetalle() {
        return "Tipo: Bebida Sin Alcohol | Nombre: " + getNombre()
                + " | Volumen: " + getVolumenML() + " ml"
                + " | Stock: " + getStock()
                + " | Azucar: " + azucarPorLitro + " g/L"
                + " | Precio: $" + String.format("%.0f", calcularPrecio());
    }


    //getter y setters
    public int getAzucarPorLitro() {return azucarPorLitro;}
    public void setAzucarPorLitro(int azucarPorLitro) {this.azucarPorLitro = azucarPorLitro;}
}
