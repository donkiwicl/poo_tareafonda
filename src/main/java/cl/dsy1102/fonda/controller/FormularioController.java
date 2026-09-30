package cl.dsy1102.fonda.controller;

import cl.dsy1102.fonda.Navegador;
import cl.dsy1102.fonda.dao.PersistenciaException;
import cl.dsy1102.fonda.model.Bebida;
import cl.dsy1102.fonda.model.BebidaAlcoholica;
import cl.dsy1102.fonda.model.BebidaSinAlcohol;
import cl.dsy1102.fonda.repository.Repository;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Formulario para crear o editar una bebida.
 *
 * El controlador valida que los campos no esten vacios y que los numeros
 * se puedan convertir; los rangos (volumen, grados, stock) los valida el
 * modelo en sus setters y el mensaje se muestra al usuario.
 */
public class FormularioController {

    private static final String ALCOHOLICA = "Alcohólica";
    private static final String SIN_ALCOHOL = "Sin alcohol";
    private static final String CLASE_ERROR = "campo-error";

    @FXML private Label lblTitulo;
    @FXML private ComboBox<String> cmbTipo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtVolumen;
    @FXML private TextField txtStock;
    @FXML private VBox boxAlcoholica;
    @FXML private TextField txtGrados;
    @FXML private CheckBox chkCertificada;
    @FXML private CheckBox chkRestringida;
    @FXML private VBox boxSinAlcohol;
    @FXML private TextField txtAzucar;

    private Repository<Bebida> repositorio;
    private Bebida original;

    @FXML
    private void initialize() {
        cmbTipo.getItems().setAll(ALCOHOLICA, SIN_ALCOHOL);
        // Solo se muestran los campos del tipo elegido; managed evita que ocupen espacio ocultos.
        boxAlcoholica.visibleProperty().bind(cmbTipo.valueProperty().isEqualTo(ALCOHOLICA));
        boxAlcoholica.managedProperty().bind(boxAlcoholica.visibleProperty());
        boxSinAlcohol.visibleProperty().bind(cmbTipo.valueProperty().isEqualTo(SIN_ALCOHOL));
        boxSinAlcohol.managedProperty().bind(boxSinAlcohol.visibleProperty());
    }

    /**
     * Recibe los datos desde la vista principal.
     *
     * @param bebida la bebida a editar, o null para registrar una nueva
     */
    public void inicializar(Repository<Bebida> repositorio, Bebida bebida) {
        this.repositorio = repositorio;
        this.original = bebida;
        if (bebida == null) {
            lblTitulo.setText("Nueva bebida");
            return;
        }
        lblTitulo.setText("Editar bebida");
        cmbTipo.setValue(bebida.obtenerTipo());
        cmbTipo.setDisable(true);
        txtNombre.setText(bebida.getNombre());
        txtVolumen.setText(String.valueOf(bebida.getVolumenML()));
        txtStock.setText(String.valueOf(bebida.getStock()));
        if (bebida instanceof BebidaAlcoholica alcoholica) {
            txtGrados.setText(String.valueOf(alcoholica.getGradosAlcohol()));
            chkCertificada.setSelected(alcoholica.isCertificada());
            chkRestringida.setSelected(alcoholica.tieneVentaRestringida());
            // ConsumoResponsable solo permite restringir, no liberar.
            chkRestringida.setDisable(alcoholica.tieneVentaRestringida());
        } else if (bebida instanceof BebidaSinAlcohol sinAlcohol) {
            txtAzucar.setText(String.valueOf(sinAlcohol.getAzucarPorLitro()));
        }
    }

    @FXML
    private void onGuardar() {
        limpiarErrores();
        List<String> errores = new ArrayList<>();

        String tipo = cmbTipo.getValue();
        if (tipo == null) {
            errores.add("Selecciona el tipo de bebida.");
            cmbTipo.getStyleClass().add(CLASE_ERROR);
        }
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            errores.add("El nombre es obligatorio.");
            txtNombre.getStyleClass().add(CLASE_ERROR);
        }
        Integer volumen = leerEntero(txtVolumen, "Volumen", errores);
        Integer stock = leerEntero(txtStock, "Stock", errores);
        Double grados = null;
        Integer azucar = null;
        if (ALCOHOLICA.equals(tipo)) {
            grados = leerDecimal(txtGrados, "Grados de alcohol", errores);
        } else if (SIN_ALCOHOL.equals(tipo)) {
            azucar = leerEntero(txtAzucar, "Azúcar por litro", errores);
        }

        if (!errores.isEmpty()) {
            Alertas.advertencia("Revisa los datos del formulario", String.join("\n", errores));
            return;
        }

        try {
            Bebida bebida = ALCOHOLICA.equals(tipo)
                    ? crearAlcoholica(nombre, volumen, stock, grados)
                    : new BebidaSinAlcohol(nombre, volumen, stock, azucar);
            if (original == null) {
                repositorio.agregar(bebida);
            } else {
                repositorio.actualizar(original, bebida);
            }
            volver();
        } catch (IllegalArgumentException e) {
            Alertas.advertencia("Dato fuera de rango", e.getMessage());
        } catch (PersistenciaException e) {
            Alertas.error("No se pudo guardar la bebida", e.getMessage());
        }
    }

    @FXML
    private void onVolver() {
        volver();
    }

    private BebidaAlcoholica crearAlcoholica(String nombre, int volumen, int stock, double grados) {
        BebidaAlcoholica bebida = new BebidaAlcoholica(nombre, volumen, stock, grados, chkCertificada.isSelected());
        if (chkRestringida.isSelected()) {
            bebida.restringirVenta();
        }
        return bebida;
    }

    private void volver() {
        PrincipalController principal = Navegador.navegar("principal-view.fxml", "Fonda San Belarmino");
        principal.inicializar(repositorio);
    }

    private Integer leerEntero(TextField campo, String nombreCampo, List<String> errores) {
        String texto = campo.getText().trim();
        if (texto.isEmpty()) {
            marcarError(campo, nombreCampo + " es obligatorio.", errores);
            return null;
        }
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            marcarError(campo, nombreCampo + " debe ser un numero entero.", errores);
            return null;
        }
    }

    private Double leerDecimal(TextField campo, String nombreCampo, List<String> errores) {
        String texto = campo.getText().trim().replace(',', '.');
        if (texto.isEmpty()) {
            marcarError(campo, nombreCampo + " es obligatorio.", errores);
            return null;
        }
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            marcarError(campo, nombreCampo + " debe ser un numero (ej: 12,5).", errores);
            return null;
        }
    }

    private void marcarError(TextField campo, String mensaje, List<String> errores) {
        errores.add(mensaje);
        campo.getStyleClass().add(CLASE_ERROR);
    }

    private void limpiarErrores() {
        List.of(cmbTipo, txtNombre, txtVolumen, txtStock, txtGrados, txtAzucar)
                .forEach(control -> control.getStyleClass().remove(CLASE_ERROR));
    }
}
