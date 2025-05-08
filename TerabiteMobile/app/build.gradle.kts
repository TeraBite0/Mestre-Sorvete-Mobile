plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.terabitemobile"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.terabitemobile"
        minSdk = 31
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.room.common)
    implementation(libs.places)
    implementation(libs.androidx.core.i18n)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation(libs.coil.compose)
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation(libs.coil.svg)

    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.4")

    val compose_version = "1.6.3"
    implementation ("androidx.compose.material:material-icons-extended:$compose_version")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.google.code.gson:gson:2.10")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")

    // OkHttp para logs
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")

    // Navegação

    val nav_version = "2.8.9"

    // Jetpack Compose integration
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Views/Fragments integration
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")

    // Feature module support for Fragments
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

    // JSON serialization library, works with the Kotlin serialization plugin
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")


    implementation("io.insert-koin:koin-androidx-compose:3.5.6")
    implementation("io.insert-koin:koin-android:3.5.6")

    implementation("io.insert-koin:koin-core:3.5.6")
    // Koin Android
    implementation("io.insert-koin:koin-android:3.5.6")
    // Koin for Compose
    implementation("io.insert-koin:koin-androidx-compose:3.5.6")
}