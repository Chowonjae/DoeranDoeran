pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://devrepo.kakao.com/nexus/content/groups/public/")
    }
}

rootProject.name = "Hello"
include(":app")
// core
include(":core:designsystem")
include(":core:ui")
include(":core:model")
include(":core:database")
include(":core:network")
include(":core:datastore")
// feature
include(":feature:rooms")
include(":feature:setting:room")
// domain
include(":domain:rooms")
include(":domain:account")
// data
include(":data:rooms")
include(":data:user")
include(":data:account")
include(":feature:setting:app")
include(":feature:setting:profile")
include(":domain:user")
include(":sync:work")
include(":feature:notification")
include(":domain:notification")
include(":data:notification")
