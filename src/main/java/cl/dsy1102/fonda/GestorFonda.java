package cl.dsy1102.fonda;

import java.util.List;
import java.util.ArrayList;

public class GestorFonda {

    private List<Bebida> bebidas;





    public GestorFonda(List<Bebida> bebidas) {
        this.setBebidas(bebidas);
    }

    public GestorFonda() {
        this.bebidas = new ArrayList<Bebida>();
    }





    public void setBebidas(List<Bebida> bebidas) throws IllegalArgumentException {
        if (bebidas == null) {
            throw new IllegalArgumentException("Bebidas es null");
        } else {
            this.bebidas = bebidas;
        }
    }

    public void registrar(Bebida bebida) throws IllegalArgumentException {
        if (bebida == null) {
            throw new IllegalArgumentException("Bebida es null");
        } else {
            this.bebidas.add(bebida);
            System.out.println("Bebida \"" + bebida.getNombre() + "\" (" + bebida.obtenerTipo() + ") registrada correctamente");
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
        // Validaciones del nombre
        if (nombre == null) {
            throw new IllegalArgumentException("Nombre es null");
        } else if (nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre está vacío");
        }

        // Validaciones de unidades
        if (unidades < 1) {
            throw new IllegalArgumentException("Unidades es menor a 1");
        }

        // Se buscan las bebidas cuyo nombre coincide con el entregado
        List<Bebida> bebidasEncontradas = this.buscarPorNombre(nombre);

        // Si no se encontraron bebidas con el nombre, terminar.
        if (bebidasEncontradas.size() == 0) {
            System.out.println("No se encontró la bebida de nombre: \"" + nombre + "\"");
            return;
        }

        // Supuesto: Si hay múltiples bebidas con el mismo nombre, vender la primera en ser encontrada.
        Bebida bebidaPorVender = bebidasEncontradas.get(0);

        // Se aplican las reglas del enunciado...
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
        List<Bebida> copia = new ArrayList<Bebida>();
        for (Bebida bebida : this.bebidas) {
            copia.add(bebida);
        }
        return copia;
    }

}
