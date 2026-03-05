plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.attendanceapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.attendanceapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Add ALL of these libraries
    implementation("androidx.recyclerview:recyclerview:1.3.2")         // For the Teacher's list
    implementation("com.github.yuriy-budiyev:code-scanner:2.3.2")   // For the Student's scanner
    implementation("com.google.zxing:core:3.5.1")
    implementation(libs.activity)                   // For the Teacher's QR generation

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
