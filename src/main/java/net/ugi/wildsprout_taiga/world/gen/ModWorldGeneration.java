package net.ugi.wildsprout_taiga.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.MiscPlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.ugi.wildsprout_taiga.WildSproutTaiga;


import java.util.ArrayList;
import java.util.List;

public class ModWorldGeneration {
    public static void generateModWorldGen() {
        List<RegistryKey<Biome>> allEnabled = new ArrayList<>();
        List<RegistryKey<Biome>> snowyIfEnabled = new ArrayList<>();
        List<RegistryKey<Biome>> normalIfEnabled = new ArrayList<>();


        BiomeModifications.create(WildSproutTaiga.identifier("addition_features_all")).add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA), context -> {
            context.getGenerationSettings().addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.ROCKS_PLACED_KEY);
            context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.TREES_PLACED_KEY);
            context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.FALLEN_TREE_PLACED_KEY);
            context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.BERRY_PATCH_PLACED_KEY);

        });

        BiomeModifications.create(WildSproutTaiga.identifier("addition_features_taiga")).add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(BiomeKeys.TAIGA), context -> {
            context.getGenerationSettings().addFeature(GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.MOSS_PLACED_KEY);
        });

        //RAW GENERATION
        //LAKES

        //LOCAL MODIFICATIONS

        //BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA), GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.BOULDERS_PLACED_KEY);
        //BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA), GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.ROCKS_PLACED_KEY);


        //UNDERGOUNDS STRUCTURES

        //SURFACE STRUCTURES

        //STRONGHOLDS

        //UNDERGOUND ORES
        //BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA), GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.MOSS_PLACED_KEY);
//        BiomeModifications.create(WildSproutTaiga.identifier("moss"))
//                .add(ModificationPhase.ADDITIONS,
//                        BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA),
//                        context -> {context.getGenerationSettings().addFeature(GenerationStep.Feature.UNDERGROUND_ORES, .create(ModPlacedFeatures.MOSS_PLACED_KEY, ResourceLocation.fromNamespaceAndPath(YungsBridgesCommon.MOD_ID, "bridge_list")));});
//    );
//    }

        //UNDERGROUND DECORATION

        //FLUID SPRINGS

        //VEGETAL DECORATION
        //BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.TREES_PLACED_KEY);

        //BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.FALLEN_TREE_PLACED_KEY);

        //TOP LAYER MODIFICATION
        //BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SNOWY_TAIGA), GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.FLUFFY_SNOW_PLACED_KEY);
        //BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS), GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.SMALL_RIVER_PLACED_KEY);

        //MODIFY FEATURES
//        BiomeModifications.create(WildSproutTaiga.identifier("large_berry_bush_patch"))
//                .add(ModificationPhase.REPLACEMENTS,
//                        BiomeSelectors.includeByKey(BiomeKeys.TAIGA),
//                        context -> {
//                            // Identify the original feature to replace.
//                            context.getGenerationSettings().removeFeature(
//                                    GenerationStep.Feature.VEGETAL_DECORATION,
//                                    VegetationPlacedFeatures.PATCH_BERRY_COMMON
//                            );
//                            // Add new custom pumpkin patch feature.
//                            context.getGenerationSettings().addFeature(
//                                    GenerationStep.Feature.VEGETAL_DECORATION,
//                                    ModPlacedFeatures.BERRY_PATCH_PLACED_KEY
//                            );
//                        }
//                );

        BiomeModifications.create(WildSproutTaiga.identifier("less_old_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA),
                        context -> {
                            // Identify the original feature to replace.
                            context.getGenerationSettings().removeFeature(
                                    GenerationStep.Feature.VEGETAL_DECORATION,
                                    VegetationPlacedFeatures.TREES_TAIGA
                            );
                            // Add new custom pumpkin patch feature.
                            context.getGenerationSettings().addFeature(
                                    GenerationStep.Feature.VEGETAL_DECORATION,
                                    ModPlacedFeatures.OLD_TAIGA_TREES_PLACED_KEY
                            );
                        }
                );



        // REMOVE FEATURES
        BiomeModifications.create(WildSproutTaiga.identifier("no_lava_spring")).add( ModificationPhase.REMOVALS,BiomeSelectors.includeByKey(BiomeKeys.TAIGA),
                context -> {
                    context.getGenerationSettings().removeFeature(
                            GenerationStep.Feature.FLUID_SPRINGS, MiscPlacedFeatures.SPRING_LAVA);});

        BiomeModifications.create(WildSproutTaiga.identifier("no_lava_lake_surface")).add( ModificationPhase.REMOVALS,BiomeSelectors.includeByKey(BiomeKeys.TAIGA),
                context -> {
                    context.getGenerationSettings().removeFeature(
                            GenerationStep.Feature.LAKES, MiscPlacedFeatures.LAKE_LAVA_SURFACE);});

        BiomeModifications.create(WildSproutTaiga.identifier("no_lava_lake_underground")).add( ModificationPhase.REMOVALS,BiomeSelectors.includeByKey(BiomeKeys.TAIGA),
                context -> {
                    context.getGenerationSettings().removeFeature(
                            GenerationStep.Feature.LAKES, MiscPlacedFeatures.LAKE_LAVA_UNDERGROUND);});

        BiomeModifications.create(WildSproutTaiga.identifier("no_flowers")).add( ModificationPhase.REMOVALS,BiomeSelectors.includeByKey(BiomeKeys.TAIGA),
                context -> {
                    context.getGenerationSettings().removeFeature(
                            GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.FLOWER_DEFAULT);});

        BiomeModifications.create(WildSproutTaiga.identifier("no_pumpkins")).add( ModificationPhase.REMOVALS,BiomeSelectors.includeByKey(BiomeKeys.TAIGA),
                context -> {
                    context.getGenerationSettings().removeFeature(
                            GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_PUMPKIN);});
//
//        BiomeModifications.create(WildSproutTaiga.identifier("no_old_trees")).add( ModificationPhase.REMOVALS,BiomeSelectors.includeByKey(BiomeKeys.TAIGA),
//                context -> {
//                    context.getGenerationSettings().removeFeature(
//                            GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.TREES_TAIGA);});


    }
}