package cl.dsy1102.fonda;

public class ProductoNoEncontrado extends RuntimeException {
    public ProductoNoEncontrado() {
        super("ERROR: Producto No Encontrado");
    }
}
