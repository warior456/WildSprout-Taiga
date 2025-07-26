package net.ugi.wildsprout_taiga.tags;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.ugi.wildsprout_taiga.WildSproutTaiga;


public class ModTags {

    public static class Blocks {

        public static final TagKey<Block> CAN_BE_REPLACED_NON_SOLID =
                createTag("can_be_replaced_non_solid");

        public static final TagKey<Block> CAN_BE_REPLACED_SOLID =
                createTag("can_be_replaced_solid");

        public static final TagKey<Block> CAN_BE_REPLACED_ALL =
                createTag("can_be_replaced_all");

        public static final TagKey<Block> VALID_PLAINS_GENERATE_BLOCK =
                createTag("valid_plains_generate_block");

        public static final TagKey<Block> VALID_ROCK_GENERATE_SLAB_ON =
                createTag("valid_rock_generate_slab_on");

        public static final TagKey<Block> OVERRIDE_SNOW_LAYER_CANNOT_SURVIVE_ON =
                createTag("override_snow_layer_cannot_survive_on");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, WildSproutTaiga.identifier( name));
        }
    }

    public static class Biome {

/*
        public static final TagKey<net.minecraft.world.biome.Biome> HAS_RIVER =
                createTag("has_river");
*/


        private static TagKey<net.minecraft.world.biome.Biome> createTag(String name) {
            return TagKey.of(RegistryKeys.BIOME, WildSproutTaiga.identifier( name));
        }
    }

    public static class Items {


        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, WildSproutTaiga.identifier( name));
        }
    }

    public static class Fluids {

        private static TagKey<Fluid> of(String name) {
            return TagKey.of(RegistryKeys.FLUID, WildSproutTaiga.identifier( name));
        }
    }

}
