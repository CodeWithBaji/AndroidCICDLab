plugins {
    `kotlin-dsl`
}

group = "com.androidcicdlab.buildlogic"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    compileOnly(libs.ktlint.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "androidcicdlab.android.application"
            implementationClass = "com.androidcicdlab.buildlogic.AndroidApplicationConventionPlugin"
        }
        register("quality") {
            id = "androidcicdlab.quality"
            implementationClass = "com.androidcicdlab.buildlogic.QualityConventionPlugin"
        }
        register("ci") {
            id = "androidcicdlab.ci"
            implementationClass = "com.androidcicdlab.buildlogic.CiConventionPlugin"
        }
    }
}
