package org.example

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

/**
 * Pantalla del Modo Avanzado: desafío interactivo de cálculo manual más
 * exportación de resultados.
 *
 * Orquesta las tres etapas del desafío delegando la construcción de cada vista
 * en componentes especializados ([VistaEtapaFormula],
 * [VistaEtapaSustitucion], [VistaEtapaValidacion]) y la exportación en
 * [BarraExportacion] (SRP).
 *
 * Todas las dependencias se reciben por constructor como abstracciones
 * ([DesafioAvanzado], [EstadoSimulacion], [ComandosSimulacion],
 * [ControlReproduccion], [ExportacionDatos], [CatalogoDeExportadores],
 * [SelectorArchivoExportacion]), aplicando inversión de dependencias (DIP).
 */
class ModoAvanzadoScreen(
    private val desafio: DesafioAvanzado,
    private val estado: EstadoSimulacion,
    private val renderizadorAnimacion: RenderizadorAnimacionCaida,
    private val comandos: ComandosSimulacion,
    private val control: ControlReproduccion,
    servicioExportacion: ExportacionDatos,
    catalogoExportadores: CatalogoDeExportadores,
    selectorArchivo: SelectorArchivoExportacion,
    private val onCambiarModo: () -> Unit
) : BorderPane() {

    private val fabrica = FabricaControles()

    private val etiquetaEnunciado = Label()
    private val etiquetaEtapa = Label()
    private val contenedorContenido = VBox(14.0)

    /** Panel de animación creado una sola vez para no duplicar suscripciones. */
    private val panelAnimacion = PanelAnimacionCaida(estado, renderizadorAnimacion)

    private val barraExportacion = BarraExportacion(
        servicioExportacion = servicioExportacion,
        catalogoExportadores = catalogoExportadores,
        selectorArchivo = selectorArchivo,
        proveedorVentana = { scene?.window },
        fabrica = fabrica
    )

    init {
        style = "-fx-background-color: #f8fafc;"
        top = construirEncabezado()
        center = construirCentro()
        bottom = construirPie()
        aplicarEscenarioDelEjercicio()
        refrescar()
    }

    // ---------------------------------------------------------------------
    // Estructura fija
    // ---------------------------------------------------------------------

    private fun construirEncabezado(): VBox {
        val encabezado = VBox(8.0)
        encabezado.padding = Insets(14.0, 20.0, 14.0, 20.0)
        encabezado.style =
            "-fx-background-color: #f5f3ff; -fx-border-color: #ddd6fe; -fx-border-width: 0 0 1 0;"

        val fila = HBox(12.0)
        fila.alignment = Pos.CENTER_LEFT

        val titulo = Label("Modo Avanzado · Desafío Interactivo")
        titulo.font = Font.font("System", FontWeight.BOLD, 20.0)
        titulo.style = "-fx-text-fill: #4c1d95;"

        val espaciador = Region()
        HBox.setHgrow(espaciador, Priority.ALWAYS)

        val botonNuevo = fabrica.botonSecundario("Nuevo Ejercicio")
        botonNuevo.setOnAction { iniciarNuevoEjercicio() }

        val botonCambiar = fabrica.botonSecundario("Cambiar Modo")
        botonCambiar.setOnAction { onCambiarModo() }

        fila.children.addAll(titulo, espaciador, botonNuevo, botonCambiar)

        etiquetaEtapa.font = Font.font("System", FontWeight.BOLD, 13.0)
        etiquetaEtapa.style = "-fx-text-fill: #6d28d9;"

        etiquetaEnunciado.isWrapText = true
        etiquetaEnunciado.font = Font.font("System", 14.0)
        etiquetaEnunciado.style =
            "-fx-text-fill: #1e293b; -fx-background-color: white; -fx-padding: 10; " +
                "-fx-background-radius: 8; -fx-border-color: #e9d5ff; -fx-border-radius: 8;"

        encabezado.children.addAll(fila, etiquetaEtapa, etiquetaEnunciado)
        return encabezado
    }

    private fun construirCentro(): ScrollPane {
        contenedorContenido.padding = Insets(18.0)
        contenedorContenido.alignment = Pos.TOP_CENTER

        val scroll = ScrollPane(contenedorContenido)
        scroll.isFitToWidth = true
        scroll.style = "-fx-background: #f8fafc; -fx-background-color: #f8fafc;"
        return scroll
    }

    private fun construirPie(): VBox {
        val pie = VBox(0.0)
        pie.children.addAll(barraExportacion, BarraControlesSimulacion(estado, control))
        return pie
    }

    // ---------------------------------------------------------------------
    // Contenido dinámico según la etapa
    // ---------------------------------------------------------------------

    private fun refrescar() {
        etiquetaEtapa.text = desafio.etapa().toString()
        etiquetaEnunciado.text = desafio.ejercicio().enunciado()
        contenedorContenido.children.setAll(listOf(construirVistaEtapa()))
    }

    private fun construirVistaEtapa(): Node = when (desafio.etapa()) {
        EtapaDesafio.FORMULA ->
            VistaEtapaFormula(desafio, fabrica) { refrescar() }.construir()

        EtapaDesafio.SUSTITUCION ->
            VistaEtapaSustitucion(desafio, fabrica) { refrescar() }.construir()

        EtapaDesafio.VALIDACION ->
            VistaEtapaValidacion(desafio, fabrica, panelAnimacion).construir()
    }

    // ---------------------------------------------------------------------
    // Acciones
    // ---------------------------------------------------------------------

    private fun iniciarNuevoEjercicio() {
        control.reiniciar()
        desafio.nuevoEjercicio()
        aplicarEscenarioDelEjercicio()
        barraExportacion.reiniciarMensaje()
        refrescar()
    }

    /**
     * Reaplica al estado de la simulación el escenario del ejercicio activo.
     *
     * Se invoca desde la raíz de composición al volver a mostrar esta pantalla
     * para evitar que el estado global quede desincronizado con el ejercicio.
     */
    fun sincronizarEscenario() {
        aplicarEscenarioDelEjercicio()
    }

    private fun aplicarEscenarioDelEjercicio() {
        val ejercicio = desafio.ejercicio()
        comandos.establecerPlaneta(ejercicio.entorno())
        comandos.establecerAlturaInicial(ejercicio.alturaInicial())
        comandos.establecerVelocidadInicial(ejercicio.velocidadInicial())
        control.reiniciar()
        // El escenario del desafío se aplica sobre la máquina de estados,
        // que inicia en reposo hasta que el usuario ejecute la simulación.
        comandos.cambiarEstado(EstadoEjecucionEnModoEjercicio(desafio))
    }
}
