import net.twisterrob.gradle.doNotNagAbout

pluginManagement {
	repositories {
		mavenCentral()
		google()
	}
}

dependencyResolutionManagement {
	versionCatalogs {
		create(defaultLibrariesExtensionName.get()) {
			// Implicit behavior: from(files("gradle/libs.versions.toml"))
			val shadowPath = settings.extra.get("com.bumptech.glide.build.shadow").toString()
			if (shadowPath.isNotBlank()) {
				// Overlay another toml file on top of the default one.
				org.gradle.api.internal.catalog.parser.TomlCatalogFileParser
					.parse(file(shadowPath).toPath(), this)
			}
		}
	}
}

plugins {
	id("net.twisterrob.gradle.plugin.settings") version "0.15.1"
}

val gradleVersion: String = GradleVersion.current().version

// TODEL Gradle sync in IDEA 2023.1 (vs. Gradle 8.1): https://issuetracker.google.com/issues/274469173 
@Suppress("MaxLineLength")
doNotNagAbout(
	"The org.gradle.util.VersionNumber type has been deprecated. " +
		"This is scheduled to be removed in Gradle 9.0. " +
		"Consult the upgrading guide for further information: " +
		"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#org_gradle_util_reports_deprecations",
	// There are 3 instances on consecutive lines, so just ignore the whole file.
	"at com.android.ide.gradle.model.builder.AndroidStudioToolingPluginKt.isGradleAtLeast(AndroidStudioToolingPlugin.kt:"
)
