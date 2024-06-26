import net.twisterrob.gradle.doNotNagAbout
import org.gradle.kotlin.dsl.support.serviceOf

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
					.parse(file(shadowPath).toPath(), this) { settings.serviceOf() }
			}
		}
	}
}

plugins {
	id("net.twisterrob.gradle.plugin.nagging") version "0.17"
}

val isCI = System.getenv("GITHUB_ACTIONS") == "true"
val gradleVersion: String = GradleVersion.current().version

// TODEL Gradle 8.2 sync in AS FL 2022.2.1 / AS GI 2022.3.1 / IDEA 2023.1, fixed in 2023.2.
// https://youtrack.jetbrains.com/issue/IDEA-320266
@Suppress("MaxLineLength")
if ((System.getProperty("idea.version") ?: "") < "2023.2") {
	doNotNagAbout(
		"The org.gradle.api.plugins.JavaPluginConvention type has been deprecated. " +
			"This is scheduled to be removed in Gradle 9.0. " +
			"Consult the upgrading guide for further information: " +
			"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#java_convention_deprecation",
		"at org.jetbrains.kotlin.idea.gradleTooling.KotlinTasksPropertyUtilsKt.getPureKotlinSourceRoots(KotlinTasksPropertyUtils.kt:59)"
	)
	doNotNagAbout(
		"The Project.getConvention() method has been deprecated. " +
			"This is scheduled to be removed in Gradle 9.0. " +
			"Consult the upgrading guide for further information: " +
			"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#deprecated_access_to_conventions",
		"at org.jetbrains.kotlin.idea.gradleTooling.KotlinTasksPropertyUtilsKt.getPureKotlinSourceRoots(KotlinTasksPropertyUtils.kt:59)"
	)
	doNotNagAbout(
		"The org.gradle.api.plugins.Convention type has been deprecated. " +
			"This is scheduled to be removed in Gradle 9.0. " +
			"Consult the upgrading guide for further information: " +
			"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#deprecated_access_to_conventions",
		"at org.jetbrains.kotlin.idea.gradleTooling.KotlinTasksPropertyUtilsKt.getPureKotlinSourceRoots(KotlinTasksPropertyUtils.kt:59)"
	)

	doNotNagAbout(
		"The Project.getConvention() method has been deprecated. " +
			"This is scheduled to be removed in Gradle 9.0. " +
			"Consult the upgrading guide for further information: " +
			"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#deprecated_access_to_conventions",
		"at org.jetbrains.plugins.gradle.tooling.builder.ProjectExtensionsDataBuilderImpl.buildAll(ProjectExtensionsDataBuilderImpl.groovy:"
	)
	doNotNagAbout(
		"The org.gradle.api.plugins.Convention type has been deprecated. " +
			"This is scheduled to be removed in Gradle 9.0. " +
			"Consult the upgrading guide for further information: " +
			"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#deprecated_access_to_conventions",
		"at org.jetbrains.plugins.gradle.tooling.builder.ProjectExtensionsDataBuilderImpl.buildAll(ProjectExtensionsDataBuilderImpl.groovy:"
	)
	doNotNagAbout(
		"The org.gradle.api.plugins.JavaPluginConvention type has been deprecated. " +
			"This is scheduled to be removed in Gradle 9.0. " +
			"Consult the upgrading guide for further information: " +
			"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#java_convention_deprecation",
		// at org.jetbrains.plugins.gradle.tooling.builder.ExternalProjectBuilderImpl.doBuild(ExternalProjectBuilderImpl.groovy:108)
		// at org.jetbrains.plugins.gradle.tooling.builder.ExternalProjectBuilderImpl.doBuild(ExternalProjectBuilderImpl.groovy:117)
		// at org.jetbrains.plugins.gradle.tooling.builder.ExternalProjectBuilderImpl.doBuild(ExternalProjectBuilderImpl.groovy:118)
		"at org.jetbrains.plugins.gradle.tooling.builder.ExternalProjectBuilderImpl.doBuild(ExternalProjectBuilderImpl.groovy:1"
	)
	// No method and line number in stack to match all these:
	//  * getJavaPluginConvention(JavaPluginUtil.java:13)
	//  * getSourceSetContainer(JavaPluginUtil.java:18)
	//  * getSourceSetContainer(JavaPluginUtil.java:19)
	doNotNagAbout(
		"The org.gradle.api.plugins.JavaPluginConvention type has been deprecated. " +
			"This is scheduled to be removed in Gradle 9.0. " +
			"Consult the upgrading guide for further information: " +
			"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#java_convention_deprecation",
		"at org.jetbrains.plugins.gradle.tooling.util.JavaPluginUtil."
	)
	doNotNagAbout(
		"The Project.getConvention() method has been deprecated. " +
			"This is scheduled to be removed in Gradle 9.0. " +
			"Consult the upgrading guide for further information: " +
			"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#deprecated_access_to_conventions",
		"at org.jetbrains.plugins.gradle.tooling.util.JavaPluginUtil."
	)
	doNotNagAbout(
		"The org.gradle.api.plugins.Convention type has been deprecated. " +
			"This is scheduled to be removed in Gradle 9.0. " +
			"Consult the upgrading guide for further information: " +
			"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#deprecated_access_to_conventions",
		"at org.jetbrains.plugins.gradle.tooling.util.JavaPluginUtil."
	)
} else {
	val error: (String) -> Unit = (if (isCI) ::error else logger::warn)
	error("Android Studio version changed, please review hack.")
}
