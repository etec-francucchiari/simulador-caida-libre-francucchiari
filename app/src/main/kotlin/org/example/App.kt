package org.example

import javafx.application.Application

/**
 * Punto de entrada de la aplicación.
 *
 * [main] es el único punto estático exigido por la JVM y delega en un lanzador
 * instanciable ([LanzadorAplicacion]), evitando clases compuestas únicamente
 * por métodos estáticos.
 */
fun main() {
    LanzadorAplicacion().iniciar()
}

/**
 * Servicio instanciable que arranca la interfaz gráfica JavaFX.
 */
class LanzadorAplicacion {

    /**
     * Inicia la aplicación JavaFX.
     */
    fun iniciar() {
        Application.launch(SimuladorApp::class.java)
    }
}
