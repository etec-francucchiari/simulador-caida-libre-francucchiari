package org.example

import kotlin.test.*

class AppTest {
    /**
     * Pruebas unitarias de verificación estructural de la clase principal de la
     * interfaz gráfica JavaFX ([SimuladorApp]).
     *
     * Comprueba que [SimuladorApp] herede de [javafx.application.Application]
     * para que pueda ser iniciada por [LanzadorAplicacion] desde el adaptador
     * de entrada [PuntoEntrada].
     */

    /**
     * Verifica mediante reflexión que [SimuladorApp] sea subclase asignable a
     * [javafx.application.Application].
     */
    @Test
    fun simuladorAppExiste() {
        val claseApp = SimuladorApp::class.java
        assertTrue(
            javafx.application.Application::class.java.isAssignableFrom(claseApp),
            "SimuladorApp debe extender de Application"
        )
    }
}
