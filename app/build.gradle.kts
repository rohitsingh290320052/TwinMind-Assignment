plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.plugin)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"


    kotlin("kapt")
}

android {
    namespace = "com.example.twinmind_assignment"
    compileSdk = 35

    buildFeatures {
        compose = true
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.twinmind_assignment"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        val apiKey = project.findProperty("GEMINI_API_KEY") ?: ""
        buildConfigField("String", "GEMINI_API_KEY", "\"$apiKey\"")

    }


    composeOptions {
        kotlinCompilerExtensionVersion = "1.7.0"
    }

    // 🔥 FIX MATERIAL3 + JVM COMPATIBILITY
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    packaging {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.compose)

    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.09.01"))

    // Compose Material 3
    implementation("androidx.compose.material3:material3")

    // Coroutines
    implementation(libs.coroutines.android)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Permissions
    implementation(libs.accompanist.permissions)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // Hilt
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Material icons
    implementation("androidx.compose.material:material-icons-extended")

    implementation("com.google.ai.client.generativeai:generativeai:0.6.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")







}
