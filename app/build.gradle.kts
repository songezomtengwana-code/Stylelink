
import com.google.protobuf.gradle.id

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("com.google.protobuf")
    id("com.google.dagger.hilt.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.ekasi.studios.stylelink"
    compileSdk = 34

//    var supabase_url: String = gradleLocalProperties(rootDir).getProperty("SUPABASE_URL").toString();
//    val supabase_key: String = gradleLocalProperties(rootDir).getProperty("SUPABASE_ANON_KEY").toString();

    // stackoverflow protobuf for kotlin configuration

    defaultConfig {
        applicationId = "com.ekasi.studios.stylelink"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

//        BuildConfigField(String, "supabaseKey", "\"$supabase_key\"")
//        BuildConfigField(String, "supabaseUrl", "\"$supabase_url\"")
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
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("androidx.compose.material3:material3-android:1.2.1")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // google fonts
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.7")

    // glide_image_retriever
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")

    // navigation dependencies
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    // Feature module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.7.7")
    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.7")
    // Jetpack Compose Integration
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Coil
    implementation("io.coil-kt:coil-compose:2.6.0")

    // splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Retrofit for API requests
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // ViewModel and LiveData for MVVM architecture
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")


    // supabase
    implementation(platform("io.github.jan-tennert.supabase:bom:2.3.0-beta-2"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.ktor:ktor-client-android:2.3.9")

    // preference
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // optional - RxJava2 support
    implementation("androidx.datastore:datastore-preferences-rxjava2:1.1.1")

    // optional - RxJava3 support
    implementation("androidx.datastore:datastore-preferences-rxjava3:1.1.1")

    // proto datastore
    implementation("androidx.datastore:datastore:1.1.0")
    implementation("androidx.datastore:datastore:1.1.0")
    implementation("com.google.protobuf:protobuf-javalite:3.20.1")
    implementation("com.google.protobuf:protobuf-kotlin-lite:3.20.1")

    // optional - RxJava2 support
    implementation("androidx.datastore:datastore-rxjava2:1.1.0")

    // optional - RxJava3 support
    implementation("androidx.datastore:datastore-rxjava3:1.1.0")

    // Alternatively - use the following artifact without an Android dependency.
    implementation("androidx.datastore:datastore-core:1.1.0")
    implementation("androidx.datastore:datastore-preferences-core:1.1.0")

    // Room local storage
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
//    kapt "androidx.room:room-compiler:2.5.2"
    ksp("androidx.room:room-compiler:2.6.1")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
    testImplementation("com.google.truth:truth:1.1.3")
//    testImplementation("org.mockito:mockito-kotlin:4.0.0") // Mockito for mocking

    //Google Services & Maps
    implementation("com.google.android.gms:play-services-location:21.2.0")
    implementation("com.google.maps.android:maps-compose:2.9.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.libraries.places:places:3.4.0")

    //Accompanist (Permission)
    implementation("com.google.accompanist:accompanist-permissions:0.31.3-beta")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    ksp("com.google.dagger:hilt-compiler:2.44")


    // Android Keystore
    implementation("androidx.security:security-crypto-ktx:1.1.0-alpha06")

    // Material Icons
    implementation ("androidx.compose.material:material-icons-extended:1.7.0-beta02")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.24.1"
    }
    // Generates the java Protobuf-lite code for the Protobufs in this project. See
    // https://github.com/google/protobuf-gradle-plugin#customizing-protobuf-compilation
    // for more information.
    generateProtoTasks {
        // see https://github.com/google/protobuf-gradle-plugin/issues/518
        // see https://github.com/google/protobuf-gradle-plugin/issues/491
        // all() here because of android multi-variant
        all().configureEach {
            // this only works on version 3.8+ that has build-ins for javalite / kotlin lite
            // with previous version the java build in is to be removed and a new plugin
            // need to be declared
            builtins {
                id("java") { // id is imported above
                    option("lite")
                }
                id("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

secrets {
    // Optionally specify a different file name containing your secrets.
    // The plugin defaults to "local.properties"
    propertiesFileName = "secrets.properties"

    // A properties file containing default secret values. This file can be
    // checked in version control.
    defaultPropertiesFileName = "local.defaults.properties"

    // Configure which keys should be ignored by the plugin by providing regular expressions.
    // "sdk.dir" is ignored by default.
    ignoreList.add("keyToIgnore") // Ignore the key "keyToIgnore"
    ignoreList.add("sdk.*")       // Ignore all keys matching the regexp "sdk.*"
}
