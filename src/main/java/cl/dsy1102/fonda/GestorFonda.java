package cl.dsy1102.fonda;

import java.util.ArrayList;
import java.util.List;

public class GestorFonda {

    private List<Bebida> bebidas;

    public GestorFonda() {
        bebidas = new ArrayList<>();
    }

    public void registrar(Bebida bebida) {

        bebidas.add(bebida);

        System.out.println(
                bebida.getNombre() +
                        " (" + bebida.getClass().getSimpleName() +
                        ") registrada correctamente."
        );
    }

    public List<Bebida> buscarPorNombre(String nombre) {

        List<Bebida> resultados = new ArrayList<>();

        for (Bebida bebida : bebidas) {

            if (bebida.getNombre().equalsIgnoreCase(nombre)) {
                resultados.add(bebida);
            }
        }

        return resultados;
    }

    public void vender(String nombre, int unidades) {

        List<Bebida> resultados = buscarPorNombre(nombre);

        if (resultados.isEmpty()) {
            System.out.println(
                    "Venta rechazada: no existe una bebida llamada "
                            + nombre + "."
            );
            return;
        }

        for (Bebida bebida : resultados) {

            if (bebida instanceof ConsumoResponsable consumo) {

                if (consumo.tieneVentaRestringida()) {
                    System.out.println(
                            "Venta rechazada: " +
                                    bebida.getNombre() +
                                    " tiene la venta restringida."
                    );
                    continue;
                }

                if (consumo.superaLimite(unidades)) {
                    System.out.println(
                            "Venta rechazada: " +
                                    unidades +
                                    " unidades de " +
                                    bebida.getNombre() +
                                    " superan el limite de " +
                                    BebidaAlcoholica.LIMITE_UNIDADES_POR_CLIENTE +
                                    " por cliente."
                    );
                    continue;
                }
            }

            if (unidades > bebida.getStock()) {
                System.out.println(
                        "Venta rechazada: stock insuficiente."
                );
                continue;
            }

            double total = bebida.calcularPrecio() * unidades;

            bebida.setStock(bebida.getStock() - unidades);

            System.out.println(
                    "Venta autorizada: " +
                            unidades +
                            " x " +
                            bebida.getNombre() +
                            " | Total: $" +
                            (int) total
            );

            return;
        }
    }

    public List<Bebida> obtenerTodas() {
        return bebidas;
    }
}