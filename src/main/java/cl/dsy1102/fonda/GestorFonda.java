package cl.dsy1102.fonda;

import java.util.ArrayList;
import java.util.List;

public class GestorFonda {
    /* Creo una lista para permitir instanciar las bebidas agregadas en main */
    private final List<Bebida> bebidas = new ArrayList<>();

    public GestorFonda() {
    }
    /* Al tener la lista creada podemos ver que se agregan todas las bebidas mediante .add*/
    public GestorFonda(List<Bebida> bebidas) {
        if (bebidas != null) {
            for (Bebida bebida:bebidas){
                this.bebidas.add(bebida);
            }
        }
    }
    /*  */
    public void agregarBebida(Bebida bebida) {
        if (bebida == null ) {
            throw new IllegalArgumentException("La bebida no puede ser nula");
        }
        bebidas.add(bebida);
        System.out.println(bebida.getNombre() + " (" + bebida.getClass().getSimpleName()
                + ") registrada correctamente.");
    }

    public List<Bebida> buscarPorNombre(String nombre) {
        List<Bebida> coincidencias = new ArrayList<>();
        if (nombre == null) {
            return coincidencias;
        }
        for (Bebida bebida : this.bebidas) {
            if (bebida.getNombre().equalsIgnoreCase(nombre)) {
                coincidencias.add(bebida);
            }
        }
        return coincidencias;
    }

    public List<Bebida> obtenerTodas() {
        return new ArrayList<>(bebidas);
    }

    public void mostrarBebidas() {
        for (Bebida bebida : bebidas) {
            System.out.println(bebida);
        }
    }

    public void vender(String nombreBebida, int unidades) {
        if (unidades <= 0) {
            System.out.println("Venta rechazada: las unidades deben ser mayores que cero.");
            return;
        }

        for (Bebida bebida : bebidas) {
            if (!bebida.getNombre().equalsIgnoreCase(nombreBebida)) {
                continue;
            }
            if (unidades > bebida.getStock()) {
                System.out.println("Venta rechazada: stock insuficiente para "  + bebida.getNombre() + ".");
                return;
            }
            if (bebida instanceof ConsumoResponsable consumo) {
                if (consumo.tieneVentaRestringida()) {
                    System.out.println("Venta rechazada: " + bebida.getNombre()  + " tiene la venta restringida.");
                    return;
                }
                if (consumo.superaLimite(unidades)) {
                    System.out.println("Venta rechazada: " + unidades + " unidades de "  + bebida.getNombre() + " superan el limite de "  + BebidaAlcoholica.LIMITE_UNIDADES_POR_CLIENTE  + " por cliente.");
                    return;
                }
            }
            int total = (int) (bebida.calcularPrecio() * unidades);
            bebida.setStock(bebida.getStock() - unidades);
            System.out.println("Venta autorizada: " + unidades + " x "  + bebida.getNombre() + " | Total: $" + total);
            return;
        }
        System.out.println("Bebida no encontrada");
    }
}
