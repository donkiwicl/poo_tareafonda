package cl.dsy1102.fonda;

public class VentaNoRealizada extends RuntimeException {
    public VentaNoRealizada(String message) {
        super(message);
    }
}
