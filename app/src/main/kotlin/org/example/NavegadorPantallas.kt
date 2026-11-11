package org.example

import javafx.scene.Parent
import javafx.scene.layout.StackPane

class NavegadorPantallas(
    private val contenedor: StackPane,
    private val servicios: ServiciosAplicacion
) {
    constructor(contenedor: StackPane, nucleo: NucleoSimulacion) : this(contenedor, ComposicionAplicacion(nucleo))
    /**
     * Orquestador de navegación entre las pantallas de la aplicación.
     *
     * Recibe por constructor el contenedor raíz y la abstracción
     * [ServiciosAplicacion] (DIP). Conserva las pantallas con suscripciones
     * reactivas para no duplicar listeners al volver a ellas.
     */

    private var pantallaSimulacion: PrincipianteSimulationScreen? = null
    private var pantallaAvanzada: ModoAvanzadoScreen? = null

    /** Muestra la pantalla de selección de modo. */
    fun mostrarSeleccionModo() {
        servicios.control().reiniciar()
        cambiarPantalla(
            ModeSelectionScreen { modo ->
                if (modo.esPrincipiante()) {
                    mostrarSeleccionPlaneta()
                } else {
                    mostrarModoAvanzado()
                }
            }
        )
    }

    /** Muestra la pantalla de selección del entorno gravitatorio. */
    fun mostrarSeleccionPlaneta() {
        servicios.control().reiniciar()
        cambiarPantalla(
            PlanetSelectionScreen(
                catalogo = servicios.catalogo(),
                onPlanetaSeleccionado = { planeta ->
                    servicios.contexto().establecerPlaneta(planeta)
                    mostrarSimulacion()
                },
                onVolver = { mostrarSeleccionModo() }
            )
        )
    }

    /** Muestra la simulación guiada del Modo Principiante. */
    fun mostrarSimulacion() {
        servicios.control().reiniciar()
        val pantalla = pantallaSimulacion ?: servicios.crearPantallaPrincipiante(
            onCambiarPlaneta = { mostrarSeleccionPlaneta() },
            onCambiarModo = { mostrarSeleccionModo() }
        ).also { pantallaSimulacion = it }
        cambiarPantalla(pantalla)
    }

    /** Muestra el desafío interactivo del Modo Avanzado. */
    fun mostrarModoAvanzado() {
        servicios.control().reiniciar()
        val pantalla = pantallaAvanzada ?: servicios.crearPantallaAvanzada(
            onCambiarModo = { mostrarSeleccionModo() }
        ).also { pantallaAvanzada = it }
        pantalla.sincronizarEscenario()
        cambiarPantalla(pantalla)
    }

    private fun cambiarPantalla(pantalla: Parent) {
        contenedor.children.setAll(pantalla)
    }
}
