object BaseDependencies {
    val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object Dependencies {
    val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    val jodaTime = "joda-time:joda-time:${Versions.jodaTime}"
    val koin = "io.insert-koin:koin-core:${Versions.koin}"
}

object AndroidDependencies {
    val core = "androidx.core:core-ktx:${Versions.androidCore}"
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val material = "com.google.android.material:material:${Versions.material}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    val koin = "io.insert-koin:koin-android:${Versions.koin}"
}

object UnitTestDependencies {
    val junit = "org.junit.jupiter:junit-jupiter-api:${Versions.junit}"
    val junitParam = "org.junit.jupiter:junit-jupiter-params:${Versions.junit}"
    val junitJupiterEngine = "org.junit.jupiter:junit-jupiter-engine"
    val kotestCore = "io.kotest:kotest-assertions-core:${Versions.kotest}"
    val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
    val mockk = "io.mockk:mockk:${Versions.mockk}"
    val mockkAgentJvm = "io.mockk:mockk-agent-jvm:${Versions.mockk}"
    val turbine = "app.cash.turbine:turbine:${Versions.turbine}"
    val koin = "io.insert-koin:koin-test-junit5:${Versions.koin}"
}

object AndroidInstrumentationTestDependencies {
    val espresso = "androidx.test.espresso:espresso-core:espresso"
}