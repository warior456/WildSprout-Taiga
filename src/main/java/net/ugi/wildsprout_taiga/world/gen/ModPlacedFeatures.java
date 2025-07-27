package net.ugi.wildsprout_taiga.world.gen;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.ugi.wildsprout_taiga.WildSproutTaiga;


import java.util.List;

public class ModPlacedFeatures {

    //-------------------------
    public static final RegistryKey<PlacedFeature> BOULDERS_PLACED_KEY = registerKey("boulders");
    public static final RegistryKey<PlacedFeature> ROCKS_PLACED_KEY = registerKey("rocks");
    public static final RegistryKey<PlacedFeature> WHEAT_PATCH_PLACED_KEY = registerKey("wheat_patch");
    public static final RegistryKey<PlacedFeature> MOSS_PLACED_KEY = registerKey("moss");
    public static final RegistryKey<PlacedFeature> BUSHES_PLACED_KEY = registerKey("bushes");
    public static final RegistryKey<PlacedFeature> SMALL_RIVER_PLACED_KEY = registerKey("small_river");
    public static final RegistryKey<PlacedFeature> LAKE_PLACED_KEY = registerKey("lake");
    //public static final RegistryKey<PlacedFeature> SMALL_RIVER_PLACED_KEY = registerKey("small_river");
    public static final RegistryKey<PlacedFeature> PUMPKIN_PATCH_PLACED_KEY = registerKey("pumpkin_patch");
    public static final RegistryKey<PlacedFeature> RANDOM_PATH_PLACED_KEY = registerKey("random_path");
    public static final RegistryKey<PlacedFeature> BERRY_PATCH_PLACED_KEY = registerKey("berry_patch");
    public static final RegistryKey<PlacedFeature> FLUFFY_SNOW_PLACED_KEY = registerKey("fluffy_snow");
    public static final RegistryKey<PlacedFeature> TREES_PLACED_KEY = registerKey("trees");
    public static final RegistryKey<PlacedFeature> FALLEN_TREE_PLACED_KEY = registerKey("fallen_tree");
    public static final RegistryKey<PlacedFeature> OLD_TAIGA_TREES_PLACED_KEY = registerKey("old_taiga_trees");


    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        //-------------------------
        register(context,BOULDERS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.BOULDERS_KEY), RarityFilterPlacementModifier.of(64), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,ROCKS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ROCKS_KEY), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
/*
        register(context,WHEAT_PATCH_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.WHEAT_PATCH_KEY), CountPlacementModifier.of(20), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        */
        register(context,MOSS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.MOSS_KEY), CountPlacementModifier.of(20), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        /*
        register(context,BUSHES_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.BUSHES_KEY), RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,SMALL_RIVER_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.SMALL_RIVER_KEY), RarityFilterPlacementModifier.of(256), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,LAKE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.LAKE_KEY), RarityFilterPlacementModifier.of(30), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        //register(context,SMALL_RIVER_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.SMALL_RIVER_KEY), RarityFilterPlacementModifier.of(256), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,PUMPKIN_PATCH_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.PUMPKIN_PATCH_KEY), RarityFilterPlacementModifier.of(300), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,RANDOM_PATH_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.RANDOM_PATH_KEY), RarityFilterPlacementModifier.of(60), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        */
        register(context,BERRY_PATCH_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.BERRY_PATCH_KEY), RarityFilterPlacementModifier.of(40), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,FLUFFY_SNOW_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.FLUFFY_SNOW_KEY), CountPlacementModifier.of(32), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,TREES_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.TREES_KEY), CountPlacementModifier.of(5), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,FALLEN_TREE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.FALLEN_TREE_KEY), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,OLD_TAIGA_TREES_PLACED_KEY, configuredFeatures.getOrThrow(VegetationConfiguredFeatures.TREES_TAIGA), CountPlacementModifier.of(5), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of());


    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(WildSproutTaiga.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                                                                   PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}