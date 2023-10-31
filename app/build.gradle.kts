@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "by.bsuir.viktornikonenko.booksapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "by.bsuir.viktornikonenko.booksapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.animation)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.ui.util)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material)
    implementation(libs.accompanist.navigation.material)
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.drawablepainter)
    implementation(libs.accompanist.adaptive)
    implementation(libs.accompanist.testharness)
    implementation(libs.androidx.core)
    implementation(libs.androidx.collection)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.androidx.datastore)
    implementation(libs.koin.compose)
    implementation(libs.koin.android)
    implementation(libs.koin.core)
    implementation(libs.kotlin.serialization)
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.work.rxjava2)
    implementation(libs.androidx.work.gcm)
    implementation(libs.androidx.work.testing)
    implementation(libs.androidx.work.multiprocess)
    implementation(libs.kotlin.coroutines)
    implementation(libs.symbol.processing.api)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.service)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.common)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.dynamic.features.fragment)
    implementation(libs.androidx.navigation.testing)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.androidx.lifecycle.runtime.compose)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.paging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}