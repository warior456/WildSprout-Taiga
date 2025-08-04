package net.ugi.wildsprout_taiga;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import net.ugi.wildsprout_taiga.config.Config;
import net.ugi.wildsprout_taiga.config.ConfigHandler;
import net.ugi.wildsprout_taiga.world.gen.ModFeatures;
import net.ugi.wildsprout_taiga.world.gen.ModWorldGeneration;
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
		return Identifier.of(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		WildSproutTaiga.LOGGER.info("Loading Config for " + MOD_ID);
		Config.loadConfig();
		ModFeatures.init();
		ModWorldGeneration.generateModWorldGen();
	}
}

// todo: rework random path to not go trough rock / trees ( and in plains too)
// todo: maybe 3 chunk river?
// todo: more types of tree
// todo: maybe reduce snow under trees
// todo: pine
// todo: other big trees