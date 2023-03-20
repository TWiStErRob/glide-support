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
