import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.purple.hello"
    compileSdk = AppConfig.compileSdk
    buildToolsVersion = AppConfig.buildToolsVersion

    defaultConfig {
        applicationId = "com.purple.hello"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["kakaoApiKey"] = getApiKey("KAKAO_API_KEY")
        buildConfigField("String", "KAKAO_NATIVE_KEY", getApiKey("KAKAO_NATIVE_KEY"))
        buildConfigField("String", "GOOGLE_KEY", getApiKey("GOOGLE_KEY"))
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
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
    implementation(project(":feature:rooms"))
    implementation(project(":feature:setting:app"))
    implementation(project(":feature:setting:room"))
    implementation(project(":feature:setting:profile"))
    implementation(project(":feature:notification"))
    implementation(project(":core:designsystem"))
    implementation(project(":domain:account"))
    implementation(project(":core:datastore"))
    implementation(project(":core:model"))
    implementation(project(":sync:work"))
    implementation("androidx.hilt:hilt-work:1.0.0")
    implementation(project(mapOf("path" to ":domain:user")))
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    implementation(composeDependencies)
    implementation(appDependencies)
    implementation(navDependencies)
    implementation(project(mapOf("path" to ":domain:user")))

    debugImplementation(composeDebug)
    testImplementation(defaultUnitTest)
    androidTestImplementation(defaultAndroidTest)

    implementation(Hilt.HILT_ANDROID)
    kapt(Hilt.HILT_ANDROID_COMPILER)

    implementation(Datastore.DATASTORE)

    implementation(socialLogin)

    implementation(firebaseDependencies)
    implementation(WorkmanagerKotlin.WORKMANAGER_KOTLIN)

    implementation(Hilt.HILT_WORK)
    kapt(Hilt.HILT_COMPILER)
}

fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}
