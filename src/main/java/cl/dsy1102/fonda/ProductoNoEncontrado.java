package cl.dsy1102.fonda;

public class ProductoNoEncontrado extends RuntimeException {
    public ProductoNoEncontrado(String nombre) {
        super("ERROR: Producto No Encontrado" + nombre);
    }
}
