package net.ugi.wildsprout.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.ugi.wildsprout.WildSproutTaiga;

import java.util.ArrayList;
import java.util.List;

public class ModWorldGeneration {
    public static void generateModWorldGen() {
        List<RegistryKey<Biome>> allEnabled = new ArrayList<>();
        List<RegistryKey<Biome>> snowyIfEnabled = new ArrayList<>();
        List<RegistryKey<Biome>> normalIfEnabled = new ArrayList<>();

        if(WildSproutTaiga.CONFIG.PlainsEnabled) {
            allEnabled.add(BiomeKeys.PLAINS);
            normalIfEnabled.add(BiomeKeys.PLAINS);
        }
        if(WildSproutTaiga.CONFIG.SnowyPlainsEnabled) {
            allEnabled.add(BiomeKeys.SNOWY_PLAINS);
            snowyIfEnabled.add(BiomeKeys.SNOWY_PLAINS);
        }
        if(WildSproutTaiga.CONFIG.SunFlowerPlainsEnabled) {
            allEnabled.add(BiomeKeys.SUNFLOWER_PLAINS);
            normalIfEnabled.add(BiomeKeys.SUNFLOWER_PLAINS);
        }


        //RAW GENERATION
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(normalIfEnabled), GenerationStep.Feature.RAW_GENERATION, ModPlacedFeatures.LAKE_PLACED_KEY);

        //LAKES

        //LOCAL MODIFICATIONS
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(allEnabled), GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.BOULDERS_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(allEnabled), GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.ROCKS_PLACED_KEY);

        //UNDERGOUNDS STRUCTURES

        //SURFACE STRUCTURES

        //STRONGHOLDS

        //UNDERGOUND ORES
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(allEnabled), GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.DIRT_PATCH_PLACED_KEY);

        //UNDERGROUND DECORATION

        //FLUID SPRINGS

        //VEGETAL DECORATION
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(allEnabled), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.BUSHES_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(normalIfEnabled), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.WHEAT_PATCH_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(normalIfEnabled), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.RANDOM_PATH_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(snowyIfEnabled), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.BERRY_PATCH_PLACED_KEY);

        //TOP LAYER MODIFICATION
        if(WildSproutTaiga.CONFIG.LayeredSnowEnabled) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(snowyIfEnabled), GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.FLUFFY_SNOW_PLACED_KEY);
        }
        //BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS), GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.SMALL_RIVER_PLACED_KEY);

        //MODIFY FEATURES
        BiomeModifications.create(WildSproutTaiga.identifier("pumpkin_patch"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.includeByKey(normalIfEnabled),
                        context -> {
                            // Identify the original feature to replace.
                            context.getGenerationSettings().removeFeature(
                                    GenerationStep.Feature.VEGETAL_DECORATION,
                                    VegetationPlacedFeatures.PATCH_PUMPKIN
                            );
                            // Add new custom pumpkin patch feature.
                            context.getGenerationSettings().addFeature(
                                    GenerationStep.Feature.VEGETAL_DECORATION,
                                    ModPlacedFeatures.PUMPKIN_PATCH_PLACED_KEY
                            );
                        }
                );

        // REMOVE FEATURES
        BiomeModifications.create(WildSproutTaiga.identifier("no_lava_spring")).add( ModificationPhase.REMOVALS,BiomeSelectors.includeByKey(allEnabled),
                context -> {
                    context.getGenerationSettings().removeFeature(
                            GenerationStep.Feature.FLUID_SPRINGS, MiscPlacedFeatures.SPRING_LAVA);});

        BiomeModifications.create(WildSproutTaiga.identifier("no_lava_lake_surface")).add( ModificationPhase.REMOVALS,BiomeSelectors.includeByKey(allEnabled),
                context -> {
                    context.getGenerationSettings().removeFeature(
                            GenerationStep.Feature.LAKES, MiscPlacedFeatures.LAKE_LAVA_SURFACE);});

        BiomeModifications.create(WildSproutTaiga.identifier("no_lava_lake_underground")).add( ModificationPhase.REMOVALS,BiomeSelectors.includeByKey(allEnabled),
                context -> {
                    context.getGenerationSettings().removeFeature(
                            GenerationStep.Feature.LAKES, MiscPlacedFeatures.LAKE_LAVA_UNDERGROUND);});

        BiomeModifications.create(WildSproutTaiga.identifier("no_flowers")).add( ModificationPhase.REMOVALS,BiomeSelectors.includeByKey(snowyIfEnabled),
                context -> {
                    context.getGenerationSettings().removeFeature(
                            GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.FLOWER_DEFAULT);});


    }
}