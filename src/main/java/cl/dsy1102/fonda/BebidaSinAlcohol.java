package cl.dsy1102.fonda;

//Creación de la clase
public class BebidaSinAlcohol extends Bebida {
    private int azucarPorLitro;


//Constructor
 public BebidaSinAlcohol(String nombre, int volumenMl, int stock, int azucarPorLitro){
    super(nombre, volumenMl, stock);
    this.azucarPorLitro = azucarPorLitro;}


 //Getters y Setters
    public int getAzucarPorLitro() {
        return azucarPorLitro;
    }

    public void setAzucarPorLitro(int azucarPorLitro) {
        this.azucarPorLitro = azucarPorLitro;
    }

//Métodos heredados
    @Override
    public double calcularPrecio() {
        double precioBase = 2000;
        if (getAzucarPorLitro() > 80){
            return precioBase * 1.1;}
        else{
            return precioBase;
        }
    }

    @Override
    public String obtenerDetalle() {
        return "Tipo: Bebida Sin Alcohol | Nombre: " + getNombre()
                + " | Volumen: " + getVolumenMl() + " ml"
                + " | Stock: " + getStock()
                + " | Azúcar: " + getAzucarPorLitro() + " g/L"
                + " | Precio: $" + (int) calcularPrecio();
    }

}