package cl.dsy1102.fonda.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Atajos para mostrar mensajes al usuario con Alert.
 */
public final class Alertas {

    private Alertas() {
    }

    public static void error(String encabezado, String mensaje) {
        mostrar(Alert.AlertType.ERROR, "Error", encabezado, mensaje);
    }

    public static void advertencia(String encabezado, String mensaje) {
        mostrar(Alert.AlertType.WARNING, "Atencion", encabezado, mensaje);
    }

    public static boolean confirmar(String encabezado, String mensaje) {
        Alert alerta = crear(Alert.AlertType.CONFIRMATION, "Confirmar", encabezado, mensaje);
        return alerta.showAndWait().filter(ButtonType.OK::equals).isPresent();
    }

    private static void mostrar(Alert.AlertType tipo, String titulo, String encabezado, String mensaje) {
        crear(tipo, titulo, encabezado, mensaje).showAndWait();
    }

    private static Alert crear(Alert.AlertType tipo, String titulo, String encabezado, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(mensaje);
        return alerta;
    }
}
