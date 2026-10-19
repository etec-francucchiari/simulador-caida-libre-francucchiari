package org.example

import javafx.application.Application

/**
 * Servicio instanciable que arranca la interfaz gráfica JavaFX.
 *
 * Se separa del adaptador de entrada [PuntoEntrada] para que la lógica de
 * arranque viva en una clase instanciable y no en una utilidad estática.
 */
class LanzadorAplicacion {

    /**
     * Inicia la aplicación JavaFX.
     */
    fun iniciar() {
        Application.launch(SimuladorApp::class.java)
    }
}
