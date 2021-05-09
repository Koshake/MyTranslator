import org.gradle.api.JavaVersion

object Config {
    const val applicationId = "com.koshake1.mytranslator"
    const val minSdkVersion = 26
    const val targetSdkVersion =  30
    const val versionCode =  1
    const val versionName = "1.0"
    const val compileSdkVersion = 30
    const val buildToolsVersion = "30.0.3"

    val java_version = JavaVersion.VERSION_1_8
}

object Modules {
    const val app = ":app"
    const val core = ":core"
    const val utils = ":utils"
    const val model = ":model"

    //Features
    const val historyScreen = ":historyscreen"
}

object Versions {

    //AndroidX
    const val appcompat = "1.2.0"
    const val multidex = "1.0.3"
    const val swiperefreshlayout = "1.0.0"

    //Design
    const val material = "1.3.0"

    //Kotlin
    const val core = "1.3.2"
    const val stdlib = "1.4.10"

    //Retrofit 2
    const val retrofit = "2.9.0"
    const val converterGson = "2.7.1"
    const val interceptor = "3.12.1"
    const val coroutinesAdapter = "0.9.2"

    //Test
    const val junit = "4.13.2"
    const val runner = "1.3.0"
    const val espressoCore = "3.3.0"

    //Koin
    const val koin = "2.0.1"

    // Coroutines
    const val coroutinesCore = "1.3.8"
    const val coroutinesAndroid = "1.4.1"

    // Room
    const val room = "2.2.0-alpha01"

    //Picasso
    const val picasso = "2.5.2"
    //Glide
    const val glide = "4.9.0"

    //Google Play
    const val googlePlayCore = "1.6.3"
}

object AndroidX {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val multidex = "com.android.support:multidex:${Versions.multidex}"
    const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}"
}

object Design {
    const val material = "com.google.android.material:material:${Versions.material}"
}

object Kotlin {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.stdlib}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.interceptor}"
    const val coroutinesAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.coroutinesAdapter}"
}

object TestImpl {
    const val junit = "junit:junit:${Versions.junit}"
    const val runner = "androidx.test:runner:${Versions.runner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}

object Koin {
    const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"
}

object Coroutines {
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
}

object Picasso {
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.room}"
    const val compiler = "androidx.room:room-compiler:${Versions.room}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"
}

object GooglePlay {
    const val googlePlayCore = "com.google.android.play:core:${Versions.googlePlayCore}"
}
