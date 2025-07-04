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

// TODEL https://issuetracker.google.com/issues/370546370
// See also https://github.com/gradle/gradle/issues/32422
@Suppress("MaxLineLength")
doNotNagAbout(
	"Declaring 'crunchPngs' as a property using an 'is-' method with a Boolean type on com.android.build.gradle.internal.dsl.BuildType\$AgpDecorated has been deprecated. " +
			"Starting with Gradle 10.0, this property will no longer be treated like a property. " +
			"The combination of method name and return type is not consistent with Java Bean property rules. " +
			"Add a method named 'getCrunchPngs' with the same behavior and mark the old one with @Deprecated, " +
			"or change the type of 'com.android.build.gradle.internal.dsl.BuildType\$AgpDecorated.isCrunchPngs' (and the setter) to 'boolean'. " +
			"Consult the upgrading guide for further information: " +
			"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#groovy_boolean_properties",
)
@Suppress("MaxLineLength")
doNotNagAbout(
	"Declaring 'useProguard' as a property using an 'is-' method with a Boolean type on com.android.build.gradle.internal.dsl.BuildType has been deprecated. " +
			"Starting with Gradle 10.0, this property will no longer be treated like a property. " +
			"The combination of method name and return type is not consistent with Java Bean property rules. " +
			"Add a method named 'getUseProguard' with the same behavior and mark the old one with @Deprecated, " +
			"or change the type of 'com.android.build.gradle.internal.dsl.BuildType.isUseProguard' (and the setter) to 'boolean'. " +
			"Consult the upgrading guide for further information: " +
			"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#groovy_boolean_properties",
)
@Suppress("MaxLineLength")
doNotNagAbout(
	"Declaring 'wearAppUnbundled' as a property using an 'is-' method with a Boolean type on com.android.build.api.variant.impl.ApplicationVariantImpl has been deprecated. " +
			"Starting with Gradle 10.0, this property will no longer be treated like a property. " +
			"The combination of method name and return type is not consistent with Java Bean property rules. " +
			"Add a method named 'getWearAppUnbundled' with the same behavior and mark the old one with @Deprecated, " +
			"or change the type of 'com.android.build.api.variant.impl.ApplicationVariantImpl.isWearAppUnbundled' (and the setter) to 'boolean'. " +
			"Consult the upgrading guide for further information: " +
			"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#groovy_boolean_properties",
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
