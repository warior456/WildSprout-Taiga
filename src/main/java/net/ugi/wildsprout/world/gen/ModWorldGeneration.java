package net.ugi.wildsprout.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.MiscPlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;


import java.util.ArrayList;
import java.util.List;

public class ModWorldGeneration {
    public static void generateModWorldGen() {
        List<RegistryKey<Biome>> allEnabled = new ArrayList<>();
        List<RegistryKey<Biome>> snowyIfEnabled = new ArrayList<>();
        List<RegistryKey<Biome>> normalIfEnabled = new ArrayList<>();




        //RAW GENERATION
        //LAKES

        //LOCAL MODIFICATIONS

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA), GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.BOULDERS_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA), GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.ROCKS_PLACED_KEY);


        //UNDERGOUNDS STRUCTURES

        //SURFACE STRUCTURES

        //STRONGHOLDS

        //UNDERGOUND ORES

        //UNDERGROUND DECORATION

        //FLUID SPRINGS

        //VEGETAL DECORATION



        //TOP LAYER MODIFICATION
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SNOWY_TAIGA), GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.FLUFFY_SNOW_PLACED_KEY);
        //BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS), GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.SMALL_RIVER_PLACED_KEY);

        //MODIFY FEATURES



        // REMOVE FEATURES



    }
}