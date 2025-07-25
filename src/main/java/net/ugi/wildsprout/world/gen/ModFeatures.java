package net.ugi.wildsprout.world.gen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import net.ugi.wildsprout.WildSproutTaiga;
import net.ugi.wildsprout.world.gen.feature.Boulders;
import net.ugi.wildsprout.world.gen.feature.FluffySnow;
import net.ugi.wildsprout.world.gen.feature.Rocks;


public class ModFeatures {
    public static final Feature<DefaultFeatureConfig> BOULDERS = new Boulders(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig>ROCKS = new Rocks(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> FLUFFY_SNOW = new FluffySnow(DefaultFeatureConfig.CODEC);

    public static void init(){

        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "boulders"), BOULDERS);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "rocks"), ROCKS);
        Registry.register(Registries.FEATURE, WildSproutTaiga.identifier( "fluffy_snow"), FLUFFY_SNOW);

    }
}
