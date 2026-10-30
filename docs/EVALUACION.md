<!-- evaluacion:inicio -->
# Evaluación — etec-francucchiari/simulador-caida-libre-francucchiari

> Documento **generado automáticamente** por la cátedra de Programación II (RepoSense + análisis de calidad POO/SOLID). **No editar a mano**: se regenera en cada evaluación.
> Rama evaluada: `main`. Análisis **heurístico** (regex + métricas): es una criba para orientar la revisión, **no una nota**.
> Generado: 2026-10-30.

## 1. Veredicto

| Aspecto | Resultado |
|---|---|
| Índice de calidad POO/SOLID | **3/100** |
| Código (LOC) | 6,129 |
| Archivos de código | 120 |
| Clases (bien usadas) | 118 (23 OK) |
| Código dentro de clases (%POO) | 79% |
| Funciones fuera de clases | 0 |
| Duplicación lógica | 3.4% |
| Señales S/O/L/I/D | 22/0/0/0/34 |

> Este informe **describe** lo que el análisis midió con los criterios ya establecidos de la materia. No es una nota.

### Semáforo de aprobación

Criterios de `CRITERIOS-CALIDAD.md`. **Aprobar** se juega con el perfil `estandar`; **destacar**, con el `exigente` (meta de excelencia).

| Semáforo | Resultado |
|---|---|
| Aprobar (`estandar`) | **no cumple** — 2/4 |
| Destacar (`exigente`) | **no cumple** — 2/5 |

**Criterios para aprobar**

| Criterio | Requisito | Tu resultado | Cumple |
|---|---|---|---|
| Código dentro de clases (%POO) | ≥ 85% | 79% | no |
| Duplicación lógica | ≤ 10% | 3.4% | sí |
| Funciones fuera de clases | ≤ 1 | 0 | sí |
| Señales POO/SOLID críticas | ninguna | Acoplamiento alto (CBO) | no |

**Criterios para destacar**

| Criterio | Requisito | Tu resultado | Cumple |
|---|---|---|---|
| Código dentro de clases (%POO) | ≥ 95% | 79% | no |
| Duplicación lógica | ≤ 5% | 3.4% | sí |
| Funciones fuera de clases | ≤ 1 | 0 | sí |
| Señales POO/SOLID críticas | ninguna | Clase demasiado grande (SRP), Baja cohesión (LCOM), Acoplamiento alto (CBO) | no |
| Índice de calidad POO/SOLID | ≥ 60 | 3/100 | no |

## 2. Autoría (RepoSense)

| Autor | Commits | Código (LOC) | Tests (LOC) | Docs (LOC) | Días activos | % del código |
|---|---:|---:|---:|---:|---:|---:|
| francucchiari-code | 13 | 6,038 | 1,395 | 1 | 10 | 100% |

> _El LOC de autoría lo calcula RepoSense sobre el historial y puede diferir del LOC de código del análisis de calidad (que mira solo el estado de `main`)._

## 3. Aspectos evaluados

Cada aspecto indica **qué mide**, **por qué importa** y **tu resultado**. Cuando el análisis reportó una señal, se agregan preguntas para **investigar la causa** (no hay recetas: entender el porqué es parte de la evaluación).

### 1. Índice de calidad POO/SOLID (indice)

- **Qué mide:** Un resumen numérico (0-100) que combina la riqueza de clases bien usadas con la penalización por señales POO/SOLID y duplicación, y luego aplica topes duros.
- **Por qué importa:** Ordena y prioriza la revisión: un índice bajo indica varias señales que conviene mirar juntas.
- **Tu resultado:** índice **3/100**

### 2. Duplicación lógica (DRY) (duplicacion) — con señales

- **Qué mide:** Porcentaje de bloques de LÓGICA repetidos. Quedan fuera imports/includes, anotaciones, declaraciones de campos y comentarios/Javadoc/docstrings: esos bloques son boilerplate, no lógica.
- **Por qué importa:** Cada copia de lógica se mantiene por separado: un arreglo hay que repetirlo y los defectos se multiplican.
- **Tu resultado:** **3.4%** de duplicación lógica
- **Para investigar la causa:**
    - ¿El trabajo se repartió por pantallas o archivos y se integró copiando y pegando bloques?
    - ¿Qué zonas del sistema cambiaron juntas más de una vez (señal de lógica compartida)?
    - ¿Hubo un lugar común acordado para la lógica repetida antes de empezar, o cada integrante resolvió lo suyo?

### 3. Adopción de POO (% de código en clases) (poo) — sin señales

- **Qué mide:** Qué porción del código vive dentro de clases (no de funciones sueltas).
- **Por qué importa:** El diseño orientado a objetos es el eje de la materia: el comportamiento debería vivir como método de una clase.
- **Tu resultado:** **78.7%** del código dentro de clases

### 4. Funciones fuera de clases (sueltas) — sin señales

- **Qué mide:** Funciones de nivel de módulo que no pertenecen a ninguna clase (en esta materia el comportamiento debe ser método de una clase).
- **Por qué importa:** Son la forma más directa de código procedural en un proyecto que debería ser orientado a objetos.
- **Tu resultado:** **0** función(es) fuera de clases

### 5. Clase demasiado grande (SRP) (god) — con señales

- **Qué mide:** Clases con demasiadas líneas o métodos de LÓGICA. No cuentan los getters/setters triviales ni la cantidad de campos por sí sola.
- **Por qué importa:** Una clase que hace de todo cambia por muchos motivos y cuesta entenderla y probarla.
- **Tu resultado:** **4** clase(s) demasiado grande(s)
- **Para investigar la causa:**
    - ¿La clase creció por acumulación de responsabilidades?
    - ¿Qué partes de la clase se usan siempre juntas y cuáles no?
    - ¿Se podría describir su responsabilidad en una sola frase?

### 6. Baja cohesión (LCOM) (cohesion) — con señales

- **Qué mide:** Métodos y campos de una clase que casi no se relacionan entre sí.
- **Por qué importa:** Una unidad que cambia por motivos distintos y comparte poca información suele esconder más de una responsabilidad.
- **Tu resultado:** **1** clase(s) con baja cohesión
- **Para investigar la causa:**
    - ¿Qué métodos de la clase no comparten estado con el resto?
    - ¿Cambian por motivos distintos según la tarea?
    - ¿Se pensó su responsabilidad antes de escribirla, o se fue agregando?

### 7. Complejidad alta (complejidad) — sin señales

- **Qué mide:** Complejidad ciclomática: cantidad de caminos posibles dentro de un método o función (condicionales, bucles, casos).
- **Por qué importa:** Muchos caminos = difícil de seguir y de probar; suelen esconderse casos sin cubrir.
- **Tu resultado:** **0** método(s)/función(es) con complejidad alta

### 8. Métodos/funciones largos o con muchos parámetros (tamano) — con señales

- **Qué mide:** Tamaño de los métodos/funciones y cantidad de parámetros.
- **Por qué importa:** Una función que hace varias cosas cuesta leerla, nombrarla y reutilizarla.
- **Tu resultado:** **11** método(s)/función(es) largos o con muchos parámetros
- **Para investigar la causa:**
    - ¿La función creció por acumulación de pasos?
    - ¿Agrupa operaciones que podrían describirse por separado?
    - ¿Los parámetros representan una misma idea (un objeto) o cosas sueltas?

### 9. Acoplamiento alto (CBO) (acoplamiento) — con señales

- **Qué mide:** Cuánto depende una clase de otros tipos del proyecto.
- **Por qué importa:** Piezas muy enlazadas hacen que un cambio se propague y que sea difícil reemplazar partes.
- **Tu resultado:** **36** clase(s) muy acoplada(s)
- **Para investigar la causa:**
    - ¿La clase conoce detalles internos de muchas otras?
    - ¿Se puede cambiar una parte sin tocar las demás?
    - ¿Los colaboradores llegan como parámetros o se crean por dentro?

### 10. Herencia profunda (DIT) (herencia) — sin señales

- **Qué mide:** Profundidad de la cadena de herencia de una clase.
- **Por qué importa:** Jerarquías muy profundas vuelven difícil saber de dónde viene cada comportamiento.
- **Tu resultado:** **0** clase(s) con herencia profunda

### 11. Switch sobre tipos (OCP) (ocp) — sin señales

- **Qué mide:** Chequeos repetidos de tipo (`instanceof`/`is`/`typeof`) que equivalen a un switch sobre el tipo.
- **Por qué importa:** Cada tipo nuevo obliga a tocar varios lugares, en vez de extender con una implementación nueva.
- **Tu resultado:** **0** clase(s) con switch sobre tipos

### 12. Interfaz con demasiados métodos (ISP) (isp) — sin señales

- **Qué mide:** Interfaces o clases abstractas con muchos métodos declarados.
- **Por qué importa:** Obliga a implementar operaciones que no se usan y acopla a los clientes a métodos que no necesitan.
- **Tu resultado:** **0** interfaz(es) con demasiados métodos

### 13. Override vacío o 'no implementado' (LSP) (lsp) — sin señales

- **Qué mide:** Métodos sobrescritos que quedan vacíos o lanzan 'no implementado'.
- **Por qué importa:** Suele indicar que la jerarquía no representa bien el problema y rompe la sustitución entre clases.
- **Tu resultado:** **0** override(s) vacío(s) o 'no implementado'

### 14. Dependencia de concretos y falta de abstracciones (DIP) (dip) — con señales

- **Qué mide:** Instanciación directa de clases concretas y ausencia de interfaces o clases abstractas pese al acoplamiento.
- **Por qué importa:** Depender de implementaciones concretas dificulta cambiar o probar una parte sin arrastrar las demás.
- **Tu resultado:** **34** dependencia(s) de concretos sin abstracciones
- **Para investigar la causa:**
    - ¿La clase crea por dentro a sus colaboradores en vez de recibirlos?
    - ¿Hay puntos de variación que convendría expresar como abstracción?
    - ¿Cómo se probaría esa clase cambiando una implementación?

### 15. Clase sin métodos (solo datos) (data) — con señales

- **Qué mide:** Clases que solo acumulan campos y no tienen comportamiento.
- **Por qué importa:** Separar datos de comportamiento reparte por el proyecto la lógica que debería estar cerca de esos datos.
- **Tu resultado:** **17** clase(s) sin métodos (solo datos)
- **Para investigar la causa:**
    - ¿Dónde vive el comportamiento que opera sobre esos datos?
    - ¿La clase representa un concepto del dominio o un contenedor técnico?
    - ¿Se evita la lógica de negocio dentro de la clase por comodidad?

### 16. Campos públicos (encapsulación) (encapsulacion) — con señales

- **Qué mide:** Clases que exponen campos sin control de acceso.
- **Por qué importa:** Expone el estado interno y hace que cualquier cambio dependa de quién lo modifica desde afuera.
- **Tu resultado:** **9** clase(s) con campos públicos
- **Para investigar la causa:**
    - ¿Se accede al campo desde otras clases para evitar un método?
    - ¿Qué reglas deberían respetarse al modificar ese estado?
    - ¿Los campos públicos se usan más como configuración que como estado?

### 17. Clase solo con métodos estáticos (utilitaria) (static) — sin señales

- **Qué mide:** Clases donde todos los métodos son estáticos.
- **Por qué importa:** Es comportamiento procedural disfrazado de clase: no se instancia ni participa del modelo de objetos.
- **Tu resultado:** **0** clase(s) solo con métodos estáticos

## 4. Anexo: dónde mirar

Archivos donde el análisis encontró cada señal (sin número de línea, a propósito):

**Clase demasiado grande (SRP) (god)** — 4 archivo(s):
- `app/src/test/kotlin/org/example/CalculoCaidaLibreTest.kt`
- `app/src/test/kotlin/org/example/ControladorSimulacionTest.kt`
- `app/src/test/kotlin/org/example/ExplicadorFisicaPasoAPasoTest.kt`
- `app/src/test/kotlin/org/example/ValidadorParametrosInicialesTest.kt`

**Baja cohesión (LCOM) (cohesion)** — 1 archivo(s):
- `app/src/main/kotlin/org/example/GestorEstadoSimulacion.kt`

**Métodos/funciones largos o con muchos parámetros (tamano)** — 9 archivo(s):
- `app/src/main/kotlin/org/example/ExplicadorFisicaPasoAPaso.kt`
- `app/src/main/kotlin/org/example/GeneradorOpcionesFormula.kt`
- `app/src/main/kotlin/org/example/TarjetaEntorno.kt`
- `app/src/main/kotlin/org/example/TarjetaEntornoPersonalizado.kt`
- `app/src/main/kotlin/org/example/TarjetaFormulaDidactica.kt`
- `app/src/main/kotlin/org/example/VistaEtapaFormula.kt`
- `app/src/main/kotlin/org/example/VistaEtapaSustitucion.kt`
- `app/src/main/kotlin/org/example/VistaEtapaValidacion.kt`
- `app/src/test/kotlin/org/example/ControladorSimulacionTest.kt`

**Acoplamiento alto (CBO) (acoplamiento)** — 36 archivo(s):
- `app/src/main/kotlin/org/example/BarraControlesSimulacion.kt`
- `app/src/main/kotlin/org/example/BarraExportacion.kt`
- `app/src/main/kotlin/org/example/CatalogoEstilosEntorno.kt`
- `app/src/main/kotlin/org/example/CatalogoPlanetas.kt`
- `app/src/main/kotlin/org/example/ComposicionAplicacion.kt`
- `app/src/main/kotlin/org/example/ControladorDesafio.kt`
- `app/src/main/kotlin/org/example/EjercicioCaidaLibre.kt`
- `app/src/main/kotlin/org/example/EncabezadoSimulacion.kt`
- `app/src/main/kotlin/org/example/FabricaControles.kt`
- `app/src/main/kotlin/org/example/GestorEstadoSimulacion.kt`
- `app/src/main/kotlin/org/example/ModeSelectionScreen.kt`
- `app/src/main/kotlin/org/example/ModoAvanzadoScreen.kt`
- `app/src/main/kotlin/org/example/NavegadorPantallas.kt`
- `app/src/main/kotlin/org/example/PanelAnimacionCaida.kt`
- `app/src/main/kotlin/org/example/PanelExplicacionFisica.kt`
- `app/src/main/kotlin/org/example/PanelGraficosSimulacion.kt`
- `app/src/main/kotlin/org/example/PanelIndicadoresFisicos.kt`
- `app/src/main/kotlin/org/example/PanelParametrosIniciales.kt`
- `app/src/main/kotlin/org/example/PanelValoresInstantaneos.kt`
- `app/src/main/kotlin/org/example/PlanetSelectionScreen.kt`
- `app/src/main/kotlin/org/example/PrincipianteSimulationScreen.kt`
- `app/src/main/kotlin/org/example/RenderizadorCaidaJavaFx.kt`
- `app/src/main/kotlin/org/example/ServiciosAplicacion.kt`
- `app/src/main/kotlin/org/example/TarjetaEntorno.kt`
- `app/src/main/kotlin/org/example/TarjetaEntornoPersonalizado.kt`
- `app/src/main/kotlin/org/example/VistaEtapaFormula.kt`
- `app/src/main/kotlin/org/example/VistaEtapaSustitucion.kt`
- `app/src/main/kotlin/org/example/VistaEtapaValidacion.kt`
- `app/src/test/kotlin/org/example/ControladorDesafioTest.kt`
- `app/src/test/kotlin/org/example/ControladorSimulacionTest.kt`
- `app/src/test/kotlin/org/example/EstadoEjecucionTest.kt`
- `app/src/test/kotlin/org/example/GeneradorEjerciciosTest.kt`
- `app/src/test/kotlin/org/example/GeneradorOpcionesFormulaTest.kt`
- `app/src/test/kotlin/org/example/GeneradorOpcionesRespuestaTest.kt`
- `app/src/test/kotlin/org/example/GestorEstadoSimulacionTest.kt`
- `app/src/test/kotlin/org/example/ServicioExportacionTest.kt`

**Dependencia de concretos y falta de abstracciones (DIP) (dip)** — 34 archivo(s):
- `app/src/main/kotlin/org/example/BarraControlesSimulacion.kt`
- `app/src/main/kotlin/org/example/BarraExportacion.kt`
- `app/src/main/kotlin/org/example/CatalogoEstilosEntorno.kt`
- `app/src/main/kotlin/org/example/ComposicionAplicacion.kt`
- `app/src/main/kotlin/org/example/ControladorDesafio.kt`
- `app/src/main/kotlin/org/example/EjercicioCaidaLibre.kt`
- `app/src/main/kotlin/org/example/EncabezadoSimulacion.kt`
- `app/src/main/kotlin/org/example/FabricaControles.kt`
- `app/src/main/kotlin/org/example/GestorEstadoSimulacion.kt`
- `app/src/main/kotlin/org/example/ModeSelectionScreen.kt`
- `app/src/main/kotlin/org/example/ModoAvanzadoScreen.kt`
- `app/src/main/kotlin/org/example/NavegadorPantallas.kt`
- `app/src/main/kotlin/org/example/PanelAnimacionCaida.kt`
- `app/src/main/kotlin/org/example/PanelExplicacionFisica.kt`
- `app/src/main/kotlin/org/example/PanelGraficosSimulacion.kt`
- `app/src/main/kotlin/org/example/PanelIndicadoresFisicos.kt`
- `app/src/main/kotlin/org/example/PanelParametrosIniciales.kt`
- `app/src/main/kotlin/org/example/PanelValoresInstantaneos.kt`
- `app/src/main/kotlin/org/example/PlanetSelectionScreen.kt`
- `app/src/main/kotlin/org/example/PrincipianteSimulationScreen.kt`
- `app/src/main/kotlin/org/example/RenderizadorCaidaJavaFx.kt`
- `app/src/main/kotlin/org/example/TarjetaEntorno.kt`
- `app/src/main/kotlin/org/example/TarjetaEntornoPersonalizado.kt`
- `app/src/main/kotlin/org/example/VistaEtapaFormula.kt`
- `app/src/main/kotlin/org/example/VistaEtapaSustitucion.kt`
- `app/src/main/kotlin/org/example/VistaEtapaValidacion.kt`
- `app/src/test/kotlin/org/example/ControladorDesafioTest.kt`
- `app/src/test/kotlin/org/example/ControladorSimulacionTest.kt`
- `app/src/test/kotlin/org/example/EstadoEjecucionTest.kt`
- `app/src/test/kotlin/org/example/GeneradorEjerciciosTest.kt`
- `app/src/test/kotlin/org/example/GeneradorOpcionesFormulaTest.kt`
- `app/src/test/kotlin/org/example/GeneradorOpcionesRespuestaTest.kt`
- `app/src/test/kotlin/org/example/GestorEstadoSimulacionTest.kt`
- `app/src/test/kotlin/org/example/ServicioExportacionTest.kt`

**Clase sin métodos (solo datos) (data)** — 17 archivo(s):
- `app/src/main/kotlin/org/example/BarraControlesSimulacion.kt`
- `app/src/main/kotlin/org/example/CampoSustitucion.kt`
- `app/src/main/kotlin/org/example/ComposicionAplicacion.kt`
- `app/src/main/kotlin/org/example/ConfiguracionFisica.kt`
- `app/src/main/kotlin/org/example/DetallePasoFormula.kt`
- `app/src/main/kotlin/org/example/EstadoInstantaneo.kt`
- `app/src/main/kotlin/org/example/EtapaDesafio.kt`
- `app/src/main/kotlin/org/example/FilaDatosSimulacion.kt`
- `app/src/main/kotlin/org/example/ModeSelectionScreen.kt`
- `app/src/main/kotlin/org/example/MotorEstadoCaida.kt`
- `app/src/main/kotlin/org/example/MuestraDatosSimulacion.kt`
- `app/src/main/kotlin/org/example/ObjetivoEjercicio.kt`
- `app/src/main/kotlin/org/example/OpcionFormula.kt`
- `app/src/main/kotlin/org/example/OpcionRespuesta.kt`
- `app/src/main/kotlin/org/example/Planeta.kt`
- `app/src/main/kotlin/org/example/PuntoTrayectoria.kt`
- `app/src/main/kotlin/org/example/Retroalimentacion.kt`

**Campos públicos (encapsulación) (encapsulacion)** — 9 archivo(s):
- `app/src/main/kotlin/org/example/CalculoCaidaLibre.kt`
- `app/src/main/kotlin/org/example/EncabezadoSimulacion.kt`
- `app/src/main/kotlin/org/example/GeneradorEjercicios.kt`
- `app/src/main/kotlin/org/example/GeneradorOpcionesRespuesta.kt`
- `app/src/main/kotlin/org/example/ModeSelectionScreen.kt`
- `app/src/main/kotlin/org/example/MotorEstadoCaida.kt`
- `app/src/main/kotlin/org/example/PanelGraficosSimulacion.kt`
- `app/src/main/kotlin/org/example/PanelIndicadoresFisicos.kt`
- `app/src/main/kotlin/org/example/PanelValoresInstantaneos.kt`

## 5. Evolución por commit

Métricas calculadas sobre el estado de `main` en **cada commit** (9 commits con trabajo), del primero al último. La calidad usa el perfil `exigente`; el aporte de código lo mide RepoSense. El índice puede **bajar** al crecer el proyecto: se acumulan señales.

```mermaid
xychart-beta
    title "Índice POO/SOLID a lo largo de los commits"
    x-axis ["e4c7dc4", "f9cdb25", "21e6166", "6f7333f", "bdbdb04", "fbb8e8c", "6d00bd3", "54c463a", "ced6b32"]
    y-axis "Índice" 0 --> 100
    line [0, 0, 24, 28, 20, 10, 5, 4, 3]
```

| # | Fecha | Commit | Autor | LOC | Índice | %POO | Duplic. | Sueltas |
|---|---|---|---|---:|---:|---:|---:|---:|
| 1 | 2026-08-14 | `e4c7dc4` | francucchiari-code | 0 | 0 | 0% | 0.0% | 0 |
| 2 | 2026-08-31 | `f9cdb25` | dqmdz-etec | 0 | 0 | 0% | 0.0% | 0 |
| 3 | 2026-09-07 | `21e6166` | francucchiari-code | 172 | 24 | 48% | 0.0% | 1 |
| 4 | 2026-09-14 | `6f7333f` | francucchiari-code | 286 | 28 | 58% | 0.0% | 1 |
| 5 | 2026-09-25 | `bdbdb04` | francucchiari-code | 937 | 20 | 78% | 0.0% | 1 |
| 6 | 2026-10-02 | `fbb8e8c` | francucchiari-code | 1,647 | 10 | 81% | 0.0% | 1 |
| 7 | 2026-10-16 | `6d00bd3` | francucchiari-code | 3,453 | 5 | 78% | 1.7% | 3 |
| 8 | 2026-10-19 | `54c463a` | francucchiari-code | 5,932 | 4 | 79% | 3.5% | 0 |
| 9 | 2026-10-30 | `ced6b32` | francucchiari-code | 6,129 | 3 | 79% | 3.4% | 0 |

---
_Informe generado por la cátedra. Los criterios y umbrales de medición corresponden al perfil `exigente` de `calidad.py`; el semáforo de aprobación, a `CRITERIOS-CALIDAD.md` (ver `README.md`). Ante dudas, revisá el archivo señalado._
<!-- evaluacion:fin -->
