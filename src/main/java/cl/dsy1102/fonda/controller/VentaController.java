package cl.dsy1102.fonda.controller;

import cl.dsy1102.fonda.Navegador;
import cl.dsy1102.fonda.model.Bebida;
import cl.dsy1102.fonda.model.GestorFonda;
import cl.dsy1102.fonda.model.VentaRechazadaException;
import cl.dsy1102.fonda.repository.Repository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Registra una venta de la bebida recibida desde la vista principal.
 * Las reglas de consumo responsable las aplica GestorFonda (modelo).
 */
public class VentaController {

    @FXML private Label lblNombre;
    @FXML private Label lblDetalle;
    @FXML private TextField txtUnidades;
    @FXML private Label lblResultado;

    private Repository<Bebida> repositorio;
    private Bebida bebida;
    private GestorFonda gestor;

    public void inicializar(Repository<Bebida> repositorio, Bebida bebida) {
        this.repositorio = repositorio;
        this.bebida = bebida;
        this.gestor = new GestorFonda(repositorio.listar());
        lblNombre.setText(bebida.getNombre() + " (" + bebida.obtenerTipo() + ")");
        lblDetalle.setText(bebida.obtenerDetalle());
    }

    @FXML
    private void onVender() {
        lblResultado.getStyleClass().removeAll("resultado-ok", "resultado-error");
        int unidades;
        try {
            unidades = Integer.parseInt(txtUnidades.getText().trim());
        } catch (NumberFormatException e) {
            Alertas.advertencia("Unidades no validas", "Ingresa la cantidad de unidades como un numero entero.");
            return;
        }
        try {
            lblResultado.setText(gestor.venderBebida(bebida, unidades));
            lblResultado.getStyleClass().add("resultado-ok");
        } catch (VentaRechazadaException e) {
            lblResultado.setText(e.getMessage());
            lblResultado.getStyleClass().add("resultado-error");
        }
    }

    @FXML
    private void onVolver() {
        PrincipalController principal = Navegador.navegar("principal-view.fxml", "Fonda San Belarmino");
        principal.inicializar(repositorio);
    }
}
