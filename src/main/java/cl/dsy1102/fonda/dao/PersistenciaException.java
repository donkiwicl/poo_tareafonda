package cl.dsy1102.fonda.dao;

/**
 * Error al leer o escribir los datos. Oculta a las capas superiores si el
 * origen es un archivo, una base de datos u otro medio.
 */
public class PersistenciaException extends Exception {

    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
