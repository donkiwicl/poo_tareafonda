package cl.dsy1102.fonda;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GestorFonda {
    private List<Bebida> bebidas = new ArrayList<>();
    public GestorFonda() {}

    public void registrar(Bebida bebida){
        this.bebidas.add(bebida);
    }

    public List<Bebida> buscarPorNombre(String nombre){
        List<Bebida> bebidaFiltradas = new ArrayList<>();
        for (Bebida buscada: this.bebidas) {
            if (buscada.getNombre().equals(nombre)) {
                bebidaFiltradas.add(buscada);
            }
        }

        if (!bebidaFiltradas.isEmpty()){
            return bebidaFiltradas;
        } else {
           throw new ProductoNoEncontrado();
        }
        }

    public void vender(String nombre, int unidades){
        List<Bebida> auxiliarParaVenta= buscarPorNombre(nombre);
        //VentaBasica //Corregir
        auxiliarParaVenta.get(0).setStock(auxiliarParaVenta.get(0).getStock()-unidades);
    }

    public List<Bebida> obtenerTodas(){
        return bebidas;
    }

    }
