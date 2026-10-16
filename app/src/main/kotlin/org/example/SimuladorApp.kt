package org.example

import javafx.application.Application
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.stage.Stage

/**
 * Punto de entrada y orquestador de navegación de la interfaz JavaFX.
 *
 * Implementa un flujo por pantallas para evitar saturar al usuario:
 * 1. [ModeSelectionScreen]: selección del modo (Principiante / Avanzado).
 * 2. [PlanetSelectionScreen]: selección del entorno gravitatorio.
 * 3. [PrincipianteSimulationScreen]: simulación guiada por pestañas.
 *
 * Actúa como raíz de composición (composition root): construye los servicios
 * instanciables y los inyecta por constructor en cada pantalla, aplicando
 * inversión de dependencias (DIP).
 *
 * No contiene `object` ni utilidades estáticas: los valores de configuración
 * son campos [private] de la instancia.
 */
class SimuladorApp : Application() {

    private val tituloVentana = "Simulador de Caída Libre"
    private val anchoVentana = 1180.0
    private val altoVentana = 780.0
    private val anchoMinimo = 980.0
    private val altoMinimo = 660.0

    // Servicios de dominio y estado (dependencias inyectables).
    private val configuracion = ConfiguracionFisica()
    private val calculo = CalculoCaidaLibre()
    private val explicador = ExplicadorFisicaPasoAPaso(calculo)
    private val validador = ValidadorParametrosIniciales(configuracion)
    private val catalogo = CatalogoPlanetas()

    private val gestorEstado =
        GestorEstadoSimulacion(configuracion, Planeta.TIERRA, calculo)
    private val controladorSimulacion =
        ControladorSimulacion(gestorEstado)

    private lateinit var contenedorRaiz: StackPane

    /** Pantalla de simulación reutilizada para no duplicar suscripciones reactivas. */
    private var pantallaSimulacion: PrincipianteSimulationScreen? = null

    override fun start(stage: Stage) {
        stage.title = tituloVentana

        contenedorRaiz = StackPane()
        contenedorRaiz.style = "-fx-background-color: #f8fafc;"

        val scene = Scene(contenedorRaiz, anchoVentana, altoVentana)
        scene.stylesheets.add(javaClass.getResource("/estilos.css")?.toExternalForm())

        mostrarSeleccionModo()

        stage.scene = scene
        stage.minWidth = anchoMinimo
        stage.minHeight = altoMinimo
        stage.isResizable = true
        stage.show()
    }

    /**
     * Pantalla 1: selección del modo de simulación.
     */
    private fun mostrarSeleccionModo() {
        controladorSimulacion.reiniciar()
        cambiarPantalla(
            ModeSelectionScreen { modo ->
                when (modo) {
                    ModoSimulacion.PRINCIPIANTE -> mostrarSeleccionPlaneta()
                    ModoSimulacion.ESTANDAR -> mostrarSeleccionPlaneta()
                }
            }
        )
    }

    /**
     * Pantalla 2: selección del planeta/entorno gravitatorio.
     */
    private fun mostrarSeleccionPlaneta() {
        controladorSimulacion.reiniciar()
        cambiarPantalla(
            PlanetSelectionScreen(
                catalogo = catalogo,
                onPlanetaSeleccionado = { planeta ->
                    gestorEstado.establecerPlaneta(planeta)
                    mostrarSimulacion()
                },
                onVolver = { mostrarSeleccionModo() }
            )
        )
    }

    /**
     * Pantalla 3: simulación guiada del Modo Principiante.
     */
    private fun mostrarSimulacion() {
        controladorSimulacion.reiniciar()
        val pantalla = pantallaSimulacion ?: PrincipianteSimulationScreen(
            estado = gestorEstado,
            comandos = gestorEstado,
            control = controladorSimulacion,
            calculo = calculo,
            explicador = explicador,
            validador = validador,
            onCambiarPlaneta = { mostrarSeleccionPlaneta() },
            onCambiarModo = { mostrarSeleccionModo() }
        ).also { pantallaSimulacion = it }

        cambiarPantalla(pantalla)
    }

    /**
     * Intercambia la pantalla visible en el contenedor raíz.
     */
    private fun cambiarPantalla(pantalla: Parent) {
        contenedorRaiz.children.setAll(pantalla)
    }
}
