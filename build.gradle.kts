import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.30"
    id("org.jetbrains.compose") version "1.0.0-alpha4-build328"
}

group = "me.foolishchow"
version = "0.0.1"

repositories {
    jcenter()
    mavenCentral()
    google()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

dependencies {
    implementation("androidx.annotation:annotation:1.2.0")
    implementation(compose.desktop.currentOs)
    implementation("org.koin:koin-core:2.0.1")
    implementation("org.koin:koin-core-ext:2.0.1")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.3.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.3.0")
    implementation("org.jsoup:jsoup:1.9.2")
    implementation("org.luaj:luaj-jse:3.0.1")
//    implementation("io.coil-kt:coil-compose:1.3.2")
    //implementation("com.github.skydoves:landscapist-glide:1.3.5")


//    implementation("com.google.accompanist:accompanist-flowlayout:0.17.0")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "me.foolishchow.bigfoot.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "big-foot"
            packageVersion = "1.0.1"
        }
    }
}