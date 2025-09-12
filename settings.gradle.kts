import net.twisterrob.gradle.doNotNagAbout
import org.gradle.kotlin.dsl.support.serviceOf

pluginManagement {
	repositories {
		gradlePluginPortal()
		google()
	}
}

dependencyResolutionManagement {
	versionCatalogs {
		fun VersionCatalogBuilder.load(path: String) {
			// Overlay another toml file on top of the default one.
			org.gradle.api.internal.catalog.parser.TomlCatalogFileParser
				.parse(file(path).toPath(), this) { settings.serviceOf() }
		}
		create(defaultLibrariesExtensionName.get()) {
			// Implicit behavior: from(files("gradle/libs.versions.toml"))
			load("gradle/oldLibs.versions.toml")
			val shadowPath = settings.extra.get("com.bumptech.glide.build.shadow").toString()
			if (shadowPath.isNotBlank()) {
				load(shadowPath)
			}
		}
	}
}

plugins {
	id("net.twisterrob.gradle.plugin.nagging") version "0.18"
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

val gradleVersion: String = GradleVersion.current().version

// TODEL Gradle 9.1 vs AGP 8.13 https://issuetracker.google.com/issues/444260628
@Suppress("detekt.MaxLineLength")
doNotNagAbout(
	Regex(
		"Declaring dependencies using multi-string notation has been deprecated. ".escape() +
				"This will fail with an error in Gradle 10. ".escape() +
				"Please use single-string notation instead: ".escape() +
				"\"${"com.android.tools.build:aapt2:".escape()}\\d+\\.\\d+\\.\\d+-\\d+:(windows|linux|osx)${"\". ".escape()}" +
				"Consult the upgrading guide for further information: ".escape() +
				"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_9.html#dependency_multi_string_notation".escape() +
				".*",
	),
	//"at com.android.build.gradle.internal.res.Aapt2FromMaven\$Companion.create(Aapt2FromMaven.kt:139)",
)

// TODEL Gradle 9.1 vs AGP 8.13 https://issuetracker.google.com/issues/444260628
@Suppress("detekt.MaxLineLength")
doNotNagAbout(
	Regex(
		"Declaring dependencies using multi-string notation has been deprecated. ".escape() +
				"This will fail with an error in Gradle 10. ".escape() +
				"Please use single-string notation instead: ".escape() +
				"\"${"com.android.tools.lint:lint-gradle:".escape()}\\d+\\.\\d+\\.\\d+${"\". ".escape()}" +
				"Consult the upgrading guide for further information: ".escape() +
				"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_9.html#dependency_multi_string_notation".escape() +
				".*",
	),
	//"at com.android.build.gradle.internal.lint.LintFromMaven\$Companion.from(AndroidLintInputs.kt:2850)",
)

private fun String.escape(): String = Regex.escape(this)
