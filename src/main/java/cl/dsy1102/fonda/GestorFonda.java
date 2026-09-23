package cl.dsy1102.fonda;

import java.util.ArrayList;
import java.util.List;

public class GestorFonda {
    private final List<Bebida> bebidas;

    public GestorFonda() {
        this.bebidas = new ArrayList<>();
    }

    public void registrar(Bebida bebida) {
        bebidas.add(bebida);
        System.out.println(bebida.getNombre() + " (" + bebida.getClass().getSimpleName() + ") registrada correctamente");
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
        for (Bebida bebida: bebidas){
            if (bebida.getNombre().equalsIgnoreCase(nombre)){
                if (bebida instanceof ConsumoResponsable consumo){
                    if (consumo.tieneVentaRestringida()){
                        System.out.println("venta rechazado: "+ nombre + " tiene la venta restringida");
                        return;
                    }
                    if (consumo.superaLimite(unidades)){
                        System.out.println("Venta rechazada: "+unidades+ " unidades de"+ nombre+ " supera el limite "+
                                BebidaAlcoholica.LIMITE_UNIDADES_POR_CLIENTE+ " por el cliente");
                        return;
                    }
                }
                double total = bebida.calcularPrecio() * unidades;
                System.out.println("Venta autorizada: " + unidades + " de " + nombre + " TOTAL: $ "+ total);
                return;
            }

        }
        System.out.println("Venta rechazada: no se encontro la bebida con el nombre de: "+nombre);

    }

    public List<Bebida> obtenerTodas() {
        return bebidas;
    }
}
