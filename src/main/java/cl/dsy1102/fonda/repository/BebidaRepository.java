package cl.dsy1102.fonda.repository;

import cl.dsy1102.fonda.dao.BebidaDao;
import cl.dsy1102.fonda.dao.PersistenciaException;
import cl.dsy1102.fonda.model.Bebida;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Mantiene la lista observable de bebidas y la persiste mediante el DAO
 * despues de cada cambio.
 *
 * Cada operacion guarda primero una copia de la lista y solo si el guardado
 * resulta modifica la lista observable; asi la tabla nunca muestra un
 * cambio que no quedo en el archivo.
 */
public class BebidaRepository implements Repository<Bebida> {

    private final BebidaDao dao;
    private final ObservableList<Bebida> bebidas = FXCollections.observableArrayList();

    public BebidaRepository(BebidaDao dao) {
        this.dao = dao;
    }

    @Override
    public void cargar() throws PersistenciaException {
        bebidas.setAll(dao.cargar());
    }

    @Override
    public ObservableList<Bebida> listar() {
        return bebidas;
    }

    @Override
    public void agregar(Bebida bebida) throws PersistenciaException {
        List<Bebida> copia = new ArrayList<>(bebidas);
        copia.add(bebida);
        dao.guardar(copia);
        bebidas.add(bebida);
    }

    @Override
    public void actualizar(Bebida original, Bebida actualizada) throws PersistenciaException {
        int indice = bebidas.indexOf(original);
        if (indice < 0) {
            throw new IllegalArgumentException("La bebida a actualizar no esta registrada.");
        }
        List<Bebida> copia = new ArrayList<>(bebidas);
        copia.set(indice, actualizada);
        dao.guardar(copia);
        bebidas.set(indice, actualizada);
    }

    @Override
    public void eliminar(Bebida bebida) throws PersistenciaException {
        List<Bebida> copia = new ArrayList<>(bebidas);
        copia.remove(bebida);
        dao.guardar(copia);
        bebidas.remove(bebida);
    }
}
