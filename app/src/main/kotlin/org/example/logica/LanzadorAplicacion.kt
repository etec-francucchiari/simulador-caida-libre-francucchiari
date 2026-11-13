package org.example

import javafx.application.Application

class LanzadorAplicacion {
    /**
     * Servicio instanciable responsable de inicializar el entorno gráfico de
     * JavaFX y lanzar la clase de aplicación principal ([SimuladorApp]).
     *
     * Se separa del adaptador de entrada de la máquina virtual ([PuntoEntrada])
     * para garantizar que toda la lógica en Kotlin pertenezca a clases
     * instanciables sin recurrir a funciones libres de nivel superior.
     */

    /**
     * Inicia el ciclo de vida de la aplicación JavaFX delegando en
     * [Application.launch] sobre [SimuladorApp].
     */
    fun iniciar() {
        Application.launch(SimuladorApp::class.java)
    }
}
