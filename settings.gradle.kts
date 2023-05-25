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

val isCI = System.getenv("GITHUB_ACTIONS") == "true"
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

// TODEL Gradle 8.2 vs AGP https://issuetracker.google.com/issues/279306626
@Suppress("MaxLineLength")
doNotNagAbout(
	"The BuildIdentifier.isCurrentBuild() method has been deprecated. " +
		"This is scheduled to be removed in Gradle 9.0. " +
		"Use getBuildPath() to get a unique identifier for the build. " +
		"Consult the upgrading guide for further information: " +
		"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#build_identifier_name_and_current_deprecation",
	// There are many instances coming from different code locations, all aggregated here:
	"at com.android.build.gradle.internal.ide.dependencies.BuildMappingUtils.getBuildId(BuildMapping.kt:40)"
)
// Android Studio Flamingo Sync https://issuetracker.google.com/issues/279306626#comment4
@Suppress("MaxLineLength")
doNotNagAbout(
	"The BuildIdentifier.getName() method has been deprecated. " +
		"This is scheduled to be removed in Gradle 9.0. " +
		"Use getBuildPath() to get a unique identifier for the build. " +
		"Consult the upgrading guide for further information: " +
		"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#build_identifier_name_and_current_deprecation",
	"at com.android.build.gradle.internal.ide.dependencies.LibraryServiceImpl\$getProjectInfo\$1.apply(LibraryService.kt:138)"
)

// TODEL Gradle 8.2 sync in AS FL 2022.2.1 / IDEA 2023.1 https://youtrack.jetbrains.com/issue/IDEA-320266.
// AGP fixed 7.4.0-beta02 and 8.0.0-alpha02 https://issuetracker.google.com/issues/241354494
@Suppress("MaxLineLength")
if ((System.getProperty("idea.version") ?: "") < "2023.1") {
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
		"at org.jetbrains.plugins.gradle.tooling.builder.ProjectExtensionsDataBuilderImpl.buildAll(ProjectExtensionsDataBuilderImpl.groovy:40)"
	)
	doNotNagAbout(
		"The org.gradle.api.plugins.Convention type has been deprecated. " +
			"This is scheduled to be removed in Gradle 9.0. " +
			"Consult the upgrading guide for further information: " +
			"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#deprecated_access_to_conventions",
		"at org.jetbrains.plugins.gradle.tooling.builder.ProjectExtensionsDataBuilderImpl.buildAll(ProjectExtensionsDataBuilderImpl.groovy:41)"
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
