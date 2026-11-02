plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.openjfx)
    application
}
repositories { mavenCentral() }
dependencies { implementation(libs.guava) }
javafx {
    version = "21.0.2"
    modules("javafx.controls", "javafx.fxml")
}
testing {
    suites { val test by getting(JvmTestSuite::class) { useKotlinTest("2.3.0") } }
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin { jvmToolchain(17) }
application { mainClass.set("org.example.PuntoEntrada") }
tasks.named<JavaExec>("run") {
    notCompatibleWithConfigurationCache("La tarea run de JavaFX no es compatible con la configuration cache")
}
