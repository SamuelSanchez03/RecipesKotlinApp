plugins {
	id("com.android.application")
	kotlin("android")
	kotlin("kapt")
	id("com.google.dagger.hilt.android")
}

android {
	namespace = "com.example.recipesapp"
	compileSdk = 34
	
	defaultConfig {
		applicationId = "com.example.recipesapp"
		minSdk = 24
		targetSdk = 33
		versionCode = 1
		versionName = "1.0"
		
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
	
	buildFeatures {
		compose = true
	}
	
	composeOptions {
		kotlinCompilerExtensionVersion = "1.4.2"
	}
	
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_18
		targetCompatibility = JavaVersion.VERSION_18
	}
	kotlinOptions {
		jvmTarget = "18"
	}
}

dependencies {
	
	val composeBom = platform("androidx.compose:compose-bom:2023.01.00")
	implementation(composeBom)
	androidTestImplementation(composeBom)
	
	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.10.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
	
	// Material Design 3 for compose
	implementation("androidx.compose.material3:material3")
	
	// Optional - Integration with activities
	implementation("androidx.activity:activity-compose:1.8.0")
	// Optional - Integration with ViewModels
	implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
	
	// Android Studio Preview support
	implementation("androidx.compose.ui:ui-tooling-preview")
	debugImplementation("androidx.compose.ui:ui-tooling")
	
	//Compose Navigation
	val navVersion = "2.7.4"
	implementation("androidx.navigation:navigation-compose:$navVersion")
	
	//Retrofit implementations for API calls
	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	//Gson implementations for managing JSON responses
	implementation("com.squareup.retrofit2:converter-gson:2.9.0")
	//Dagger Hilt for Dependency Injection
	val daggerVersion = "2.44"
	implementation("com.google.dagger:hilt-android:$daggerVersion")
	kapt("com.google.dagger:hilt-android-compiler:$daggerVersion")
	
	//Hilt viewModel Navigation
	implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
	
	implementation("io.coil-kt:coil-compose:2.4.0")
}

kapt {
	correctErrorTypes = true
}