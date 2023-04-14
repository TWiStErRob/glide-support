plugins {
	alias(libs.plugins.agp)
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

@Suppress("UnstableApiUsage")
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
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}

	packaging {
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

dependencies {
	// Immediate SNAPSHOT resolution (in case the built version is too new), default is a day
	//configurations.glide4Implementation.resolutionStrategy.cacheChangingModulesFor 0, "seconds"

	// Lock in the version of Kotlin used so that the transitive dependencies are consistently upgraded.
	// https://kotlinlang.org/docs/whatsnew18.html#usage-of-the-latest-kotlin-stdlib-version-in-transitive-dependencies
	implementation(platform(libs.kotlin.bom))

	// Basic Android dependencies
	implementation(libs.androidx.annotation)
	implementation(libs.androidx.appcompat)
	implementation(libs.androidx.preference)

	// Extra dependencies for individual issues
	implementation(libs.androidx.cardview)
	implementation(libs.androidx.recyclerview)
	implementation(libs.androidx.palette)
	implementation(libs.androidx.percentlayout)
	implementation(libs.androidx.material)
	implementation(libs.androidx.lifecycleViewmodel)
}

dependencies { // Glide v4
	glide4Implementation(libs.glide4)
	glide4Implementation(libs.glide4.annotations)
	"glide4AnnotationProcessor"(libs.glide4.compiler)
	glide4Implementation(libs.glide4.recyclerview)

	// (DO NOT USE in production, not even for testing)
	"testGlide4CompileOnly"(libs.glide4.compiler)

	// OkHttp
	glide4Implementation(libs.glide4.okhttp3)
	glide4Implementation(libs.okhttp3)
	glide4Implementation(libs.okhttp3.logging)

	// Volley
	glide4Implementation(libs.glide4.volley)
	glide4Implementation(libs.volley2)

	glide4Implementation(libs.stetho)
	glide4Implementation(libs.stetho.okhttp3)
	glide4Implementation(libs.stetho.urlconnection)
}

dependencies { // Glide v3
	glide3Implementation(libs.glide3)

	// OkHttp
	glide3Implementation(libs.glide3.okhttp2)
	glide3Implementation(libs.okhttp2)

	// OkHttp3
	glide3Implementation(libs.glide3.okhttp3)
	glide3Implementation(libs.okhttp3)
	glide3Implementation(libs.okhttp3.logging)

	// Volley
	glide3Implementation(libs.glide3.volley)
	glide3Implementation(libs.volley)

	// Glide Transformations
	glide3Implementation(libs.glide3.wasabeef)
	glide3Implementation(libs.glide3.gpuimage)

	// Extra dependencies for individual issues
	glide3Implementation(libs.firebase)

	glide3Implementation(libs.stetho)
	glide3Implementation(libs.stetho.okhttp3)
	glide3Implementation(libs.stetho.urlconnection)

	glide3Implementation(libs.picasso)
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
