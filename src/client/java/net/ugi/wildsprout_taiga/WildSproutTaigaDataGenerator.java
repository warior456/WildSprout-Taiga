package net.ugi.wildsprout_taiga;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.ugi.wildsprout_taiga.datagen.ModBlockTagProvider;
import net.ugi.wildsprout_taiga.datagen.ModRegistryDataGenerator;
import net.ugi.wildsprout_taiga.world.gen.ModConfiguredFeatures;
import net.ugi.wildsprout_taiga.world.gen.ModPlacedFeatures;

public class WildSproutTaigaDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModRegistryDataGenerator::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
	}
}
