plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.library")
    id("com.google.dagger.hilt.android")
}
android {
    namespace = "com.purple.hello.feature.groups"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = KotlinOptions.jvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = ComposeOptions.kotlinCompilerExtensionVersion
    }
}

dependencies {

    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
    implementation(project(":domain:rooms"))
    implementation(project(":domain:account"))

    implementation(composeDependencies)
    implementation(imageLoadDependencies)
    implementation(appDependencies)
    implementation(navDependencies)
    implementation(cameraDependencies)
    implementation(calendarLibrary)
    implementation(Compose.PERMISSION)

    implementation("com.airbnb.android:lottie-compose:6.0.0")
    implementation(Hilt.HILT_ANDROID)
    implementation("androidx.camera:camera-view:1.1.0-beta02")
    kapt(Hilt.HILT_ANDROID_COMPILER)

    debugImplementation(composeDebug)
    testImplementation(defaultUnitTest)
    androidTestImplementation(defaultAndroidTest)
}
