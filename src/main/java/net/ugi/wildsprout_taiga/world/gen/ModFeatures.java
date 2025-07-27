package net.ugi.wildsprout_taiga.world.gen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import net.ugi.wildsprout_taiga.WildSproutTaiga;
import net.ugi.wildsprout_taiga.world.gen.feature.*;


public class ModFeatures {
    public static final Feature<DefaultFeatureConfig> BOULDERS = new Boulders(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig>ROCKS = new Rocks(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> FLUFFY_SNOW = new FluffySnow(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> MOSS = new Moss(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> TREES = new Trees(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> FALLEN_TREE = new FallenTree(DefaultFeatureConfig.CODEC);

    public static void init(){

        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "boulders"), BOULDERS);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "rocks"), ROCKS);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "fluffy_snow"), FLUFFY_SNOW);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "moss"), MOSS);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "fallen_tree"), FALLEN_TREE);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "trees"), TREES);

    }
}
