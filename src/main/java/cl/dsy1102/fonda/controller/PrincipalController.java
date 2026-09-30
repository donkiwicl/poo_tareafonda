package cl.dsy1102.fonda.controller;

import cl.dsy1102.fonda.Navegador;
import cl.dsy1102.fonda.dao.PersistenciaException;
import cl.dsy1102.fonda.model.Bebida;
import cl.dsy1102.fonda.model.ConsumoResponsable;
import cl.dsy1102.fonda.repository.Repository;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Vista principal: tabla de bebidas con busqueda en tiempo real y acciones.
 */
public class PrincipalController {

    @FXML private TextField txtBuscar;
    @FXML private TableView<Bebida> tblBebidas;
    @FXML private TableColumn<Bebida, String> colTipo;
    @FXML private TableColumn<Bebida, String> colNombre;
    @FXML private TableColumn<Bebida, Integer> colVolumen;
    @FXML private TableColumn<Bebida, Integer> colStock;
    @FXML private TableColumn<Bebida, Double> colPrecio;
    @FXML private TableColumn<Bebida, String> colRestringida;
    @FXML private Label lblTotal;

    private Repository<Bebida> repositorio;

    /** Lo llama FXMLLoader al terminar de inyectar los @FXML. */
    @FXML
    private void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colVolumen.setCellValueFactory(new PropertyValueFactory<>("volumenML"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        // Estos valores no son getters del modelo, se obtienen con un lambda.
        colTipo.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().obtenerTipo()));
        colPrecio.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<>(celda.getValue().calcularPrecio()));
        colPrecio.setCellFactory(columna -> new TableCell<>() {
            @Override
            protected void updateItem(Double precio, boolean vacia) {
                super.updateItem(precio, vacia);
                setText(vacia || precio == null ? null : String.format("$%,.0f", precio));
            }
        });
        colRestringida.setCellValueFactory(celda -> new ReadOnlyStringWrapper(textoRestriccion(celda.getValue())));

        // Doble clic sobre una fila abre la edicion.
        tblBebidas.setRowFactory(tabla -> {
            TableRow<Bebida> fila = new TableRow<>();
            fila.setOnMouseClicked(evento -> {
                if (evento.getClickCount() == 2 && !fila.isEmpty()) {
                    abrirFormulario(fila.getItem());
                }
            });
            return fila;
        });
    }

    /** Recibe el repositorio desde quien navega hacia esta vista y enlaza la tabla. */
    public void inicializar(Repository<Bebida> repositorio) {
        this.repositorio = repositorio;

        FilteredList<Bebida> filtradas = new FilteredList<>(repositorio.listar(), bebida -> true);
        txtBuscar.textProperty().addListener((obs, anterior, texto) ->
                filtradas.setPredicate(bebida -> coincide(bebida, texto)));

        SortedList<Bebida> ordenadas = new SortedList<>(filtradas);
        ordenadas.comparatorProperty().bind(tblBebidas.comparatorProperty());
        tblBebidas.setItems(ordenadas);

        lblTotal.textProperty().bind(Bindings.format("%d de %d bebidas",
                Bindings.size(filtradas), Bindings.size(repositorio.listar())));
    }

    @FXML
    private void onNueva() {
        abrirFormulario(null);
    }

    @FXML
    private void onEditar() {
        Bebida seleccionada = obtenerSeleccion("editar");
        if (seleccionada != null) {
            abrirFormulario(seleccionada);
        }
    }

    @FXML
    private void onEliminar() {
        Bebida seleccionada = obtenerSeleccion("eliminar");
        if (seleccionada == null) {
            return;
        }
        boolean confirma = Alertas.confirmar("Eliminar bebida",
                "Se eliminara \"" + seleccionada.getNombre() + "\" (" + seleccionada.obtenerTipo() + "). ¿Continuar?");
        if (!confirma) {
            return;
        }
        try {
            repositorio.eliminar(seleccionada);
        } catch (PersistenciaException e) {
            Alertas.error("No se pudo eliminar la bebida", e.getMessage());
        }
    }

    @FXML
    private void onVender() {
        Bebida seleccionada = obtenerSeleccion("vender");
        if (seleccionada != null) {
            VentaController venta = Navegador.navegar("venta-view.fxml", "Vender - " + seleccionada.getNombre());
            venta.inicializar(repositorio, seleccionada);
        }
    }

    private void abrirFormulario(Bebida bebida) {
        String titulo = bebida == null ? "Nueva bebida" : "Editar - " + bebida.getNombre();
        FormularioController formulario = Navegador.navegar("formulario-view.fxml", titulo);
        formulario.inicializar(repositorio, bebida);
    }

    private Bebida obtenerSeleccion(String accion) {
        Bebida seleccionada = tblBebidas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Alertas.advertencia("Ninguna bebida seleccionada", "Selecciona una fila de la tabla para " + accion + ".");
        }
        return seleccionada;
    }

    private static boolean coincide(Bebida bebida, String texto) {
        return texto == null || texto.isBlank()
                || bebida.getNombre().toLowerCase().contains(texto.trim().toLowerCase());
    }

    private static String textoRestriccion(Bebida bebida) {
        if (bebida instanceof ConsumoResponsable controlada) {
            return controlada.tieneVentaRestringida() ? "Sí" : "No";
        }
        return "No aplica";
    }
}
