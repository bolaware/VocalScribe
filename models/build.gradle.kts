import java.util.UUID

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.bolaware.model"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    sourceSets.getByName("main") {
        assets.srcDirs(file("$buildDir/generated/assets")) // ✅ Correct function call
    }
}

tasks.register("genUUID") {
    val uuidEn = UUID.randomUUID().toString()
    val uuidDe = UUID.randomUUID().toString()

    val buildDir = layout.buildDirectory.dir("generated/assets")

    val enDir = buildDir.map { it.dir("model-en-us") }
    val deDir = buildDir.map { it.dir("model-small-de") }

    val enFile = enDir.map { it.file("uuid") }
    val deFile = deDir.map { it.file("uuid") }

    doLast {
        enDir.get().asFile.mkdirs()
        deDir.get().asFile.mkdirs()

        enFile.get().asFile.writeText(uuidEn)
        deFile.get().asFile.writeText(uuidDe)

        println("✅ UUIDs generated:")
        println(" - English: ${enFile.get().asFile.absolutePath} -> $uuidEn")
        println(" - German: ${deFile.get().asFile.absolutePath} -> $uuidDe")
    }
}

tasks.named("preBuild").configure {
    dependsOn("genUUID")
}


