package cl.dsy1102.fonda;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Centraliza el cambio de vistas sobre el Stage principal.
 *
 * navegar() retorna el controlador de la vista cargada para que quien navega
 * le entregue los datos que necesita (paso de parametros entre controladores).
 */
public final class Navegador {

    private static final String RUTA_VISTAS = "/cl/dsy1102/fonda/view/";

    private static Stage stage;

    private Navegador() {
    }

    public static void setStage(Stage stagePrincipal) {
        stage = stagePrincipal;
    }

    public static <T> T navegar(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(Navegador.class.getResource(RUTA_VISTAS + fxml));
            Parent raiz = loader.load();
            if (stage.getScene() == null) {
                stage.setScene(new Scene(raiz, 900, 560));
            } else {
                // Se reemplaza solo la raiz: la ventana conserva su tamano.
                stage.getScene().setRoot(raiz);
            }
            stage.setTitle(titulo);
            return loader.getController();
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar la vista " + fxml, e);
        }
    }
}
