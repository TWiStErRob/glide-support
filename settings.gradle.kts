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
	id("net.twisterrob.gradle.plugin.nagging") version "0.17"
}

val isCI = System.getenv("GITHUB_ACTIONS") == "true"
val gradleVersion: String = GradleVersion.current().version

// TODEL Gradle 8.13 vs AGP 8.0-8.9 https://issuetracker.google.com/issues/370546370
@Suppress("detekt.MaxLineLength")
doNotNagAbout(
	"Declaring an 'is-' property with a Boolean type has been deprecated. " +
			"Starting with Gradle 9.0, this property will be ignored by Gradle. " +
			"The combination of method name and return type is not consistent with Java Bean property rules and will become unsupported in future versions of Groovy. " +
			"Add a method named 'getCrunchPngs' with the same behavior and mark the old one with @Deprecated, " +
			"or change the type of 'com.android.build.gradle.internal.dsl.BuildType\$AgpDecorated.isCrunchPngs' (and the setter) to 'boolean'. " +
			"Consult the upgrading guide for further information: " +
			"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#groovy_boolean_properties"
)
@Suppress("detekt.MaxLineLength")
doNotNagAbout(
	"Declaring an 'is-' property with a Boolean type has been deprecated. " +
			"Starting with Gradle 9.0, this property will be ignored by Gradle. " +
			"The combination of method name and return type is not consistent with Java Bean property rules and will become unsupported in future versions of Groovy. " +
			"Add a method named 'getUseProguard' with the same behavior and mark the old one with @Deprecated, " +
			"or change the type of 'com.android.build.gradle.internal.dsl.BuildType.isUseProguard' (and the setter) to 'boolean'. " +
			"Consult the upgrading guide for further information: " +
			"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#groovy_boolean_properties"
)
@Suppress("detekt.MaxLineLength")
doNotNagAbout(
	"Declaring an 'is-' property with a Boolean type has been deprecated. " +
			"Starting with Gradle 9.0, this property will be ignored by Gradle. " +
			"The combination of method name and return type is not consistent with Java Bean property rules and will become unsupported in future versions of Groovy. " +
			"Add a method named 'getWearAppUnbundled' with the same behavior and mark the old one with @Deprecated, " +
			"or change the type of 'com.android.build.api.variant.impl.ApplicationVariantImpl.isWearAppUnbundled' (and the setter) to 'boolean'. " +
			"Consult the upgrading guide for further information: " +
			"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#groovy_boolean_properties"
)

// TODEL Gradle 8.14 vs AGP 8.9 https://issuetracker.google.com/issues/408334529
@Suppress("detekt.MaxLineLength")
doNotNagAbout(
	"Retrieving attribute with a null key. " +
			"This behavior has been deprecated. " +
			"This will fail with an error in Gradle 10.0. " +
			"Don't request attributes from attribute containers using null keys. " +
			"Consult the upgrading guide for further information: " +
			"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#null-attribute-lookup",
	"at com.android.build.gradle.internal.ide.dependencies.ArtifactUtils.isAndroidProjectDependency(ArtifactUtils.kt:539)",
)
