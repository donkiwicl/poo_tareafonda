package cl.dsy1102.fonda;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GestorFonda {
    private List<Bebida> bebidas = new ArrayList<>();
    public GestorFonda() {}

    public void registrar(Bebida bebida){

        this.bebidas.add(bebida);
        if (bebida instanceof BebidaAlcoholica){
            System.out.println(bebida.getNombre() + " (BebidaAlcoholica) registrada correctamente");
        }
        if (bebida instanceof BebidaSinAlcohol){
            System.out.println(bebida.getNombre() + " (BebidaSinAlcohol) registrada correctamente");
        }
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
    for (Bebida bebida: this.bebidas) {
        if (bebida.getNombre().equals(nombre)){
            bebida.venderBebidaActual(unidades);
        }
    }
    }

    public List<Bebida> obtenerTodas(){
        return bebidas;
    }

    }
