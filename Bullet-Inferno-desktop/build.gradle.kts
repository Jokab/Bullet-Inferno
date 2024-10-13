plugins {
    application
}

application {
    mainClass = "se.dat255.bulletinferno.Main"
}

dependencies {
    implementation(project(":Bullet-Inferno"))

    implementation(libs.gdx)
    implementation(libs.gdx.backend.lwjgl)
    implementation(libs.gdx.platform)
    implementation("${libs.gdx.platform.get()}:natives-desktop")
}
