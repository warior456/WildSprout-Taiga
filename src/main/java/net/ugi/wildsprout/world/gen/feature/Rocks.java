package net.ugi.wildsprout.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.ugi.wildsprout.tags.ModTags;

import java.util.ArrayList;
import java.util.List;

public class Rocks extends Feature<DefaultFeatureConfig> {

    public Rocks(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    public void generateRock(StructureWorldAccess structureWorldAccess, Random random, BlockPos center, BlockState block, BlockState slab){
        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()));
        DoublePerlinNoiseSampler noise = DoublePerlinNoiseSampler.create(chunkRandom, -2, new double[]{1});

        double radius = 0.5 + random.nextDouble();



        // Iterate a cube around the center
        for (BlockPos pos : BlockPos.iterate(center.add(-(int)Math.round(radius*2), -(int)Math.round(radius*2), -(int)Math.round(radius*2)), center.add((int)Math.round(radius*2), (int)Math.round(radius*2), (int)Math.round(radius*2)))) {
            double distance = center.getSquaredDistance(pos);

            // Carve a rough sphere
            if (distance <= radius * radius + noise.sample(pos.getX(), pos.getY(), pos.getZ())) {
                if (!structureWorldAccess.getBlockState(pos).isIn(ModTags.Blocks.CAN_BE_REPLACED_ALL)) continue;
                structureWorldAccess.setBlockState(pos, block,2);
            }
        }

        for (BlockPos pos : BlockPos.iterate(center.add(-(int)Math.round(radius*2), -(int)Math.round(radius*2), -(int)Math.round(radius*2)), center.add((int)Math.round(radius*2), (int)Math.round(radius*2), (int)Math.round(radius*2)))) {
            double distance = center.getSquaredDistance(pos);

            if (distance <= radius * radius + noise.sample(pos.getX(), pos.getY(), pos.getZ())+0.7) {
                if (structureWorldAccess.getBlockState(pos).isIn(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID) &&
                        (structureWorldAccess.getBlockState(pos.down()).isIn(ModTags.Blocks.VALID_ROCK_GENERATE_SLAB_ON)) &&
                        structureWorldAccess.getBlockState(pos.up()).isIn(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID)){
                    structureWorldAccess.setBlockState(pos, slab,2);
                }
            }
        }
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos center = context.getOrigin();
        Random random = context.getRandom();

        int j = structureWorldAccess.getChunk(new BlockPos(center.getX(),center.getY(),center.getZ())).getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get((32+center.getX()%16)%16, (32+center.getZ()%16)%16);

        center = new BlockPos(center.getX(),j,center.getZ());

        if (!(structureWorldAccess.getBlockState(center.down()).isIn(ModTags.Blocks.VALID_PLAINS_GENERATE_BLOCK))) return false;

        center = center.down();

        List<BlockState> possibleBlocks = new ArrayList<>();
        possibleBlocks.add(Blocks.STONE.getDefaultState());
        possibleBlocks.add(Blocks.ANDESITE.getDefaultState());
        possibleBlocks.add(Blocks.GRANITE.getDefaultState());
        possibleBlocks.add(Blocks.DIORITE.getDefaultState());
        possibleBlocks.add(Blocks.TUFF.getDefaultState());

        List<BlockState> possibleSlabs = new ArrayList<>();
        possibleSlabs.add(Blocks.STONE_SLAB.getDefaultState());
        possibleSlabs.add(Blocks.ANDESITE_SLAB.getDefaultState());
        possibleSlabs.add(Blocks.GRANITE_SLAB.getDefaultState());
        possibleSlabs.add(Blocks.DIORITE_SLAB.getDefaultState());
        possibleSlabs.add(Blocks.TUFF_SLAB.getDefaultState());

        int r = random.nextInt(possibleBlocks.size());

        generateRock(structureWorldAccess,random,center,possibleBlocks.get(r), possibleSlabs.get(r));

        return true;
    }
}