
buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.9.4")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    id("java")
    id("com.google.protobuf") version "0.9.4"
}
