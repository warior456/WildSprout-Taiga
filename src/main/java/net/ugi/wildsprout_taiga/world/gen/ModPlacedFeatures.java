package net.ugi.wildsprout_taiga.world.gen;

import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.ugi.wildsprout_taiga.WildSproutTaiga;


import java.util.List;

import static net.minecraft.world.gen.feature.VegetationPlacedFeatures.treeModifiers;

public class ModPlacedFeatures {

    //-------------------------
    public static final RegistryKey<PlacedFeature> BOULDERS_PLACED_KEY = registerKey("boulders");
    public static final RegistryKey<PlacedFeature> MOSSY_ROCKS_PLACED_KEY = registerKey("mossy_rocks");
    public static final RegistryKey<PlacedFeature> SNOWY_ROCKS_PLACED_KEY = registerKey("snowy_rocks");
    public static final RegistryKey<PlacedFeature> MOSS_PLACED_KEY = registerKey("moss");
    public static final RegistryKey<PlacedFeature> MOSS_PATCH_PLACED_KEY = registerKey("moss_patch");
    public static final RegistryKey<PlacedFeature> BERRY_PATCH_PLACED_KEY = registerKey("berry_patch");
    public static final RegistryKey<PlacedFeature> FLUFFY_SNOW_PLACED_KEY = registerKey("fluffy_snow");
    public static final RegistryKey<PlacedFeature> TREES_PLACED_KEY = registerKey("trees");
    public static final RegistryKey<PlacedFeature> FALLEN_TREE_PLACED_KEY = registerKey("fallen_tree");
    public static final RegistryKey<PlacedFeature> ORIGINAL_TAIGA_TREES_PLACED_KEY = registerKey("original_taiga_trees");
    public static final RegistryKey<PlacedFeature> REPLACED_OLD_GROWTH_SPRUCE_TREES_PLACED_KEY = registerKey("replaced_old_growth_spruce_trees");
    public static final RegistryKey<PlacedFeature> RANDOM_PATH_PLACED_KEY = registerKey("random_path");
    public static final RegistryKey<PlacedFeature> NEW_MEGA_SPRUCE_PLACED_KEY = registerKey("new_mega_spruce");



    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        //-------------------------
        register(context,BOULDERS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.BOULDERS_KEY), RarityFilterPlacementModifier.of(64), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,MOSSY_ROCKS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.MOSSY_ROCKS_KEY), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,SNOWY_ROCKS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.SNOWY_ROCKS_KEY), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,MOSS_PATCH_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.MOSS_PATCH_KEY), CountPlacementModifier.of(32), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,MOSS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.MOSS_KEY), CountPlacementModifier.of(32), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,BERRY_PATCH_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.BERRY_PATCH_KEY), RarityFilterPlacementModifier.of(40), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,FLUFFY_SNOW_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.FLUFFY_SNOW_KEY), CountPlacementModifier.of(32), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,TREES_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.TREES_KEY), CountPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,FALLEN_TREE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.FALLEN_TREE_KEY), RarityFilterPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,ORIGINAL_TAIGA_TREES_PLACED_KEY, configuredFeatures.getOrThrow(VegetationConfiguredFeatures.TREES_TAIGA), CountPlacementModifier.of(5), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,RANDOM_PATH_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.RANDOM_PATH_KEY), RarityFilterPlacementModifier.of(60), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context,REPLACED_OLD_GROWTH_SPRUCE_TREES_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.REPLACED_TREES_OLD_GROWTH_SPRUCE_TAIGA_KEY), treeModifiers(PlacedFeatures.createCountExtraModifier(10, 0.1F, 1)));


        register(context,NEW_MEGA_SPRUCE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NEW_MEGA_SPRUCE_KEY), PlacedFeatures.wouldSurvive(Blocks.SPRUCE_SAPLING));
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