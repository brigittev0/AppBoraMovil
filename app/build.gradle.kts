plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "pe.edu.idat.appborabora"
    compileSdk = 34

    defaultConfig {
        applicationId = "pe.edu.idat.appborabora"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Proporciona LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

    // Proporciona ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    // Convertidor que utiliza Gson para la serialización de JSON.
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Una biblioteca de serialización/deserialización de Java para convertir objetos Java en JSON y viceversa.
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("com.google.code.gson:gson:2.8.8")

    // Una biblioteca de carga de imágenes para Android.
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Autenticación de Firebase.
    implementation ("com.google.firebase:firebase-auth:21.0.1")

    // Servicios de autenticación de Google Play.
    implementation ("com.google.android.gms:play-services-auth:19.2.0")

    // Room es una biblioteca de persistencia de datos que proporciona una capa de abstracción sobre SQLite.
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    testImplementation("androidx.room:room-testing:2.6.1")

    // Biblioteca para mostrar diálogos de alerta de estilo dulce.
    implementation ("com.github.f0ris.sweetalert:library:1.6.2")

    // Biblioteca para crear un deslizador de imágenes automático.
    implementation ("com.github.smarteist:autoimageslider:1.3.9")

    //--------Dependencias para integrar Niubiz
    implementation("com.android.support:multidex:1.0.3")
    implementation("com.squareup.picasso:picasso:2.5.2")
    implementation("com.nimbusds:nimbus-jose-jwt:7.0.1")
    implementation("org.bouncycastle:bcprov-jdk15on:1.61")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.6")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.6")

    // Agregar archivos de Niubiz .aar del directorio 'libs'
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))

}