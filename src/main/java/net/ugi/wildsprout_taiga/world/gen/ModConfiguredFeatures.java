package net.ugi.wildsprout_taiga.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.ugi.wildsprout_taiga.WildSproutTaiga;

import java.util.List;

public class ModConfiguredFeatures {
    //-------------------------
    public static final RegistryKey<ConfiguredFeature<?,?>> BOULDERS_KEY = registerKey("boulders");
    public static final RegistryKey<ConfiguredFeature<?,?>> MOSSY_ROCKS_KEY = registerKey("mossy_rocks");
    public static final RegistryKey<ConfiguredFeature<?,?>> SNOWY_ROCKS_KEY = registerKey("snowy_rocks");
    public static final RegistryKey<ConfiguredFeature<?,?>> MOSS_KEY = registerKey("moss");
    public static final RegistryKey<ConfiguredFeature<?,?>> MOSS_PATCH_KEY = registerKey("moss_patch");
    public static final RegistryKey<ConfiguredFeature<?,?>> BERRY_PATCH_KEY = registerKey("berry_patch");
    public static final RegistryKey<ConfiguredFeature<?,?>> FLUFFY_SNOW_KEY = registerKey("fluffy_snow");
    public static final RegistryKey<ConfiguredFeature<?,?>> FALLEN_TREE_KEY = registerKey("fallen_tree");
    public static final RegistryKey<ConfiguredFeature<?,?>> TREES_KEY = registerKey("trees");
    public static final RegistryKey<ConfiguredFeature<?,?>> RANDOM_PATH_KEY = registerKey("random_path");
    public static final RegistryKey<ConfiguredFeature<?,?>> REPLACED_TREES_OLD_GROWTH_SPRUCE_TAIGA_KEY = registerKey("replaced_trees_old_growth_spruce_taiga");

    public static final RegistryKey<ConfiguredFeature<?,?>> NEW_MEGA_SPRUCE_KEY = registerKey("new_mega_spruce");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {

        RegistryEntryLookup<PlacedFeature> registryEntryLookup = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
        RegistryEntry<PlacedFeature> originalMegaSpruce = registryEntryLookup.getOrThrow(TreePlacedFeatures.MEGA_SPRUCE_CHECKED);
        RegistryEntry<PlacedFeature> originalMegaPine = registryEntryLookup.getOrThrow(TreePlacedFeatures.MEGA_PINE_CHECKED);
        RegistryEntry<PlacedFeature> originalSpruce = registryEntryLookup.getOrThrow(TreePlacedFeatures.SPRUCE_CHECKED);
        RegistryEntry<PlacedFeature> originalPine = registryEntryLookup.getOrThrow(TreePlacedFeatures.PINE_CHECKED);

        RegistryEntry<PlacedFeature> newSpruce = registryEntryLookup.getOrThrow(ModPlacedFeatures.TREES_RAW_PLACED_KEY);
        RegistryEntry<PlacedFeature> newMegaSpruce = registryEntryLookup.getOrThrow(ModPlacedFeatures.NEW_MEGA_SPRUCE_PLACED_KEY);


        //-------------------------
        register(context,BOULDERS_KEY, ModFeatures.BOULDERS, new DefaultFeatureConfig());
        register(context,MOSSY_ROCKS_KEY, ModFeatures.MOSSY_ROCKS, new DefaultFeatureConfig());
        register(context,SNOWY_ROCKS_KEY, ModFeatures.SNOWY_ROCKS, new DefaultFeatureConfig());
        register(context, FLUFFY_SNOW_KEY, ModFeatures.FLUFFY_SNOW, new DefaultFeatureConfig());
        register(context,MOSS_KEY, ModFeatures.MOSS, new DefaultFeatureConfig());
        register(context,MOSS_PATCH_KEY, ModFeatures.MOSS_PATCH, new DefaultFeatureConfig());
        register(context,FALLEN_TREE_KEY, ModFeatures.FALLEN_TREE, new DefaultFeatureConfig());
        register(context, BERRY_PATCH_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(512, 16, 4, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.SWEET_BERRY_BUSH.getDefaultState().with(SweetBerryBushBlock.AGE,3))),
                BlockPredicate.allOf(new BlockPredicate[]{BlockPredicate.matchingBlocks(Direction.DOWN.getVector(), new Block[]{Blocks.GRASS_BLOCK, Blocks.MOSS_BLOCK})}))));
        register(context,TREES_KEY, ModFeatures.TREES, new DefaultFeatureConfig());
        register(context,RANDOM_PATH_KEY, ModFeatures.RANDOM_PATH, new DefaultFeatureConfig());

        register(context,NEW_MEGA_SPRUCE_KEY, ModFeatures.MEGA_SPRUCE_TREES, new DefaultFeatureConfig());
        //replace old growth trees
        ConfiguredFeatures.register(
                context,
                REPLACED_TREES_OLD_GROWTH_SPRUCE_TAIGA_KEY,
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfig(List.of(new RandomFeatureEntry(originalMegaSpruce, 0.33333334F), new RandomFeatureEntry(originalMegaPine, 0.33333334F), new RandomFeatureEntry(newSpruce, 0.86f)), originalSpruce)
        );




    }


    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(WildSproutTaiga.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}