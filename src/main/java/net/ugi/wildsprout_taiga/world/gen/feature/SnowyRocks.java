package net.ugi.wildsprout_taiga.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.ugi.wildsprout_taiga.tags.ModTags;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.block.SnowBlock.LAYERS;

public class SnowyRocks extends Feature<DefaultFeatureConfig> {

    public SnowyRocks(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    private double biased_random_linear(Random random, double min, double max) {
        double r = random.nextDouble();
        double difference = max - min;
        return difference * (1- Math.sqrt(1-r)) + min;

    }

    public void generateRock(StructureWorldAccess structureWorldAccess, Random random, BlockPos center, BlockState block, BlockState slab){
        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()));
        DoublePerlinNoiseSampler noise = DoublePerlinNoiseSampler.create(chunkRandom, -2, new double[]{1});
        DoublePerlinNoiseSampler snowNoise = DoublePerlinNoiseSampler.create(chunkRandom, -4, new double[]{1});

        double radius = biased_random_linear(random, 0.5, 2);

        List<BlockPos> fullBlocksPos = new ArrayList<>();
        List<BlockPos> slabBlocksPos = new ArrayList<>();

        // Iterate a cube around the center
        for (BlockPos pos : BlockPos.iterate(center.add(-(int)Math.round(radius*2), -(int)Math.round(radius*2), -(int)Math.round(radius*2)), center.add((int)Math.round(radius*2), (int)Math.round(radius*2), (int)Math.round(radius*2)))) {
            double distance = center.getSquaredDistance(pos);

            // Carve a rough sphere
            if (distance <= radius * radius + noise.sample(pos.getX(), pos.getY(), pos.getZ())) {
                if (!structureWorldAccess.getBlockState(pos).isIn(ModTags.Blocks.CAN_BE_REPLACED_ALL)) continue;
                fullBlocksPos.add(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
            }
        }


        for (BlockPos pos : BlockPos.iterate(center.add(-(int)Math.round(radius*2), -(int)Math.round(radius*2), -(int)Math.round(radius*2)), center.add((int)Math.round(radius*2), (int)Math.round(radius*2), (int)Math.round(radius*2)))) {
            double distance = center.getSquaredDistance(pos);

            if (distance <= radius * radius + noise.sample(pos.getX(), pos.getY(), pos.getZ())*radius+0.7*radius) {
                if ((structureWorldAccess.getBlockState(pos).isIn(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID) && !fullBlocksPos.contains(pos)  ) &&
                        (structureWorldAccess.getBlockState(pos.down()).isIn(ModTags.Blocks.VALID_ROCK_GENERATE_SLAB_ON) || fullBlocksPos.contains(pos.down())) &&
                        (structureWorldAccess.getBlockState(pos.up()).isIn(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID) && !fullBlocksPos.contains(pos.up()))){

                    slabBlocksPos.add(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
                }
            }
        }

        List<BlockPos> allBlocksPos = new ArrayList<>(slabBlocksPos);
        allBlocksPos.addAll(fullBlocksPos);

        List<BlockPos> allBlocksPosCopyForSnow = new ArrayList<>(allBlocksPos);


        for (BlockPos pos : fullBlocksPos) {
            this.setBlockState(structureWorldAccess,pos, block);
        }

        for (BlockPos pos : slabBlocksPos) {
            this.setBlockState(structureWorldAccess,pos, slab);
        }

        List<BlockPos> allBlock2D = new ArrayList<>();
        for (BlockPos pos : allBlocksPos) {
            if (allBlock2D.stream().noneMatch(p -> p.getX() == pos.getX() && p.getZ() == pos.getZ())) {
                allBlock2D.add(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
            }
        }



        for (BlockPos pos : allBlock2D) {
            while(allBlocksPosCopyForSnow.contains(pos)) {
                pos = pos.up();
            }
            if (pos.getY() != structureWorldAccess.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,pos.getX(),pos.getZ())) continue;

            int snowlayers = (int)Math.round(Math.clamp(snowNoise.sample(pos.getX(), pos.getY(), pos.getZ())+0.5,0,2)*3) + random.nextInt(2)+1;
            if (structureWorldAccess.getBlockState(pos).equals(slab))
                snowlayers += 4;
            if (snowlayers > 8){
                this.setBlockState(structureWorldAccess,pos.down(), Blocks.SNOW_BLOCK.getDefaultState());
                this.setBlockState(structureWorldAccess, pos, Blocks.SNOW.getDefaultState().with(LAYERS, snowlayers-8));
            }
            else if (snowlayers == 8){
                this.setBlockState(structureWorldAccess,pos.down(), Blocks.SNOW_BLOCK.getDefaultState());
            }
            else{
                this.setBlockState(structureWorldAccess, pos.down(), Blocks.SNOW.getDefaultState().with(LAYERS, snowlayers));
            }
        }



    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos center = context.getOrigin();
        Random random = context.getRandom();

        int j = structureWorldAccess.getChunk(new BlockPos(center.getX(),center.getY(),center.getZ())).getHeightmap(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).get((32+center.getX()%16)%16, (32+center.getZ()%16)%16);

        center = new BlockPos(center.getX(),j,center.getZ());

        if (!(structureWorldAccess.getBlockState(center.down()).isIn(ModTags.Blocks.VALID_TAIGA_GENERATE_BLOCK))) return false;

        center = center.down();

        List<BlockState> possibleBlocks = new ArrayList<>();
        possibleBlocks.add(Blocks.STONE.getDefaultState());
        possibleBlocks.add(Blocks.TUFF.getDefaultState());
        possibleBlocks.add(Blocks.COBBLESTONE.getDefaultState());

        List<BlockState> possibleSlabs = new ArrayList<>();
        possibleSlabs.add(Blocks.STONE_SLAB.getDefaultState());
        possibleSlabs.add(Blocks.TUFF_SLAB.getDefaultState());
        possibleSlabs.add(Blocks.COBBLESTONE_SLAB.getDefaultState());

        int r = random.nextInt(possibleBlocks.size());

        generateRock(structureWorldAccess,random,center,possibleBlocks.get(r), possibleSlabs.get(r));

        return true;
    }
}