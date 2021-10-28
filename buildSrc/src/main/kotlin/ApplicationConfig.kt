import org.gradle.api.JavaVersion

object ApplicationConfig {

    val compileSdkVersion = 30
    val minSdkVersion = 23
    val targetSdkVersion = 30
    val versionCode = 1
    val versionName = "0.0.1"
}

object Flavours {

    val productionApplicationId = "blog.mateuszgrabarski.todo"
    val stageApplicationId = "$productionApplicationId.stage"
}

object Java {

    val java_version = JavaVersion.VERSION_1_8
}