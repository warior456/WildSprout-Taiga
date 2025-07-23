package net.ugi.wildsprout.world.gen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.ugi.wildsprout.WildSproutTaiga;
import net.ugi.wildsprout.world.gen.feature.*;

public class ModFeatures {
    public static final Feature<DefaultFeatureConfig> BOULDERS = new Boulders(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig>ROCKS = new Rocks(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> WHEAT_PATCH = new WheatPatch(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> DIRT_PATCH = new DirtPatch(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> BUSHES = new Bushes(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> SMALL_RIVER = new SmallRiver(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> LAKE = new Lake(DefaultFeatureConfig.CODEC);
    //public static final Feature<DefaultFeatureConfig> SMALL_RIVER = new SmallRiver(DefaultFeatureConfig.CODEC);
    public static final Feature<RandomPatchFeatureConfig> PUMPKIN_PATCH = new PumpkinPatch(RandomPatchFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> RANDOM_PATH = new RandomPath(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> FLUFFY_SNOW = new FluffySnow(DefaultFeatureConfig.CODEC);

    public static void init(){

        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "boulders"), BOULDERS);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "rocks"), ROCKS);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "wheat_patch"), WHEAT_PATCH);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "dirt_patch"), DIRT_PATCH);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "bushes"), BUSHES);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "small_river"), SMALL_RIVER);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "lake"), LAKE);
        //Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "small_river"), SMALL_RIVER);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "pumpkin_patch"), PUMPKIN_PATCH);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "random_path"), RANDOM_PATH);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "fluffy_snow"), FLUFFY_SNOW);
    }
}
