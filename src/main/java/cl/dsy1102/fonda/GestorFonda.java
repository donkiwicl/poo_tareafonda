package cl.dsy1102.fonda;

import java.util.ArrayList;
import java.util.List;

public class GestorFonda {
    private List<Bebida> bebidas = new ArrayList<>();

    //constructor
    public GestorFonda() {}

    public void registrar(Bebida bebida) {
        bebidas.add(bebida);
    }

    public List<Bebida> buscarXNombre(String nombre) {
        List<Bebida> lista = new ArrayList<>();

        for  (Bebida bebida : bebidas) {
            if(bebida.getNombre().equals(nombre)) {
                lista.add(bebida);
            }
        }
        return lista;
    }

    public void vender(String nombre, int unidades) {
        int unidad = unidades;
        while(true){
            List <Bebida> lista = buscarXNombre(nombre);
            if (lista.size() > 1) {
                for (Bebida bebida : lista) {
                    if (bebida.getNombre().equals("Chicha") && bebida.getClass().getSimpleName().equals("BebidaAlcoholica")) {
                        BebidaAlcoholica restringir =  (BebidaAlcoholica) bebida;
                        if (restringir.tieneVentaRest()) {
                            System.out.println("Venta rechazada: " + bebida.getNombre() + " tiene la venta restringida");
                        }
                    }
                }
            } else {
                Bebida bebida = lista.get(0);
                if (bebida.getClass().getSimpleName().equals("BebidaAlcoholica")) {
                    if (unidad > 3){
                        System.out.println("Venta rechazada: " + unidad + " unidades de " + bebida.getNombre() + " supera el limite de 3 por cliente.");
                    } else {
                        System.out.println("Venta autorizada: " + unidad + " x " + bebida.getNombre() + " | $" + String.format("%.0f", bebida.calcularPrecio()*unidad));
                    }
                }else {
                    System.out.println("Venta autorizada: " + unidad + " x " + bebida.getNombre() + " | $" + String.format("%.0f", bebida.calcularPrecio()*unidad));
                }
            }
            break;
        }

    }

    public List<Bebida> obtenerTodas(){
        System.out.println(bebidas.size() + " bebidas registradas.");
        for   (Bebida bebida : bebidas) {
            System.out.println("Nombre: " + bebida.getNombre() + " | Volumen: " + bebida.getVolumenML() + "ml");
        }
        return bebidas;
    }
}