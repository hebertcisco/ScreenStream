import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinParcelize)
    alias(libs.plugins.compose)
    alias(libs.plugins.ksp)
}

java.toolchain.languageVersion = JavaLanguageVersion.of(17)

android {
    namespace = "info.dvkr.screenstream.webrtc"
    compileSdk = rootProject.extra["compileSdkVersion"] as Int
    buildToolsVersion = rootProject.extra["buildToolsVersion"] as String
    ndkVersion = "27.0.12077973"

    defaultConfig {
        minSdk = rootProject.extra["minSdkVersion"] as Int
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }

    val localProps = Properties()
    File(rootProject.rootDir, "local.properties").apply { if (exists() && isFile) inputStream().use { localProps.load(it) } }

    buildTypes {
        debug {
            buildConfigField("String", "SIGNALING_SERVER", localProps.getProperty("SIGNALING_SERVER_DEV", "\"\""))
            buildConfigField("String", "CLOUD_PROJECT_NUMBER", localProps.getProperty("CLOUD_PROJECT_NUMBER_DEV", "\"\""))
        }

        release {
            buildConfigField("String", "SIGNALING_SERVER", localProps.getProperty("SIGNALING_SERVER_RELEASE", "\"\""))
            buildConfigField("String", "CLOUD_PROJECT_NUMBER", localProps.getProperty("CLOUD_PROJECT_NUMBER_RELEASE", "\"\""))
        }
    }

    kotlinOptions {
        freeCompilerArgs += "-Xexplicit-api=strict"
    }

    composeCompiler {
        enableStrongSkippingMode = true
    }
}

dependencies {
    implementation(project(":common"))

    ksp(libs.koin.ksp)

    implementation(libs.play.services.tasks)
    implementation(libs.play.integrity)

    // Local Webrtc m126.0.6478.122
    implementation(libs.socket)
    implementation(libs.okio)
    implementation(libs.okhttp)
}

configurations.all {
    exclude("androidx.fragment", "fragment")
    exclude("org.json", "json")
}