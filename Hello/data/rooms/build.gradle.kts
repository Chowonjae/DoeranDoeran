plugins {
    id("com.android.library")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.purple.data.rooms"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))

    implementation(Kotlin.KOTLIN_COROUTINES)
    implementation(KotlinxSerializationJson.KOTLIN_SELIAIZATION_JSON)
    implementation(retrofit2Dependencies)

    implementation(Hilt.HILT_ANDROID)
    implementation(project(mapOf("path" to ":core:datastore")))
    kapt(Hilt.HILT_ANDROID_COMPILER)
}
