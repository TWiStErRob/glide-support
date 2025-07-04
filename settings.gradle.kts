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

// TODEL Gradle 8.14 vs AGP 8.9 https://issuetracker.google.com/issues/408334529
@Suppress("detekt.MaxLineLength")
doNotNagAbout(
	"Retrieving attribute with a null key. " +
			"This behavior has been deprecated. " +
			"This will fail with an error in Gradle 10. " +
			"Don't request attributes from attribute containers using null keys. " +
			"Consult the upgrading guide for further information: " +
			"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#null-attribute-lookup",
	"at com.android.build.gradle.internal.ide.dependencies.ArtifactUtils.isAndroidProjectDependency(ArtifactUtils.kt:539)",
)
