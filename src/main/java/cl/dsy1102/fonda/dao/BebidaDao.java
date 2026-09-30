package cl.dsy1102.fonda.dao;

import cl.dsy1102.fonda.model.Bebida;

import java.util.List;

/**
 * Contrato de acceso fisico a las bebidas. Cambiar JSON por otro medio
 * solo requiere una nueva implementacion de esta interfaz.
 */
public interface BebidaDao {

    List<Bebida> cargar() throws PersistenciaException;

    void guardar(List<Bebida> bebidas) throws PersistenciaException;
}
