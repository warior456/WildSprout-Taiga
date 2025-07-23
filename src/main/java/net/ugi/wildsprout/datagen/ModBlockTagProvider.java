package net.ugi.wildsprout.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.ugi.wildsprout.tags.ModTags;


import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        getOrCreateTagBuilder(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID)
                .add(Blocks.AIR)
                .add(Blocks.SHORT_GRASS)
                .add(Blocks.SNOW);

        getOrCreateTagBuilder(ModTags.Blocks.CAN_BE_REPLACED_SOLID)
                .add(Blocks.GRASS_BLOCK)
                .add(Blocks.DIRT)
                .add(Blocks.COARSE_DIRT);

        getOrCreateTagBuilder(ModTags.Blocks.CAN_BE_REPLACED_ALL)
                .addTag(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID)
                .addTag(ModTags.Blocks.CAN_BE_REPLACED_SOLID);

        getOrCreateTagBuilder(ModTags.Blocks.VALID_PLAINS_GENERATE_BLOCK)
                .add(Blocks.GRASS_BLOCK)
                .add(Blocks.DIRT)
                .add(Blocks.COARSE_DIRT);

        getOrCreateTagBuilder(ModTags.Blocks.OVERRIDE_SNOW_LAYER_CANNOT_SURVIVE_ON)
                .add(Blocks.ICE)
                .add(Blocks.PACKED_ICE)
                .add(Blocks.BLUE_ICE);

        getOrCreateTagBuilder(ModTags.Blocks.VALID_ROCK_GENERATE_SLAB_ON)
                .addTag(ModTags.Blocks.CAN_BE_REPLACED_SOLID)
                .add(Blocks.MUD)
                .add(Blocks.STONE)
                .add(Blocks.MOSSY_COBBLESTONE)
                .add(Blocks.TUFF)
                .add(Blocks.DIORITE)
                .add(Blocks.ANDESITE)
                .add(Blocks.GRANITE);


    }
}