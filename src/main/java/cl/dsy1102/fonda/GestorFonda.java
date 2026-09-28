package cl.dsy1102.fonda;

import java.util.List;
import java.util.ArrayList;

public class GestorFonda {

    private List<Bebida> bebidas;

    public GestorFonda() {
        this.bebidas = new ArrayList<Bebida>();
    }

    public void registrar(Bebida bebida) throws IllegalArgumentException {
        if (bebida == null) {
            throw new IllegalArgumentException("Bebida es null");
        } else {
            this.bebidas.add(bebida);
            String tipo;
            if (bebida instanceof BebidaSinAlcohol) {
                tipo = "BebidaSinAlcohol";
            } else if (bebida instanceof BebidaAlcoholica) {
                tipo = "BebidaAlcoholica";
            } else {
                tipo = "Bebida";
            }
            System.out.println("Bebida \"" + bebida.getNombre() + "\" (" + tipo + ") registrada correctamente");
        }
    }

    public List<Bebida> buscarPorNombre(String nombre) {
        List<Bebida> matches = new ArrayList<Bebida>();
        for (Bebida bebida : this.bebidas) {
            if (bebida.getNombre().equalsIgnoreCase(nombre)) {
                matches.add(bebida);
            }
        }
        return matches;
    }

    public void vender(String nombre, int unidades) throws IllegalArgumentException {
        if (nombre == null) {
            throw new IllegalArgumentException("Nombre es null");
        } else if (nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre está vacío");
        }
        List<Bebida> bebidasEncontradas = this.buscarPorNombre(nombre);
        if (bebidasEncontradas.size() == 0) {
            System.out.println("No se encontró la bebida de nombre: \"" + nombre + "\"");
            return;
        }
        Bebida bebidaPorVender = bebidasEncontradas.get(0);
        if (bebidaPorVender instanceof ConsumoResponsable) {
            if (((ConsumoResponsable) bebidaPorVender).tieneVentaRestringida()) {
                System.out.println("Venta rechazada: " + bebidaPorVender.getNombre() + " tiene la venta restringida");
            } else if (((ConsumoResponsable) bebidaPorVender).superaLimite(unidades)) {
                System.out.println("Venta rechazada: " + unidades + " unidades de " + bebidaPorVender.getNombre() + " superan el límite de " + BebidaAlcoholica.LIMITE_UNIDADES_POR_CLIENTE + " por cliente");
            } else {
                System.out.println("Venta autorizada: " + unidades + " x " + bebidaPorVender.getNombre() + " | Total: $" + bebidaPorVender.calcularPrecio() * unidades);
            }
        } else {
            System.out.println("Venta autorizada: " + unidades + " x " + bebidaPorVender.getNombre() + " | Total: $" + bebidaPorVender.calcularPrecio() * unidades);
        }
    }

    public List<Bebida> obtenerTodas() {
        return this.bebidas;
    }

}
