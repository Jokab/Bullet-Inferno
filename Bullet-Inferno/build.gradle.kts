plugins {
    `java-library`
}

dependencies {
    implementation(libs.gdx)

    testImplementation(libs.junit)
    testImplementation(libs.gdx.platform)
    testImplementation("${libs.gdx.platform.get()}:natives-desktop")
}