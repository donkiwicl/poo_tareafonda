package cl.dsy1102.fonda;

import cl.dsy1102.fonda.controller.Alertas;
import cl.dsy1102.fonda.controller.PrincipalController;
import cl.dsy1102.fonda.dao.JsonBebidaDao;
import cl.dsy1102.fonda.dao.PersistenciaException;
import cl.dsy1102.fonda.model.Bebida;
import cl.dsy1102.fonda.repository.BebidaRepository;
import cl.dsy1102.fonda.repository.Repository;
import javafx.application.Application;
import javafx.stage.Stage;

import java.nio.file.Path;

/**
 * Punto de entrada de la aplicacion grafica (EA2). Ejecuta con: mvn javafx:run
 *
 * Es el unico lugar, junto al DAO, que conoce la ubicacion del archivo de datos.
 */
public class AppFX extends Application {

    private static final Path ARCHIVO_DATOS = Path.of("data", "bebidas.json");

    @Override
    public void init() {
        System.out.println("[Ciclo de vida] init()  - hilo: " + Thread.currentThread().getName());
    }

    @Override
    public void start(Stage stage) {
        System.out.println("[Ciclo de vida] start() - hilo: " + Thread.currentThread().getName());

        Repository<Bebida> repositorio = new BebidaRepository(new JsonBebidaDao(ARCHIVO_DATOS));

        Navegador.setStage(stage);
        stage.setMinWidth(720);
        stage.setMinHeight(480);
        PrincipalController principal = Navegador.navegar("principal-view.fxml", "Fonda San Belarmino");
        principal.inicializar(repositorio);
        stage.show();

        try {
            repositorio.cargar();
        } catch (PersistenciaException e) {
            Alertas.error("No se pudieron cargar los datos", e.getMessage()
                    + "\n\nLa aplicacion iniciara sin bebidas. Si registras cambios, el archivo se reemplazara.");
        }
    }

    @Override
    public void stop() {
        System.out.println("[Ciclo de vida] stop()  - hilo: " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
