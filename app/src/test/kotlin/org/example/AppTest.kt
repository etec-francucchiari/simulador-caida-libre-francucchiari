package org.example

import kotlin.test.Test
import kotlin.test.assertTrue

class AppTest {

    @Test
    fun simuladorAppExiste() {
        val claseApp = SimuladorApp::class.java
        assertTrue(
            javafx.application.Application::class.java.isAssignableFrom(claseApp),
            "SimuladorApp debe extender de Application"
        )
    }
}
