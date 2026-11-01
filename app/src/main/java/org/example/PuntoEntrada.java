package org.example;

public final class PuntoEntrada {
    /**
     * Adaptador de entrada de la JVM.
     *
     * No contiene lógica de negocio: delega inmediatamente en el lanzador
     * instanciable {@link LanzadorAplicacion}, de modo que el código Kotlin no
     * declare funciones de nivel superior.
     */

    private PuntoEntrada() {
    }

    public static void main(String[] args) {
        new LanzadorAplicacion().iniciar();
    }
}
