package cl.dsy1102.fonda.dao;

import cl.dsy1102.fonda.model.Bebida;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Guarda y carga las bebidas en un archivo JSON usando Jackson.
 */
public class JsonBebidaDao implements BebidaDao {

    // Con el tipo generico explicito Jackson escribe el atributo "tipo" de cada subclase.
    private static final TypeReference<List<Bebida>> TIPO_LISTA = new TypeReference<>() {
    };

    private final Path archivo;
    private final ObjectMapper mapper = new ObjectMapper();

    public JsonBebidaDao(Path archivo) {
        this.archivo = archivo;
    }

    @Override
    public List<Bebida> cargar() throws PersistenciaException {
        try {
            if (!Files.exists(archivo) || Files.size(archivo) == 0) {
                return new ArrayList<>();
            }
            return mapper.readValue(archivo.toFile(), TIPO_LISTA);
        } catch (IOException | IllegalArgumentException e) {
            // IllegalArgumentException: un setter del modelo rechazo un valor del archivo.
            throw new PersistenciaException("No se pudieron leer las bebidas desde " + archivo.getFileName()
                    + ". El archivo esta danado o tiene datos invalidos.", e);
        }
    }

    @Override
    public void guardar(List<Bebida> bebidas) throws PersistenciaException {
        try {
            Path carpeta = archivo.toAbsolutePath().getParent();
            if (carpeta != null) {
                Files.createDirectories(carpeta);
            }
            ObjectWriter writer = mapper.writerFor(TIPO_LISTA).withDefaultPrettyPrinter();
            writer.writeValue(archivo.toFile(), bebidas);
        } catch (IOException e) {
            throw new PersistenciaException("No se pudieron guardar las bebidas en " + archivo.getFileName()
                    + ". Revisa que el disco tenga espacio y permisos de escritura.", e);
        }
    }
}
