val composeDependencies = listOf(
    Compose.UI,
    Compose.PREVIEW,
    Compose.MATERIAL3,
    Compose.MATERIAL3_WINDOW_SIZE,
    Compose.ANIMATION,
    Compose.MATERIAL,
)

val composeDebug = listOf(
    Debug.COMPOSE_TOOLING,
    Debug.COMPOSE_TEST_MANIFEST,
)

val imageLoadDependencies = listOf(
    Coil.COMPOSE,
)

val appDependencies = listOf(
    KTX.CORE,
    KTX.LIFECYCLE_RUNTIME,
    Compose.ACTIVITY,
    Androidx.WINDOW
)

val navDependencies = listOf(
    Hilt.HILT_NAV_COMPOSE,
    Androidx.NAVIGATION_COMPOSE,
    Compose.NAVIGATION,
)

val androidLibraryDependencies = listOf(
    KTX.CORE,
)

val defaultUnitTest = listOf(
    UnitTest.JUNIT,
)

val defaultAndroidTest = listOf(
    AndroidTest.ESPRESSO_CORE,
    AndroidTest.ANDROID_JUNIT,
)

val calendarLibrary = listOf(
    CalendarLibrary.CALENDAR_CORE,
    CalendarLibrary.CALENDAR,
)

val socialLogin = listOf(
    SocialLogin.GOOGLE_LOGIN,
    SocialLogin.KAKAO_LOGIN,
)

val retrofit2Dependencies = listOf(
    Retrofit2.RETROFIT2,
    Retrofit2.KOTLIN_CONVERTER,
)

val protobufDependencies = listOf(
    Protobuf.PROTOBUF_JAVALITE,
    Protobuf.PROTOBUF_KOTLINLITE,
)

val cameraDependencies = listOf(
    Camera.CAMERA2,
    Camera.CAMERA_CORE,
    Camera.CAMERA_LIFECYCLE
)

val firebaseDependencies = listOf(
    Firebase.FIREBASE_BOM,
    Firebase.FIREBASE_ANAYTICS,
    Firebase.FIREBASE_MESSAGING,
    Firebase.FIREBASE_MESSAGING_DIRECTBOOT,
)
