package net.ugi.wildsprout.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BlockFilterPlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.ugi.wildsprout.WildSproutTaiga;
import net.ugi.wildsprout.tags.ModTags;

public class ModConfiguredFeatures {
    //-------------------------
    public static final RegistryKey<ConfiguredFeature<?,?>> BOULDERS_KEY = registerKey("boulders");
    public static final RegistryKey<ConfiguredFeature<?,?>> ROCKS_KEY = registerKey("rocks");
    public static final RegistryKey<ConfiguredFeature<?,?>> WHEAT_PATCH_KEY = registerKey("wheat_patch");

    public static final RegistryKey<ConfiguredFeature<?,?>> MOSS_KEY = registerKey("moss");
    public static final RegistryKey<ConfiguredFeature<?,?>> BUSHES_KEY = registerKey("bushes");
    public static final RegistryKey<ConfiguredFeature<?,?>> SMALL_RIVER_KEY = registerKey("small_river");
    public static final RegistryKey<ConfiguredFeature<?,?>> LAKE_KEY = registerKey("lake");
    //public static final RegistryKey<ConfiguredFeature<?,?>> SMALL_RIVER_KEY = registerKey("small_river");
    public static final RegistryKey<ConfiguredFeature<?,?>> PUMPKIN_PATCH_KEY = registerKey("pumpkin_patch");
    public static final RegistryKey<ConfiguredFeature<?,?>> RANDOM_PATH_KEY = registerKey("random_path");
    public static final RegistryKey<ConfiguredFeature<?,?>> BERRY_PATCH_KEY = registerKey("berry_patch");
    public static final RegistryKey<ConfiguredFeature<?,?>> FLUFFY_SNOW_KEY = registerKey("fluffy_snow");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {

        //-------------------------
        register(context,BOULDERS_KEY, ModFeatures.BOULDERS, new DefaultFeatureConfig());
        register(context,ROCKS_KEY, ModFeatures.ROCKS, new DefaultFeatureConfig());
        register(context, FLUFFY_SNOW_KEY, ModFeatures.FLUFFY_SNOW, new DefaultFeatureConfig());
        register(context,MOSS_KEY, ModFeatures.MOSS, new DefaultFeatureConfig());
        register(context, BERRY_PATCH_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(512, 16, 4, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.SWEET_BERRY_BUSH.getDefaultState().with(SweetBerryBushBlock.AGE,3))),
                BlockPredicate.allOf(new BlockPredicate[]{BlockPredicate.matchingBlocks(Direction.DOWN.getVector(), new Block[]{Blocks.GRASS_BLOCK, Blocks.MOSS_BLOCK})}))));


    }
    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(WildSproutTaiga.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}