package org.example;

import java.util.ArrayList;
import java.util.List;

public class GestorFonda {
    private final List<Bebida> listBebidas = new ArrayList<>();

    public GestorFonda(){};

    public GestorFonda(List<Bebida> listBebidas) {
        if (listBebidas != null) {
            for (Bebida elemBebida : listBebidas) {
                this.listBebidas.add(elemBebida);
            }
        }
    }

    public void registrarBebida(Bebida elemBebidas) {
        if (elemBebidas == null || elemBebidas.getNombre().isBlank()) {
            throw new IllegalArgumentException("No se pueden agregar elementos vacios ");
        }

        listBebidas.add(elemBebidas);

        System.out.printf("%s (%s) registrada correctamente.%n",
                elemBebidas.getNombre(),
                elemBebidas.getClass().getSimpleName());


    }

    public List<Bebida> buscarBebidas(String nombre) {
        List<Bebida> resultados = new ArrayList<>();
        if (nombre == null) {
            return resultados;
        } else {
            for (Bebida elemtBebidas : listBebidas) {
                if (elemtBebidas.getNombre().equalsIgnoreCase(nombre)) {
                    resultados.add(elemtBebidas);

                }
            }
            return resultados;
        }


    }

    public void mostrarBebidas() {
        for (Bebida bebida : listBebidas) {
            System.out.println(bebida);
        }
    }
    public void ventaBebidas(String nombre, int cantidadCompra) {
        List<Bebida> resBusquedaBebida = buscarBebidas(nombre);

        for (Bebida iBebida : resBusquedaBebida) {
            boolean puedeVender = true;
            if (iBebida instanceof ConsumoResponsable) {
                ConsumoResponsable ofBebida = (ConsumoResponsable) iBebida;
                if(ofBebida.tieneVentaRestringida() == true){
                System.out.println("Venta restringida, no se puede proceder con la compra");
                puedeVender = false;}

                else if (ofBebida.superaLimite(cantidadCompra)){
                System.out.println("No se pueden vender mas bebidas, se supero el limite");
                puedeVender = false;}

                else{
                    System.out.println("Puede proceder con la compra");
                    puedeVender = true;}
                }
            if (puedeVender == true){
                double totalPagar = iBebida.calcularPrecio() * cantidadCompra;
                System.out.println("Total a pagar de la compra:" + totalPagar);
            }

            }
        }
    }
