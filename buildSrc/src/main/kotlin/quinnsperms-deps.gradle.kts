import dev.compasses.multiloader.extension.MultiLoaderExtension
import gradle.kotlin.dsl.accessors._523dc74e2e9552463686721a7434f18b.compileOnly
import gradle.kotlin.dsl.accessors._523dc74e2e9552463686721a7434f18b.implementation
import gradle.kotlin.dsl.accessors._523dc74e2e9552463686721a7434f18b.modImplementation

extensions.configure(MultiLoaderExtension::class) {
    mods {
        create("ftbteams") {
            required()

            requiresRepo("FTB Maven", "https://maven.saps.dev/releases", setOf("dev.ftb.mods"))
            requiresRepo("Architectury Maven", "https://maven.architectury.dev", setOf("dev.architectury"))

            artifacts {
                when (project.name) {
                    "common" -> {
                        compileOnly("dev.ftb.mods:ftb-library-neoforge:2100.1.4")
                        compileOnly("dev.ftb.mods:ftb-teams-neoforge:2100.1.0")
                    }
                    "neoforge" -> {
                        implementation("dev.ftb.mods:ftb-library-neoforge:2100.1.4")
                        implementation("dev.ftb.mods:ftb-teams-neoforge:2100.1.0")
                    }
                    else -> {
                        modImplementation("dev.ftb.mods:ftb-library-fabric:2100.1.4") {
                            exclude(group = "mezz.jei")
                        }

                        modImplementation("dev.ftb.mods:ftb-teams-fabric:2100.1.0") {
                            exclude(group = "mezz.jei")
                        }
                    }
                }
            }
        }

        create("ftbchunks") {
            required()

            requiresRepo("FTB Maven", "https://maven.saps.dev/releases", setOf("dev.ftb.mods"))
            requiresRepo("Architectury Maven", "https://maven.architectury.dev", setOf("dev.architectury"))

            artifacts {
                when (project.name) {
                    "common" -> compileOnly("dev.ftb.mods:ftb-chunks-neoforge:2100.1.1")
                    "neoforge" -> implementation("dev.ftb.mods:ftb-chunks-neoforge:2100.1.1")
                    else -> modImplementation("dev.ftb.mods:ftb-chunks-fabric:2100.1.1") {
                        exclude(group = "mezz.jei")
                    }
                }
            }
        }

        create("sereneseasons") {
            required()

            requiresRepo("Sponge Maven", "https://repo.spongepowered.org/repository/maven-public/", setOf("com.github.glitchfiend"))

            artifacts {
                when (project.name) {
                    "common" -> {
                        compileOnly("com.github.glitchfiend:GlitchCore-neoforge:1.21-2.0.0.0")
                        compileOnly("com.github.glitchfiend:SereneSeasons-neoforge:1.21-10.0.0.0")
                    }
                    "neoforge" -> {
                        implementation("com.github.glitchfiend:GlitchCore-neoforge:1.21-2.0.0.0")
                        implementation("com.github.glitchfiend:SereneSeasons-neoforge:1.21-10.0.0.0")
                    }
                    else -> {
                        modImplementation("com.github.glitchfiend:GlitchCore-fabric:1.21-2.0.0.0")
                        modImplementation("com.github.glitchfiend:SereneSeasons-fabric:1.21-10.0.0.0")
                    }
                }

                compileOnly("com.electronwill.night-config:toml:3.6.7")
                compileOnly("com.electronwill.night-config:core:3.6.7")
            }
        }
    }
}