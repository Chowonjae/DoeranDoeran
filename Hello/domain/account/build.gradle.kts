plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.purple.hello.domain.account"
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
}

dependencies {
    implementation(project(":domain:user"))
    implementation(project(":data:account"))
    implementation(project(":data:user"))

    implementation(appDependencies)
    implementation(project(mapOf("path" to ":data:user")))
    testImplementation(defaultUnitTest)
    androidTestImplementation(defaultAndroidTest)
    implementation(Kotlin.KOTLIN_COROUTINES)
    implementation("javax.inject:javax.inject:1")
}
