package net.ugi.wildsprout_taiga.world.gen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import net.ugi.wildsprout_taiga.WildSproutTaiga;
import net.ugi.wildsprout_taiga.world.gen.feature.*;

/*
ONLY USE THESE HEIGHTMAPS IN WORLD GEN, IF OTHER ARE USED, IT WILL BUG ON WORLD SAVING / RELOADING
Heightmap.Type.MOTION_BLOCKING
Heightmap.Type.MOTION_BLOCKING_NO_LEAVES
Heightmap.Type.OCEAN_FLOOR
Heightmap.Type.WORLD_SURFACE

NO HEIGHTMAPS WITH _WG, THEY ARE REMOVED AFTER CARVERS
https://minecraft.wiki/w/Heightmap
 */

public class ModFeatures {
    public static final Feature<DefaultFeatureConfig> BOULDERS = new Boulders(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> MOSSY_ROCKS = new MossyRocks(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> OLD_GROWTH_MOSSY_ROCKS = new OldGrowthMossyRocks(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> SNOWY_ROCKS = new SnowyRocks(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> FLUFFY_SNOW = new FluffySnow(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> MOSS = new Moss(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> MOSS_PATCH = new MossPatch(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> TREES = new Trees(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> MEGA_SPRUCE_TREES = new MegaSpruceTrees(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> MEGA_PINE_TREES = new MegaPineTrees(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> FALLEN_TREE = new FallenTree(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> RANDOM_PATH = new RandomPath(DefaultFeatureConfig.CODEC);

    public static void init(){
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "boulders"), BOULDERS);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "mossy_rocks"), MOSSY_ROCKS);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "old_growth_mossy_rocks"), OLD_GROWTH_MOSSY_ROCKS);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "snowy_rocks"), SNOWY_ROCKS);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "fluffy_snow"), FLUFFY_SNOW);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "moss"), MOSS);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "moss_patch"), MOSS_PATCH);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "fallen_tree"), FALLEN_TREE);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "trees"), TREES);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "mega_spruce_trees"), MEGA_SPRUCE_TREES);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "mega_pine_trees"), MEGA_PINE_TREES);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "random_path"), RANDOM_PATH);

    }
}
