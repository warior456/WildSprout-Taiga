package net.ugi.wildsprout_taiga.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.ugi.wildsprout_taiga.tags.ModTags;

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
                .add(Blocks.TALL_GRASS)
                .add(Blocks.SNOW)
                .add(Blocks.FERN)
                .add(Blocks.MOSS_CARPET)
                .add(Blocks.BROWN_MUSHROOM)
                .add(Blocks.RED_MUSHROOM)
                .add(Blocks.MOSS_CARPET)
                .add(Blocks.SWEET_BERRY_BUSH)
                .add(Blocks.LARGE_FERN);

        getOrCreateTagBuilder(ModTags.Blocks.CAN_BE_REPLACED_SOLID)
                .add(Blocks.GRASS_BLOCK)
                .add(Blocks.DIRT)
                .add(Blocks.COARSE_DIRT)
                .add(Blocks.MOSS_BLOCK);

        getOrCreateTagBuilder(ModTags.Blocks.CAN_BE_REPLACED_ALL)
                .addTag(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID)
                .addTag(ModTags.Blocks.CAN_BE_REPLACED_SOLID);

        getOrCreateTagBuilder(ModTags.Blocks.VALID_TAIGA_GENERATE_BLOCK)
                .add(Blocks.GRASS_BLOCK)
                .add(Blocks.DIRT)
                .add(Blocks.COARSE_DIRT)
                .add(Blocks.MOSS_BLOCK);

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

        getOrCreateTagBuilder(BlockTags.MUSHROOM_GROW_BLOCK)
                .add(Blocks.SPRUCE_LOG);

    }
}