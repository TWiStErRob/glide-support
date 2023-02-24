@file:Suppress("UnstableApiUsage")

plugins {
	id("com.android.application") version "7.4.1"
}

repositories {
	mavenCentral()
	google()
	// https://github.com/bumptech/glide/wiki/Snapshots#building-snapshots-locally
	if (project.hasProperty("local.repo")) {
		maven("glide-local") { setUrl(project.property("local.repo")!!) }
	}
	// https://github.com/bumptech/glide/wiki/Snapshots#obtaining-snapshots
	maven("glide-snapshot") { setUrl("https://oss.sonatype.org/content/repositories/snapshots") }
}

android {
	compileSdk = 33
	namespace = "com.bumptech.glide.supportapp"
	defaultConfig {
		@Suppress("MinSdkTooLow") // holding back for when AndroidX is migrated.
		minSdk = 14
		@Suppress("ExpiredTargetSdkVersion") // holding back for when AndroidX is migrated.
		targetSdk = 28
		versionCode = 1
		versionName = "0.1"
		multiDexEnabled = true
		vectorDrawables.useSupportLibrary = true
		proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
	}
	sourceSets["main"].java.srcDir("src/main/thirdparty")
	flavorDimensions += "version"
	productFlavors {
		create("glide3") {
			dimension = "version"
			applicationId = "com.bumptech.glide.supportapp.v3"
			sourceSets["glide3"].java.srcDir("src/glide3/thirdparty")
			sourceSets["glide3"].java.srcDir("src/glide3/hacks")
		}
		create("glide4") {
			dimension = "version"
			applicationId = "com.bumptech.glide.supportapp.v4"
			sourceSets["glide4"].java.srcDir("src/glide4/thirdparty")
			sourceSets["glide4"].java.srcDir("src/glide4/hacks")
		}
	}

	androidComponents.beforeVariants { variant ->
		if (variant.buildType == "release") {
			variant.enable = false
		}
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}

	packagingOptions {
		jniLibs.useLegacyPackaging = false
		resources {
			excludes += listOf(
				"META-INF/DEPENDENCIES.txt",
				"META-INF/NOTICE",
				"META-INF/NOTICE.txt",
				"META-INF/LICENSE",
				"META-INF/LICENSE.txt",
			)
		}
	}

	lint {
		checkAllWarnings = true
		abortOnError = false
	}
}

val stethoVersion = "1.6.0"

dependencies {
	// Immediate SNAPSHOT resolution (in case the built version is too new), default is a day
	//configurations.glide4Implementation.resolutionStrategy.cacheChangingModulesFor 0, "seconds"

	// Lock in the version of Kotlin used so that the transitive dependencies are consistently upgraded.
	// https://kotlinlang.org/docs/whatsnew18.html#usage-of-the-latest-kotlin-stdlib-version-in-transitive-dependencies
	implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.10"))

	// Basic Android dependencies
	implementation("androidx.annotation:annotation:1.6.0")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("androidx.preference:preference:1.2.0")

	// Extra dependencies for individual issues
	implementation("androidx.cardview:cardview:1.0.0")
	implementation("androidx.recyclerview:recyclerview:1.2.1")
	implementation("androidx.palette:palette:1.0.0")
	implementation("androidx.percentlayout:percentlayout:1.0.0")
	implementation("com.google.android.material:material:1.8.0")
	implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
}

dependencies { // Glide v4
	val glideVersion = "4.15.0"
	glide4Implementation("com.github.bumptech.glide:glide:${glideVersion}")
	glide4Implementation("com.github.bumptech.glide:annotations:${glideVersion}")
	"glide4AnnotationProcessor"("com.github.bumptech.glide:compiler:${glideVersion}")
	@Suppress("GradleDependency", "GradleDynamicVersion")
	glide4Implementation("com.github.bumptech.glide:recyclerview-integration:${glideVersion}@aar")

	// (DO NOT USE in production, not even for testing)
	"testGlide4CompileOnly"("com.github.bumptech.glide:compiler:${glideVersion}")

	// OkHttp
	glide4Implementation("com.github.bumptech.glide:okhttp3-integration:${glideVersion}")
	glide4Implementation("com.squareup.okhttp3:okhttp:4.10.0")
	glide4Implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

	// Volley
	glide4Implementation("com.github.bumptech.glide:volley-integration:${glideVersion}")
	glide4Implementation("com.android.volley:volley:1.2.1")

	glide4Implementation("com.facebook.stetho:stetho:${stethoVersion}")
	glide4Implementation("com.facebook.stetho:stetho-okhttp3:${stethoVersion}")
	glide4Implementation("com.facebook.stetho:stetho-urlconnection:${stethoVersion}")
}

dependencies { // Glide v3
	val glideVersion = "3.9.0-SNAPSHOT"
	val glideIntegrationVersion = "1.6.0-SNAPSHOT"
	glide3Implementation("com.github.bumptech.glide:glide:${glideVersion}")

	// OkHttp
	glide3Implementation("com.github.bumptech.glide:okhttp-integration:${glideIntegrationVersion}")
	glide3Implementation("com.squareup.okhttp:okhttp:2.7.5")

	// OkHttp3
	glide3Implementation("com.github.bumptech.glide:okhttp3-integration:${glideIntegrationVersion}")
	glide3Implementation("com.squareup.okhttp3:okhttp:4.10.0")
	glide3Implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

	// Volley
	glide3Implementation("com.github.bumptech.glide:volley-integration:${glideIntegrationVersion}")
	glide3Implementation("com.mcxiaoke.volley:library:1.0.19")

	// Glide Transformations
	glide3Implementation("jp.wasabeef:glide-transformations:1.0.6")
	glide3Implementation("jp.co.cyberagent.android.gpuimage:gpuimage-library:1.4.0")

	// Extra dependencies for individual issues
	glide3Implementation("com.firebase:firebase-client-android:2.5.2")

	glide3Implementation("com.facebook.stetho:stetho:${stethoVersion}")
	glide3Implementation("com.facebook.stetho:stetho-okhttp3:${stethoVersion}")
	glide3Implementation("com.facebook.stetho:stetho-urlconnection:${stethoVersion}")

	glide3Implementation("com.squareup.picasso:picasso:2.71828")
}

dependencies { // Competition
	//implementation("com.nostra13.universalimageloader:universal-image-loader:1.9.5") // not used yet
	//implementation("com.facebook.fresco:fresco:0.9.0") // not used yet, it has too many dependencies
}

tasks.withType<JavaCompile>().configureEach {
	options.compilerArgs = options.compilerArgs + listOf(
		"-Werror",
		"-Xlint:all",
		"-Xlint:-auxiliaryclass",
		"-Xlint:-processing",
	)
	if (name.contains("Glide4")) {
		options.compilerArgs = options.compilerArgs + listOf(
			// The framework in IssueInfoAdapter and GlideReset needs GlideModules at runtime.
			"-Xlint:-deprecation",
		)
		options.isFork = true
		options.forkOptions.jvmArgs = options.forkOptions.jvmArgs.orEmpty() + listOf(
			// TODEL https://github.com/bumptech/glide/issues/5003 along with fork=true.
			"--add-opens=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED",
		)
	}
}

fun DependencyHandler.glide3Implementation(dependencyNotation: Any): Dependency? =
	add("glide3Implementation", dependencyNotation)

fun DependencyHandler.glide4Implementation(dependencyNotation: Any): Dependency? =
	add("glide4Implementation", dependencyNotation)
