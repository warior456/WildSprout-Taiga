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

    private List<BlockPos> getmossCarpetPos(List<BlockPos> fullBlockPos, List<BlockPos> slabBlockPos) {
        List<BlockPos> fullBlock2D = new ArrayList<>();
        for (BlockPos pos : fullBlockPos) {
            if (fullBlock2D.stream().noneMatch(p -> p.getX() == pos.getX() && p.getZ() == pos.getZ())) {
                fullBlock2D.add(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
            }
        }

        List<BlockPos> slabBlocks2D = new ArrayList<>();
        for (BlockPos pos : slabBlockPos) {
            if (slabBlocks2D.stream().noneMatch(p -> p.getX() == pos.getX() && p.getZ() == pos.getZ())) {
                slabBlocks2D.add(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
            }
        }
        fullBlock2D.removeAll(slabBlocks2D);
        return fullBlock2D;
    }

    protected boolean canPlaceMossAt(StructureWorldAccess world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        return Block.isFaceFullSquare(blockState.getCollisionShape(world, pos.down()), Direction.UP) || blockState.isOf(Blocks.SNOW) && (Integer)blockState.get(LAYERS) == 8;
    }

    private void mossBlock(List<BlockPos> allBlocksPos, List<BlockPos> fullBlocksPos, List<BlockPos> slabBlocksPos, StructureWorldAccess structureWorldAccess, BlockPos pos) {
        if (!allBlocksPos.contains(pos)) return;
        if (fullBlocksPos.contains(pos)) {
            this.setBlockState(structureWorldAccess, pos, Blocks.MOSS_BLOCK.getDefaultState());
        }
        allBlocksPos.remove(pos);
        fullBlocksPos.remove(pos);
        slabBlocksPos.remove(pos);

        this.mossyCobbleBlock(allBlocksPos,  fullBlocksPos,  slabBlocksPos, structureWorldAccess, pos.up());
        this.mossyCobbleBlock(allBlocksPos,  fullBlocksPos,  slabBlocksPos, structureWorldAccess, pos.down());
        this.mossyCobbleBlock(allBlocksPos,  fullBlocksPos,  slabBlocksPos, structureWorldAccess, pos.north());
        this.mossyCobbleBlock(allBlocksPos,  fullBlocksPos,  slabBlocksPos, structureWorldAccess, pos.south());
        this.mossyCobbleBlock(allBlocksPos,  fullBlocksPos,  slabBlocksPos, structureWorldAccess, pos.east());
        this.mossyCobbleBlock(allBlocksPos,  fullBlocksPos,  slabBlocksPos, structureWorldAccess, pos.west());
    }

    private void mossyCobbleBlock(List<BlockPos> allBlocksPos, List<BlockPos> fullBlocksPos, List<BlockPos> slabBlocksPos, StructureWorldAccess structureWorldAccess, BlockPos pos) {
        if (!allBlocksPos.contains(pos)) return;
        if (fullBlocksPos.contains(pos)) {
            this.setBlockState(structureWorldAccess, pos, Blocks.MOSSY_COBBLESTONE.getDefaultState());
        }
        else if (slabBlocksPos.contains(pos)) {
            this.setBlockState(structureWorldAccess, pos, Blocks.MOSSY_COBBLESTONE_SLAB.getDefaultState());
        }
        allBlocksPos.remove(pos);
        fullBlocksPos.remove(pos);
        slabBlocksPos.remove(pos);

        this.tuffBlock(allBlocksPos,  fullBlocksPos,  slabBlocksPos, structureWorldAccess, pos.up());
        this.tuffBlock(allBlocksPos,  fullBlocksPos,  slabBlocksPos, structureWorldAccess, pos.down());
        this.tuffBlock(allBlocksPos,  fullBlocksPos,  slabBlocksPos, structureWorldAccess, pos.north());
        this.tuffBlock(allBlocksPos,  fullBlocksPos,  slabBlocksPos, structureWorldAccess, pos.south());
        this.tuffBlock(allBlocksPos,  fullBlocksPos,  slabBlocksPos, structureWorldAccess, pos.east());
        this.tuffBlock(allBlocksPos,  fullBlocksPos,  slabBlocksPos, structureWorldAccess, pos.west());

    }

    private void tuffBlock(List<BlockPos> allBlocksPos, List<BlockPos> fullBlocksPos, List<BlockPos> slabBlocksPos, StructureWorldAccess structureWorldAccess, BlockPos pos) {
        if (fullBlocksPos.contains(pos)) {
            this.setBlockState(structureWorldAccess, pos, Blocks.TUFF.getDefaultState());
        }
        else if (slabBlocksPos.contains(pos)) {
            this.setBlockState(structureWorldAccess, pos, Blocks.TUFF_SLAB.getDefaultState());
        }
        allBlocksPos.remove(pos);
        fullBlocksPos.remove(pos);
        slabBlocksPos.remove(pos);

    }

    public void generateRock(StructureWorldAccess structureWorldAccess, Random random, BlockPos center, BlockState block, BlockState slab){
        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()));
        DoublePerlinNoiseSampler noise = DoublePerlinNoiseSampler.create(chunkRandom, -2, new double[]{1});

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

        List<BlockPos> mossCarpetPos = this.getmossCarpetPos(fullBlocksPos, slabBlocksPos);
        List<BlockPos> allBlocksPosCopyForMoss = new ArrayList<>(allBlocksPos);

        int rockSize = allBlocksPos.size();

        if (rockSize > 6 &&  rockSize < 15) {
            int i = 0;
            int amount = random.nextInt(2) + 1;
            List<BlockPos> allBlocksPosCopy = new ArrayList<>(allBlocksPos);
            while (i < amount && !allBlocksPosCopy.isEmpty()) {
                BlockPos randomPos = allBlocksPosCopy.get(random.nextInt(allBlocksPosCopy.size()));
                if (randomPos.getY() >= center.getY()) {
                    this.mossyCobbleBlock(allBlocksPos, fullBlocksPos, slabBlocksPos, structureWorldAccess, randomPos);
                    i++;
                }
//                else {
//                    this.tuffBlock(allBlocksPos, fullBlocksPos, slabBlocksPos, structureWorldAccess, randomPos);
//                }
                allBlocksPosCopy.remove(randomPos);
            }
        }
        else {
            int i = 0;
            int amount = random.nextInt(2) + 2;
            List<BlockPos> fullBlocksPosCopy = new ArrayList<>(fullBlocksPos);
            while (i < amount && !fullBlocksPosCopy.isEmpty()) {
                BlockPos randomPos = fullBlocksPosCopy.get(random.nextInt(fullBlocksPosCopy.size()));
                if (randomPos.getY() > center.getY()) {
                    this.mossBlock(allBlocksPos, fullBlocksPos, slabBlocksPos, structureWorldAccess, randomPos);
                    i++;
                }
//                else {
//                    this.tuffBlock(allBlocksPos, fullBlocksPos, slabBlocksPos, structureWorldAccess, randomPos);
//                }
                fullBlocksPosCopy.remove(randomPos);
            }
        }


        for (BlockPos pos : fullBlocksPos) {
            this.setBlockState(structureWorldAccess,pos, block);
        }

        for (BlockPos pos : slabBlocksPos) {
            this.setBlockState(structureWorldAccess,pos, slab);
        }



        for (BlockPos pos : mossCarpetPos) {
            if (random.nextDouble() > 0.7) continue;
            while(allBlocksPosCopyForMoss.contains(pos)) {
                pos = pos.up();
            }
            if (!this.canPlaceMossAt(structureWorldAccess, pos)) continue;
            this.setBlockState(structureWorldAccess, pos, Blocks.MOSS_CARPET.getDefaultState());
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
        possibleBlocks.add(Blocks.MOSSY_COBBLESTONE.getDefaultState());

        List<BlockState> possibleSlabs = new ArrayList<>();
        possibleSlabs.add(Blocks.STONE_SLAB.getDefaultState());
        possibleSlabs.add(Blocks.TUFF_SLAB.getDefaultState());
        possibleSlabs.add(Blocks.COBBLESTONE_SLAB.getDefaultState());
        possibleSlabs.add(Blocks.MOSSY_COBBLESTONE_SLAB.getDefaultState());

        int r = random.nextInt(possibleBlocks.size());

        generateRock(structureWorldAccess,random,center,possibleBlocks.get(r), possibleSlabs.get(r));

        return true;
    }
}