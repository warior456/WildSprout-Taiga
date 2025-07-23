package net.ugi.wildsprout;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import net.ugi.wildsprout.config.Config;
import net.ugi.wildsprout.config.ConfigHandler;
import net.ugi.wildsprout.world.gen.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WildSproutTaiga implements ModInitializer {
	public static final String MOD_ID = "wildsprout-taiga";
	public static ConfigHandler CONFIG;

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier identifier(String path) {
		return Identifier.of(WildSproutTaiga.MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		WildSproutTaiga.LOGGER.info("Loading Config for " + WildSproutTaiga.MOD_ID);
		Config.loadConfig();
		ModFeatures.init();
		ModStructureTypes.init();
		ModStructurePieceTypes.init();
		ModStructureKeys.init();
		ModWorldGeneration.generateModWorldGen();



		//Registry.register(Registries.STRUCTURE_TYPE, WildSproutTaiga.identifier("river"), ModStructureTypes.RIVER);
	}
}