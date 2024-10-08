plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinParcelize)
    alias(libs.plugins.compose)
    alias(libs.plugins.ksp)
}

java.toolchain.languageVersion = JavaLanguageVersion.of(17)

android {
    namespace = "info.dvkr.screenstream.common"
    compileSdk = rootProject.extra["compileSdkVersion"] as Int
    buildToolsVersion = rootProject.extra["buildToolsVersion"] as String

    defaultConfig {
        minSdk = rootProject.extra["minSdkVersion"] as Int
    }

    kotlinOptions {
        freeCompilerArgs += "-Xexplicit-api=strict"
    }
}

dependencies {
    api(libs.kotlinx.coroutines.android)

    api(libs.androidx.core.ktx)
    api(libs.androidx.activity.compose)
    api(libs.androidx.appcompat)
    api(libs.androidx.lifecycle.runtime.compose)
    api(libs.androidx.window)
    api(libs.androidx.datastore.preferences)

    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.ui)
//    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material3.window)
    api("androidx.compose.foundation:foundation:1.7.0-rc01")
    api("androidx.compose.material3:material3:1.3.0-rc01")

    api(libs.koin.android.compose)
    api(libs.koin.annotations)
    ksp(libs.koin.ksp)

    api(libs.xlog)

//    api(libs.androidx.compose.ui.tooling.preview)
//    debugApi(libs.androidx.compose.ui.tooling)
}