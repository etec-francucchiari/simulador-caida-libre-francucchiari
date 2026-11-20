package org.example

import javafx.scene.Parent
import javafx.scene.layout.StackPane

class NavegadorPantallas(
    contenedor: StackPane,
    servicios: ServiciosAplicacion = ComposicionAplicacion()
) {
    /**
     * Orquestador de navegación entre las pantallas de la aplicación.
     *
     * Recibe por constructor el contenedor raíz y la abstracción
     * [ServiciosAplicacion] (DIP). Conserva las pantallas con suscripciones
     * reactivas para no duplicar listeners al volver a ellas.
     */

    private val navegacion = Triple(
        contenedor,
        servicios,
        arrayOfNulls<Parent>(2)
    )

    /** Muestra la pantalla de selección de modo. */
    fun mostrarSeleccionModo() {
        navegacion.second.control().reiniciar()
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
        navegacion.second.control().reiniciar()
        cambiarPantalla(
            PlanetSelectionScreen(
                catalogo = navegacion.second.catalogo(),
                onPlanetaSeleccionado = { planeta ->
                    navegacion.second.contexto().establecerPlaneta(planeta)
                    mostrarSimulacion()
                },
                onVolver = { mostrarSeleccionModo() }
            )
        )
    }

    /** Muestra la simulación guiada del Modo Principiante. */
    fun mostrarSimulacion() {
        navegacion.second.control().reiniciar()
        val existente = navegacion.third[0]
        val pantalla = existente ?: navegacion.second.crearPantallaPrincipiante(
            onCambiarPlaneta = { mostrarSeleccionPlaneta() },
            onCambiarModo = { mostrarSeleccionModo() }
        ).also { navegacion.third[0] = it }
        cambiarPantalla(pantalla)
    }

    /** Muestra el desafío interactivo del Modo Avanzado. */
    fun mostrarModoAvanzado() {
        navegacion.second.control().reiniciar()
        val existente = navegacion.third[1] as? ModoAvanzadoScreen
        val pantalla = existente ?: navegacion.second.crearPantallaAvanzada(
            onCambiarModo = { mostrarSeleccionModo() }
        ).also { navegacion.third[1] = it }
        pantalla.sincronizarEscenario()
        cambiarPantalla(pantalla)
    }

    private fun cambiarPantalla(pantalla: Parent) {
        navegacion.first.children.setAll(pantalla)
    }
}
