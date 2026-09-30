# Guía de solución EA2 — Fonda San Belarmino

**DSY1102 · Programación Orientada a Objetos · EA2: Desarrollo de interfaces gráficas con persistencia en archivo**

Esta guía acompaña la rama `solucion`. Recorre el enunciado del [`README.md`](README.md) en el mismo orden de las sesiones 2.1 a 2.4. En cada paso indica **qué se pide**, **dónde está resuelto** y **por qué se resolvió así**. Al final incluye una lista de verificación con los criterios de la Evaluación Parcial 2.

> Para comparar tu avance con la solución: `git diff main solucion -- <archivo>`.
> Para ver la solución construida capa por capa: `git log --oneline main..solucion`.

---

## 0. Mapa de requisitos

| Requisito | Sesión | Dónde se cumple |
|---|---|---|
| R1 Maven, módulos y ciclo de vida | 2.1 | `pom.xml`, `module-info.java`, `AppFX` |
| R2 Modelo EA1 listo para JSON | 2.1 / 2.4 | `model/*` |
| R3 Vista principal con tabla y búsqueda | 2.2 / 2.3 | `principal-view.fxml`, `PrincipalController` |
| R4 Formulario crear/editar | 2.2 / 2.3 | `formulario-view.fxml`, `FormularioController` |
| R5 Venta con consumo responsable | 2.3 | `venta-view.fxml`, `VentaController`, `GestorFonda.venderBebida` |
| R6 Persistencia JSON con Repository y DAO | 2.4 | `dao/*`, `repository/*` |
| R7 Validación y manejo de errores | 2.3 / 2.4 | `FormularioController`, `Alertas`, `JsonBebidaDao` |
| R8 Navegación con paso de parámetros | 2.3 | `Navegador`, método `inicializar(...)` de cada controlador |

Estructura final:

```
src/main/java/
├── module-info.java
└── cl/dsy1102/fonda/
    ├── AppFX.java                  Application: ciclo de vida, arma las capas
    ├── Navegador.java              Cambio de vistas con FXMLLoader
    ├── Main.java                   Consola EA1 (sigue funcionando)
    ├── model/
    │   ├── Bebida.java             abstracta + anotaciones Jackson
    │   ├── BebidaAlcoholica.java   implements ConsumoResponsable
    │   ├── BebidaSinAlcohol.java
    │   ├── ConsumoResponsable.java
    │   ├── GestorFonda.java        reglas de venta
    │   └── VentaRechazadaException.java
    ├── dao/
    │   ├── BebidaDao.java          interfaz
    │   ├── JsonBebidaDao.java      Jackson + archivo
    │   └── PersistenciaException.java
    ├── repository/
    │   ├── Repository.java         interfaz CRUD genérica
    │   └── BebidaRepository.java   ObservableList + DAO
    └── controller/
        ├── Alertas.java
        ├── PrincipalController.java
        ├── FormularioController.java
        └── VentaController.java
src/main/resources/cl/dsy1102/fonda/view/
├── principal-view.fxml
├── formulario-view.fxml
├── venta-view.fxml
└── styles.css
```

Flujo de dependencias. Cada capa conoce solo a la de su derecha:

```
Vista (FXML) → Controlador → Repository (interfaz) → BebidaRepository → BebidaDao (interfaz) → JsonBebidaDao → data/bebidas.json
                    └────────────→ Modelo (Bebida, GestorFonda) ←────────────┘
```

---

## Paso 1 — Maven, módulos y ciclo de vida (Sesión 2.1)

### 1.1 `pom.xml`

| Elemento | Para qué sirve |
|---|---|
| `javafx-controls` | Controles (`TableView`, `Button`, `TextField`, `Alert`...). Trae `javafx-base` y `javafx-graphics` como dependencias transitivas. |
| `javafx-fxml` | `FXMLLoader` y la inyección de `@FXML`. |
| `jackson-databind` | `ObjectMapper` para convertir objetos a JSON y viceversa. Trae `jackson-core` y `jackson-annotations`. |
| `maven.compiler.release` 25 y `javafx.version` 25.0.4 | El proyecto usa JDK 25 LTS. **La versión mayor de JavaFX debe coincidir con la del JDK**: JavaFX 25 no funciona en JDK 21, y con JDK 21 se debe volver a `21` y `21.0.6`. |
| `javafx-maven-plugin` | `mvn javafx:run`. Con `module-info.java` la clase se indica como `modulo/clase`: `cl.dsy1102.fonda/cl.dsy1102.fonda.AppFX`. La opción `--enable-native-access=javafx.graphics` es necesaria desde JDK 24: sin ella aparece la advertencia *"A restricted method in java.lang.System has been called"*, que en futuras versiones será un error. |
| `maven-clean-plugin` 3.3.2 y `maven-resources-plugin` 3.3.1 | Se fijan las versiones porque las que trae Maven 3.8 por defecto (2.5 y 2.6) no suelen estar en el repositorio local de los laboratorios, y así `mvn -o` no funciona. |

> **Sin internet en la EP2:** ejecuta `mvn compile` y `mvn javafx:run` con conexión antes de la evaluación para que todo quede en `~/.m2`. Desde ahí funciona con `mvn -o`.
>
> Con Maven 3.8 sobre JDK 25 puede aparecer *"sun.misc.Unsafe::objectFieldOffset has been called by com.google.common..."*. La advertencia viene de la biblioteca Guava que usa el propio Maven, no del proyecto, y no afecta la compilación.

### 1.2 `module-info.java`

```java
module cl.dsy1102.fonda {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    opens cl.dsy1102.fonda.controller to javafx.fxml;
    opens cl.dsy1102.fonda.model to com.fasterxml.jackson.databind, javafx.base;

    exports cl.dsy1102.fonda;
}
```

- `opens ...controller to javafx.fxml`: FXML asigna por reflexión los atributos **privados** `@FXML`. Sin esto aparece `IllegalAccessException ... module cl.dsy1102.fonda does not open cl.dsy1102.fonda.controller`.
- `opens ...model to com.fasterxml.jackson.databind`: Jackson crea las bebidas con su constructor protegido y escribe `ventaRestringida`, que es privado.
- `opens ...model to javafx.base`: `PropertyValueFactory` invoca los getters por reflexión.
- `exports cl.dsy1102.fonda`: JavaFX necesita instanciar `AppFX`.

### 1.3 `AppFX`: ciclo de vida

- `init()` → `start(Stage)` → `stop()` se trazan por consola, incluyendo el hilo. `init()` corre en `JavaFX-Launcher` y `start()` en `JavaFX Application Thread`. Por eso los `Alert` solo se muestran desde `start()` en adelante.
- Se usa el `Stage` que entrega `start()`. Nunca `new Stage()` para la ventana principal.
- `start()` **arma las capas**: `new BebidaRepository(new JsonBebidaDao(ARCHIVO_DATOS))`. Es el único lugar, además del DAO, donde aparece la ruta `data/bebidas.json`.
- Primero se muestra la ventana y luego se cargan los datos. Si la carga falla, se informa con un `Alert` y la aplicación sigue con la tabla vacía.

**Pregunta de mediación:** *¿De dónde proviene el objeto `Stage`?* Lo crea el runtime de JavaFX (`Application.launch`) y lo entrega a `start()`.

---

## Paso 2 — Modelo EA1 preparado para JSON (Sesiones 2.1 y 2.4)

Las reglas de EA1 no cambian. `mvn compile exec:java` sigue produciendo exactamente la salida esperada de EA1. Solo se agregan cuatro elementos:

1. **Constructor sin parámetros `protected`** en cada clase. Jackson crea el objeto vacío y luego llama a los setters, **así que las validaciones del modelo también se aplican al leer el archivo**. Un JSON con `"volumenML": 50` se rechaza.
2. **Subtipos en el JSON.** `Bebida` es abstracta, así que Jackson necesita saber qué clase crear:
   ```java
   @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "tipo")
   @JsonSubTypes({
           @JsonSubTypes.Type(value = BebidaAlcoholica.class, name = "ALCOHOLICA"),
           @JsonSubTypes.Type(value = BebidaSinAlcohol.class, name = "SIN_ALCOHOL")
   })
   public abstract class Bebida { ... }
   ```
3. **`@JsonProperty("ventaRestringida")`** sobre el atributo. `tieneVentaRestringida()` no sigue la convención `getX()`/`isX()`, así que Jackson no lo detectaría. La interfaz `ConsumoResponsable` no se toca.
4. **`obtenerTipo()` abstracto** para la columna *Tipo*. La tabla lo usa por polimorfismo, sin `instanceof`. Como no empieza con `get`, Jackson no lo persiste.

Además, `GestorFonda.venderBebida(Bebida, int)` **retorna** el mensaje de venta autorizada o **lanza** `VentaRechazadaException`. El `vender(String, int)` de EA1 lo reutiliza e imprime por consola. Así la regla de negocio vive en un solo lugar y la interfaz gráfica no imprime nada.

> **Error frecuente (docente 2.4):** "fallas al serializar objetos complejos". Mantén el modelo como POJO: atributos primitivos o `String`, sin referencias a controles JavaFX ni al repositorio.

---

## Paso 3 — Vistas FXML (Sesión 2.2)

Cada vista declara en su raíz `fx:controller` y `stylesheets="@styles.css"`. Así Scene Builder la muestra con estilos y el controlador queda enlazado.

| Vista | Contenedores | Controles con `fx:id` |
|---|---|---|
| `principal-view.fxml` | `BorderPane` (top `VBox`, center `TableView`, bottom `HBox`) | `txtBuscar`, `tblBebidas`, `colTipo`...`colRestringida`, `btnNueva`, `btnEditar`, `btnEliminar`, `btnVender`, `lblTotal` |
| `formulario-view.fxml` | `BorderPane` + `ScrollPane` + `GridPane` | `lblTitulo`, `cmbTipo`, `txtNombre`, `txtVolumen`, `txtStock`, `boxAlcoholica`, `txtGrados`, `chkCertificada`, `chkRestringida`, `boxSinAlcohol`, `txtAzucar`, `btnGuardar`, `btnVolver` |
| `venta-view.fxml` | `BorderPane` + `VBox` + `HBox` | `lblNombre`, `lblDetalle`, `txtUnidades`, `btnVender`, `lblResultado`, `btnVolver` |

Decisiones de diseño:

- **Redimensionamiento:** no hay coordenadas absolutas. La tabla crece en el centro del `BorderPane`, el buscador usa `HBox.hgrow="ALWAYS"` y las columnas usan `CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN`. `AppFX` fija un tamaño mínimo de 720×480.
- **Eventos:** `onAction="#onGuardar"` en el FXML y `@FXML private void onGuardar()` en el controlador. El parámetro `ActionEvent` es opcional si no se usa.
- **Teclado:** `defaultButton="true"` (Enter) en Guardar/Vender y `cancelButton="true"` (Esc) en Volver.
- `styles.css` define las clases `primario`, `peligro`, `campo-error`, `resultado-ok` y `resultado-error`, que los controladores activan según el estado.

> **Pregunta de mediación:** *¿Qué pasa si un `@FXML` no tiene su `fx:id`?* El atributo queda en `null` y se produce un `NullPointerException` al usarlo, normalmente dentro de `initialize()`.

---

## Paso 4 — TableView, búsqueda y MVC (Sesión 2.3)

### 4.1 Columnas (`PrincipalController.initialize`)

```java
colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));      // getNombre()
colVolumen.setCellValueFactory(new PropertyValueFactory<>("volumenML"));  // getVolumenML()
colTipo.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().obtenerTipo()));
colPrecio.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().calcularPrecio()));
```

`PropertyValueFactory` sirve para los getters. Para valores calculados (`obtenerTipo()`, `calcularPrecio()`) se usa un lambda. La columna *Precio* guarda un `Double`, así que ordena como número, y un `TableCell` le da el formato `$4.200`.

### 4.2 Búsqueda en tiempo real (`PrincipalController.inicializar`)

```java
FilteredList<Bebida> filtradas = new FilteredList<>(repositorio.listar(), b -> true);
txtBuscar.textProperty().addListener((obs, anterior, texto) ->
        filtradas.setPredicate(bebida -> coincide(bebida, texto)));

SortedList<Bebida> ordenadas = new SortedList<>(filtradas);
ordenadas.comparatorProperty().bind(tblBebidas.comparatorProperty());
tblBebidas.setItems(ordenadas);
```

- La cadena es `ObservableList` (repositorio) → `FilteredList` (búsqueda) → `SortedList` (orden por clic en el encabezado) → `TableView`.
- **Nunca** se agregan filas al `TableView`. Todo cambio ocurre en la `ObservableList` del repositorio y la tabla se actualiza sola.
- `lblTotal` se enlaza con `Bindings.format("%d de %d bebidas", ...)` y tampoco necesita actualizarse a mano.

> **Pregunta de mediación:** *¿Por qué `ObservableList` y no `ArrayList`?* Porque notifica cada `add`, `set` o `remove` a sus observadores (`FilteredList`, `TableView`, bindings). Un `ArrayList` no avisa a nadie.

### 4.3 Responsabilidades MVC

| Capa | Hace | No hace |
|---|---|---|
| Vista (FXML) | Estructura y estilo | Lógica ni validaciones |
| Controlador | Leer campos, validar entrada, mostrar `Alert`, navegar, llamar al repositorio | Leer o escribir archivos, calcular precios, decidir si una venta procede |
| Modelo | Reglas de negocio y validación de rangos | Conocer JavaFX o JSON |
| Repository/DAO | Mantener y persistir la colección | Mostrar mensajes al usuario |

---

## Paso 5 — Navegación con paso de parámetros (Sesión 2.3)

`Navegador.navegar(fxml, titulo)` carga el FXML, reemplaza la raíz de la escena (la ventana conserva su tamaño) y **retorna el controlador** de la vista cargada:

```java
// PrincipalController: abrir el formulario en modo edición
FormularioController formulario = Navegador.navegar("formulario-view.fxml", "Editar - " + bebida.getNombre());
formulario.inicializar(repositorio, bebida);
```

- `initialize()` (sin parámetros, lo llama `FXMLLoader`) configura lo visual.
- `inicializar(...)` (público, lo llama quien navega) recibe los datos: el repositorio y, cuando corresponde, la bebida seleccionada.
- El repositorio viaja entre controladores al navegar. No hay variables estáticas globales de datos.

| Acción | Origen | Destino | Datos entregados |
|---|---|---|---|
| Nueva | Principal | Formulario | `repositorio`, `null` |
| Editar / doble clic | Principal | Formulario | `repositorio`, bebida seleccionada |
| Vender | Principal | Venta | `repositorio`, bebida seleccionada |
| Guardar / Volver | Formulario o Venta | Principal | `repositorio` |

---

## Paso 6 — Validación de formularios (Sesión 2.3)

La validación ocurre en **dos niveles**, y cada uno tiene su responsabilidad:

1. **Controlador (antes de tocar el modelo):** tipo seleccionado, campos obligatorios y conversión numérica (`Integer.parseInt`, `Double.parseDouble`). Los grados aceptan coma decimal (`12,5`). Se juntan **todos** los errores en un solo `Alert` y los campos con problemas se marcan con la clase CSS `campo-error`.
2. **Modelo (setters):** rangos de negocio (volumen 100–3000, stock > 0, grados 0,5–45). Si un setter lanza `IllegalArgumentException`, el controlador la captura y muestra su mensaje en un `Alert`:

```java
try {
    Bebida bebida = ...;               // los setters validan rangos
    if (original == null) repositorio.agregar(bebida);
    else repositorio.actualizar(original, bebida);
    volver();
} catch (IllegalArgumentException e) {
    Alertas.advertencia("Dato fuera de rango", e.getMessage());
} catch (PersistenciaException e) {
    Alertas.error("No se pudo guardar la bebida", e.getMessage());
}
```

Así los rangos se definen **una sola vez**, en el modelo. El formulario no los duplica.

**Editar sin corromper datos:** al editar se construye una bebida **nueva** y se reemplaza con `repositorio.actualizar(original, nueva)`. Si un setter falla a mitad de camino, la bebida original queda intacta.

**Venta restringida:** `ConsumoResponsable` solo permite `restringirVenta()`. Por eso, al editar una bebida restringida, `chkRestringida` aparece marcado y deshabilitado.

---

## Paso 7 — Persistencia: DAO y Repository (Sesión 2.4)

### 7.1 DAO: cómo se accede al archivo

```java
public interface BebidaDao {
    List<Bebida> cargar() throws PersistenciaException;
    void guardar(List<Bebida> bebidas) throws PersistenciaException;
}
```

`JsonBebidaDao` es la única clase que usa `ObjectMapper`, `Path` y `Files`:

| Situación | Implementación |
|---|---|
| El archivo no existe o está vacío | `return new ArrayList<>()` |
| Falta la carpeta `data/` | `Files.createDirectories(...)` antes de escribir |
| JSON dañado o con valores inválidos | `IOException` → `PersistenciaException` con mensaje para el usuario |
| No se puede escribir | `IOException` → `PersistenciaException` |
| JSON legible | `writerFor(TIPO_LISTA).withDefaultPrettyPrinter()` |

> **Detalle importante:** se usa `mapper.writerFor(new TypeReference<List<Bebida>>(){})`. Con `mapper.writeValue(archivo, lista)` Jackson solo ve una `List` genérica, por el borrado de tipos, **y omite el atributo `"tipo"`**. El archivo se escribiría, pero no se podría volver a leer.

`PersistenciaException` existe para que los controladores **no** dependan de `IOException`. Si mañana el DAO usa una base de datos, los controladores no cambian.

### 7.2 Repository: cómo trabaja la aplicación con los datos

```java
public interface Repository<T> {
    void cargar() throws PersistenciaException;
    ObservableList<T> listar();
    void agregar(T elemento) throws PersistenciaException;
    void actualizar(T original, T actualizado) throws PersistenciaException;
    void eliminar(T elemento) throws PersistenciaException;
}
```

`BebidaRepository` **guarda después de cada cambio** y lo hace en un orden que protege la consistencia:

```java
public void agregar(Bebida bebida) throws PersistenciaException {
    List<Bebida> copia = new ArrayList<>(bebidas);
    copia.add(bebida);
    dao.guardar(copia);      // si falla, lanza y no se ejecuta la línea siguiente
    bebidas.add(bebida);     // la tabla solo muestra lo que quedó guardado
}
```

Los controladores declaran el atributo como `Repository<Bebida>`, no como `BebidaRepository` ni `JsonBebidaDao`. **Ningún controlador contiene `File`, `Path`, `ObjectMapper` ni `"bebidas.json"`.** Puedes comprobarlo con:

```bash
grep -rnE "ObjectMapper|java.io.File|java.nio|bebidas.json" src/main/java/cl/dsy1102/fonda/controller   # sin resultados
```

> **Pregunta de mediación:** *¿Por qué una interfaz Repository en vez de escribir el JSON en el controlador?* Para que el controlador solo capture datos y delegue. El ejemplo "mal diseñado" de la guía docente (un `FileWriter` dentro de `btnGuardarClick`) mezcla interfaz, formato y disco en un mismo método, y no se puede reutilizar ni probar.

---

## Paso 8 — Probar la solución

```bash
mvn compile              # debe terminar sin errores
mvn javafx:run           # aplicación gráfica
mvn compile exec:java    # salida de consola de EA1
```

Recorrido de pruebas del enunciado, con el resultado que entrega esta solución:

| # | Prueba | Resultado esperado |
|---|---|---|
| 1 | Guardar formulario vacío | Alert: tipo, nombre, volumen y stock obligatorios; campos en rojo |
| 1 | Volumen `abc` | Alert: "Volumen debe ser un numero entero." |
| 1 | Volumen `50` | Alert: "El volumen debe estar entre 100 y 3000 ml." (modelo) |
| — | Registrar las 4 bebidas de EA1 | Tabla con 4 filas; Chicha alcohólica $4.200, restringida "Sí" |
| 2 | Buscar `chi` | 2 filas ("2 de 4 bebidas") |
| 3 | Vender 2 Pisco Sour | `Venta autorizada: 2 x Pisco Sour \| Total: $7000` |
| 3 | Vender 5 Pisco Sour | `Venta rechazada: 5 unidades de Pisco Sour superan el limite de 3 por cliente.` |
| 3 | Vender 1 Chicha alcohólica | `Venta rechazada: Chicha tiene la venta restringida.` |
| — | Vender 6 Mote con Huesillo | `Venta autorizada: 6 x Mote con Huesillo \| Total: $12000` |
| — | Editar / Eliminar sin selección | Alert "Ninguna bebida seleccionada" |
| — | Eliminar y cancelar | La fila permanece |
| — | Cerrar y reabrir | Las 4 bebidas vuelven, con tipo y restricción |
| 4 | Borrar `data/bebidas.json` y abrir | Tabla vacía, sin error |
| 5 | Escribir basura en el JSON y abrir | Alert "No se pudieron cargar los datos"; la app sigue abierta |

---

## Paso 9 — Errores frecuentes y su solución

| Síntoma | Causa | Solución |
|---|---|---|
| `Module javafx.controls not found` o `JavaFX runtime components are missing` | Se ejecutó la clase con el botón Run del IDE sin module path | Ejecuta con `mvn javafx:run` o configura el IDE para usar Maven |
| `release version 25 not supported` o `class file has wrong version 69.0` | El IDE o Maven usa un JDK anterior al 25 | Configura el SDK del proyecto en JDK 25 (en IntelliJ: *Project Structure → SDK*) y revisa `mvn -v` |
| `IllegalAccessException ... does not open cl.dsy1102.fonda.controller` | Falta `opens ... to javafx.fxml` | Revisa `module-info.java` |
| `InvalidDefinitionException: Cannot construct instance of Bebida` | Falta `@JsonTypeInfo`/`@JsonSubTypes` o el JSON no tiene `"tipo"` | Anota `Bebida` y guarda con `writerFor(TypeReference)` |
| `Location is not set` al cargar FXML | Ruta del recurso mal escrita | Los FXML están en `/cl/dsy1102/fonda/view/`, con `/` inicial |
| `NullPointerException` en un `@FXML` | `fx:id` distinto del nombre del atributo | Mismo nombre exacto en FXML y controlador |
| La tabla no se actualiza | Se modificó una copia o se usó `tabla.getItems().add` | Modifica la `ObservableList` del repositorio |
| Columna vacía | El texto de `PropertyValueFactory` no coincide con el getter | `"volumenML"` ↔ `getVolumenML()` |
| Los datos no persisten | No se guardó tras cambiar, o se lee otro directorio de trabajo | `data/` es relativo a la carpeta desde donde se ejecuta (la raíz del proyecto con `mvn javafx:run`) |

---

## Paso 10 — Lista de verificación antes de entregar (EP2)

- [ ] `mvn compile` sin errores y `mvn javafx:run` abre la ventana.
- [ ] Se navega Principal ↔ Formulario ↔ Principal ↔ Venta sin excepciones en consola.
- [ ] Los controladores no contienen `File`, `Path`, `ObjectMapper` ni el nombre del archivo.
- [ ] Todas las altas, ediciones y bajas pasan por `Repository` y la tabla se actualiza sola.
- [ ] La búsqueda filtra mientras se escribe y el orden por columna sigue funcionando.
- [ ] El formulario rechaza campos vacíos, texto en campos numéricos y valores fuera de rango, con `Alert`.
- [ ] Al reiniciar, cada bebida conserva su subtipo, atributos y venta restringida.
- [ ] Sin archivo JSON la app inicia vacía; con el archivo dañado muestra un `Alert` y no se cierra.
- [ ] `IOException` solo aparece en el DAO; hacia arriba viaja como `PersistenciaException`.
- [ ] El historial de Git muestra el avance por capas: modelo → persistencia → interfaz.
- [ ] El ZIP incluye `.git` y excluye `target/` y `data/`.
