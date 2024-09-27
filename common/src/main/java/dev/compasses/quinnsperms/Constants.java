package dev.compasses.quinnsperms;

import dev.ftb.mods.ftbteams.api.property.BooleanProperty;
import net.minecraft.resources.ResourceLocation;

public class Constants {
	public static final String MOD_ID = "quinnsperms";

	public static BooleanProperty ALLOW_SEASONS_SNOW_ICE = new BooleanProperty(ResourceLocation.fromNamespaceAndPath("quinnsperms", "allow_seasons_snow_ice"), true);
}