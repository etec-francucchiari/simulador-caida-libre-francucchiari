package org.example

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

/**
 * Pantalla 2 - Selección de Planeta/Entorno.
 *
 * Permite elegir el entorno gravitatorio con tarjetas visuales:
 * - Tierra (g = 9.81 m/s²)
 * - Luna (g = 1.62 m/s²)
 * - Marte (g = 3.71 m/s²)
 * - Personalizado (ingresar g manualmente)
 *
 * Incluye botón "Volver" a la selección de modo.
 *
 * Responsabilidad Única (SRP): Solo gestiona la selección del planeta/entorno.
 */
class PlanetSelectionScreen(
    private val catalogo: CatalogoPlanetas,
    private val onPlanetaSeleccionado: (EntornoGravitatorio) -> Unit,
    private val onVolver: () -> Unit
) : VBox(20.0) {

    init {
        alignment = Pos.TOP_CENTER
        padding = Insets(30.0)
        style = "-fx-background-color: linear-gradient(to bottom, #f8fafc, #e2e8f0);"
        maxWidth = Double.MAX_VALUE
        maxHeight = Double.MAX_VALUE

        // Barra superior con botón Volver
        val barraSuperior = HBox()
        barraSuperior.alignment = Pos.CENTER_LEFT
        barraSuperior.maxWidth = Double.MAX_VALUE

        val botonVolver = Button("← Volver")
        botonVolver.font = Font.font("System", FontWeight.MEDIUM, 13.0)
        botonVolver.style = """
            -fx-background-color: transparent;
            -fx-text-fill: #64748b;
            -fx-padding: 8 16;
            -fx-background-radius: 8;
            -fx-cursor: hand;
        """
        botonVolver.setOnAction { onVolver() }
        botonVolver.onMouseEntered = { botonVolver.style = """
            -fx-background-color: #e2e8f0;
            -fx-text-fill: #334155;
            -fx-padding: 8 16;
            -fx-background-radius: 8;
            -fx-cursor: hand;
        """ }
        botonVolver.onMouseExited = { botonVolver.style = """
            -fx-background-color: transparent;
            -fx-text-fill: #64748b;
            -fx-padding: 8 16;
            -fx-background-radius: 8;
            -fx-cursor: hand;
        """ }

        barraSuperior.children.add(botonVolver)

        // Título
        val titulo = Label("Selecciona el Entorno Gravitatorio")
        titulo.font = Font.font("System", FontWeight.BOLD, 28.0)
        titulo.style = "-fx-text-fill: #1e293b;"

        val subtitulo = Label("La gravedad determina la aceleración de la caída (g)")
        subtitulo.font = Font.font("System", 15.0)
        subtitulo.style = "-fx-text-fill: #64748b;"

        // Grid de tarjetas de planetas
        val gridPlanetas = VBox(16.0)
        gridPlanetas.alignment = Pos.CENTER
        gridPlanetas.maxWidth = 560.0

        // Tarjetas predefinidas
        for (planeta in catalogo.predefinidos()) {
            gridPlanetas.children.add(crearTarjetaPlaneta(planeta))
        }

        // Tarjeta personalizada
        gridPlanetas.children.add(crearTarjetaPersonalizada())

        children.addAll(barraSuperior, titulo, subtitulo, gridPlanetas)
    }

    private fun crearTarjetaPlaneta(planeta: EntornoGravitatorio): VBox {
        val tarjeta = VBox(8.0)
        tarjeta.alignment = Pos.CENTER
        tarjeta.padding = Insets(20.0, 24.0, 20.0, 24.0)
        tarjeta.maxWidth = Double.MAX_VALUE
        tarjeta.style = """
            -fx-background-color: white;
            -fx-background-radius: 12;
            -fx-border-color: #e2e8f0;
            -fx-border-width: 1.5;
            -fx-border-radius: 12;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 8, 0, 0, 2);
            -fx-cursor: hand;
        """

        val filaSuperior = HBox(12.0)
        filaSuperior.alignment = Pos.CENTER_LEFT

        val icono = Label(obtenerIconoPlaneta(planeta.nombre()))
        icono.font = Font.font(32.0)

        val info = VBox(2.0)
        val nombre = Label(planeta.nombre())
        nombre.font = Font.font("System", FontWeight.BOLD, 20.0)
        nombre.style = "-fx-text-fill: #1e293b;"

        val gravedad = Label("g = ${planeta.gravedadFormateada()} m/s²")
        gravedad.font = Font.font("System", FontWeight.BOLD, 16.0)
        gravedad.style = "-fx-text-fill: #3b82f6;"

        val descripcion = Label(planeta.descripcion())
        descripcion.font = Font.font("System", 12.0)
        descripcion.style = "-fx-text-fill: #64748b;"

        info.children.addAll(nombre, gravedad, descripcion)
        HBox.setHgrow(info, javafx.scene.layout.Priority.ALWAYS)

        val flecha = Label("→")
        flecha.font = Font.font("System", FontWeight.BOLD, 24.0)
        flecha.style = "-fx-text-fill: #cbd5e1;"

        filaSuperior.children.addAll(icono, info, flecha)
        tarjeta.children.add(filaSuperior)

        tarjeta.setOnMouseClicked { onPlanetaSeleccionado(planeta) }
        tarjeta.onMouseEntered = { tarjeta.style = """
            -fx-background-color: #f8fafc;
            -fx-background-radius: 12;
            -fx-border-color: #3b82f6;
            -fx-border-width: 2;
            -fx-border-radius: 12;
            -fx-effect: dropshadow(three-pass-box, rgba(59,130,246,0.15), 12, 0, 0, 4);
            -fx-cursor: hand;
        """ }
        tarjeta.onMouseExited = { tarjeta.style = """
            -fx-background-color: white;
            -fx-background-radius: 12;
            -fx-border-color: #e2e8f0;
            -fx-border-width: 1.5;
            -fx-border-radius: 12;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 8, 0, 0, 2);
            -fx-cursor: hand;
        """ }

        return tarjeta
    }

    private fun crearTarjetaPersonalizada(): VBox {
        val tarjeta = VBox(12.0)
        tarjeta.alignment = Pos.CENTER
        tarjeta.padding = Insets(20.0, 24.0, 20.0, 24.0)
        tarjeta.maxWidth = Double.MAX_VALUE
        tarjeta.style = """
            -fx-background-color: #fefce8;
            -fx-background-radius: 12;
            -fx-border-color: #f59e0b;
            -fx-border-width: 2;
            -fx-border-radius: 12;
            -fx-border-style: dashed;
            -fx-cursor: hand;
        """

        val filaIcono = HBox(12.0)
        filaIcono.alignment = Pos.CENTER_LEFT

        val icono = Label("⚙️")
        icono.font = Font.font(32.0)

        val info = VBox(2.0)
        val nombre = Label("Entorno Personalizado")
        nombre.font = Font.font("System", FontWeight.BOLD, 20.0)
        nombre.style = "-fx-text-fill: #1e293b;"

        val gravedad = Label("Define tu propia gravedad (g)")
        gravedad.font = Font.font("System", 14.0)
        gravedad.style = "-fx-text-fill: #f59e0b;"

        info.children.addAll(nombre, gravedad)
        HBox.setHgrow(info, javafx.scene.layout.Priority.ALWAYS)

        filaIcono.children.addAll(icono, info)
        tarjeta.children.add(filaIcono)

        // Campo de entrada para gravedad personalizada
        val campoGravedad = TextField()
        campoGravedad.promptText = "Ej: 9.81, 1.62, 3.71, 24.79..."
        campoGravedad.font = Font.font("System", 14.0)
        campoGravedad.prefWidth = 280.0
        campoGravedad.maxWidth = 280.0
        campoGravedad.style = """
            -fx-background-color: white;
            -fx-background-radius: 8;
            -fx-border-color: #d1d5db;
            -fx-border-width: 1.5;
            -fx-border-radius: 8;
            -fx-padding: 10 14;
        """

        val botonAplicar = Button("Usar esta gravedad")
        botonAplicar.font = Font.font("System", FontWeight.BOLD, 13.0)
        botonAplicar.prefWidth = 180.0
        botonAplicar.style = """
            -fx-background-color: #f59e0b;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-radius: 8;
            -fx-padding: 10 20;
            -fx-cursor: hand;
        """
        botonAplicar.disableProperty().bind(campoGravedad.textProperty().isEmpty())

        val accionAplicar = {
            val texto = campoGravedad.text.trim().replace(',', '.')
            texto.toDoubleOrNull()?.let { g ->
                if (catalogo.esGravedadValida(g)) {
                    onPlanetaSeleccionado(catalogo.personalizado(g))
                }
            }
        }
        botonAplicar.setOnAction { accionAplicar() }
        campoGravedad.setOnAction { accionAplicar() }

        tarjeta.children.addAll(campoGravedad, botonAplicar)

        return tarjeta
    }

    private fun obtenerIconoPlaneta(nombre: String): String = when (nombre) {
        "Tierra" -> "🌍"
        "Luna" -> "🌙"
        "Marte" -> "🔴"
        "Júpiter" -> "🪐"
        else -> "🪐"
    }
}