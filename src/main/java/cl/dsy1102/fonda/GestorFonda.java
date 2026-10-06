package cl.dsy1102.fonda;

import java.util.ArrayList;
import java.util.List;

public class GestorFonda {
    private List<Bebida> bebidas;
    public GestorFonda() {this.bebidas = new ArrayList<>();}

    public void registrar(Bebida bebida){
        this.bebidas.add(bebida);
        if (bebida instanceof BebidaAlcoholica){
            System.out.println(bebida.getNombre() + " (BebidaAlcoholica) registrada correctamente.");
        }
        if (bebida instanceof BebidaSinAlcohol){
            System.out.println(bebida.getNombre() + " (BebidaSinAlcohol) registrada correctamente.");
        }
    }

    public List<Bebida> buscarPorNombre(String nombre){
        List<Bebida> listaFiltrada = new ArrayList<Bebida>();
        for (Bebida bebida: this.bebidas){
            if (bebida.getNombre().equals(nombre)){
                listaFiltrada.add(bebida);
            }
        }
        return listaFiltrada;
    }


    public void vender(String nombre, int unidades){
        List<Bebida> listaVentas = buscarPorNombre(nombre);
        if (listaVentas.isEmpty()){
            throw new ProductoNoEncontrado(nombre);
        }
        Bebida bebida = listaVentas.get(0);

        if (bebida instanceof ConsumoResponsable){
            ConsumoResponsable controlada = (ConsumoResponsable) bebida;
            if (controlada.tieneVentaRestringida()){
                throw new VentaNoRealizada(nombre + " tiene la venta restringida.");
            }
            if (controlada.superaLimite(unidades)){
                throw new VentaNoRealizada(unidades + " unidades de " + nombre
                        + " superan el limite de " + BebidaAlcoholica.LIMITE_UNIDADES_POR_CLIENTE
                        + " por cliente.");
            }
        }
        if (bebida.getStock() < unidades){
            throw new VentaNoRealizada("Stock insuficiente de " + nombre + ".");
        }

        bebida.setStock(bebida.getStock() - unidades);
        double total = unidades * bebida.calcularPrecio();
        System.out.println("Venta autorizada: " + unidades + " x " + nombre
                + " | Total: $" + String.format("%.0f", total));
    }

    public List<Bebida> obtenerTodas(){return this.bebidas;}

    }
