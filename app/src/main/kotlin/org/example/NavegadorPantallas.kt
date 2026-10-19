package org.example

import javafx.scene.Parent
import javafx.scene.layout.StackPane

/**
 * Orquestador de navegación entre las pantallas de la aplicación.
 *
 * Recibe por constructor el contenedor raíz y la abstracción
 * [ServiciosAplicacion] (DIP). Conserva las pantallas con suscripciones
 * reactivas para no duplicar listeners al volver a ellas.
 */
class NavegadorPantallas(
    private val contenedor: StackPane,
    private val servicios: ServiciosAplicacion
) {

    /** Pantalla de simulación reutilizada. */
    private var pantallaSimulacion: PrincipianteSimulationScreen? = null

    /** Pantalla avanzada reutilizada. */
    private var pantallaAvanzada: ModoAvanzadoScreen? = null

    /** Muestra la pantalla de selección de modo. */
    fun mostrarSeleccionModo() {
        servicios.control().reiniciar()
        cambiarPantalla(
            ModeSelectionScreen { modo ->
                when (modo) {
                    ModoSimulacion.PRINCIPIANTE -> mostrarSeleccionPlaneta()
                    ModoSimulacion.ESTANDAR -> mostrarModoAvanzado()
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
        val pantalla = pantallaSimulacion ?: PrincipianteSimulationScreen(
            estado = servicios.contexto(),
            comandos = servicios.contexto(),
            control = servicios.control(),
            series = ConstructorSeriesCinematica(servicios.calculo()),
            explicador = servicios.explicador(),
            validador = servicios.validador(),
            renderizadorAnimacion = RenderizadorCaidaJavaFx(CatalogoEstilosEntorno()),
            onCambiarPlaneta = { mostrarSeleccionPlaneta() },
            onCambiarModo = { mostrarSeleccionModo() }
        ).also { pantallaSimulacion = it }

        cambiarPantalla(pantalla)
    }

    /** Muestra el desafío interactivo del Modo Avanzado. */
    fun mostrarModoAvanzado() {
        servicios.control().reiniciar()
        val pantalla = pantallaAvanzada ?: ModoAvanzadoScreen(
            desafio = servicios.desafio(),
            estado = servicios.contexto(),
            renderizadorAnimacion = RenderizadorCaidaJavaFx(CatalogoEstilosEntorno()),
            comandos = servicios.contexto(),
            control = servicios.control(),
            servicioExportacion = servicios.exportacion(),
            catalogoExportadores = servicios.catalogoExportadores(),
            selectorArchivo = servicios.selectorArchivo(),
            onCambiarModo = { mostrarSeleccionModo() }
        ).also { pantallaAvanzada = it }

        pantalla.sincronizarEscenario()
        cambiarPantalla(pantalla)
    }

    /** Intercambia la pantalla visible en el contenedor raíz. */
    private fun cambiarPantalla(pantalla: Parent) {
        contenedor.children.setAll(pantalla)
    }
}
