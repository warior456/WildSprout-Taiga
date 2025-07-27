package net.ugi.wildsprout_taiga.world.gen.feature.spruce_trees;

import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.ugi.wildsprout_taiga.tags.ModTags;

public class Spruce1 {

    public static boolean generate(FeatureContext<DefaultFeatureConfig> context){
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos center = context.getOrigin();
        Random random = context.getRandom();

        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()));
        DoublePerlinNoiseSampler noise = DoublePerlinNoiseSampler.create(chunkRandom, -2, new double[]{1});

        int j = structureWorldAccess.getChunk(new BlockPos(center.getX(),center.getY(),center.getZ())).getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get((32+center.getX()%16)%16, (32+center.getZ()%16)%16);

        center = new BlockPos(center.getX(),j,center.getZ());

//        structureWorldAccess.setBlockState(center.up(30), Blocks.DIAMOND_BLOCK.getDefaultState(),3);

        int height = 12 + random.nextInt(5);
        int logheight = height - 4 - random.nextInt(3);
        int leavesStartHeight = 2 + random.nextInt(3);
        double leavesRadiusStart = (double) logheight /5 + 1 ;//+ random.nextInt(2);
        double leavesRadius = leavesRadiusStart;

        for (int i = 0; i < height+2; i++) {
            BlockPos pos = new BlockPos(center.getX(),center.getY() + i,center.getZ());
            if (i >= leavesStartHeight) {
                for (BlockPos pos2 : BlockPos.iterate(pos.add(-(int)Math.round(leavesRadius*2), 0, -(int)Math.round(leavesRadius*2)), pos.add((int)Math.round(leavesRadius*2), 0, (int)Math.round(leavesRadius*2)))) {
                    double distance = pos.getSquaredDistance(pos2);
                    if (distance <= leavesRadius * leavesRadius + noise.sample(pos2.getX(), pos2.getY(), pos2.getZ())* Math.clamp(leavesRadius - 2, 0, 100)) {
                        if (!structureWorldAccess.getBlockState(pos2).isIn(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID)) continue;

                        double r = random.nextDouble();
                        if (r > 0.55 + (1-leavesRadius/leavesRadiusStart)*0.45) continue;
                        structureWorldAccess.setBlockState(pos2, Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE,1), 3);
                    }
                }
                leavesRadius -= leavesRadiusStart/(height-leavesStartHeight);
            }
            if(i <= logheight) structureWorldAccess.setBlockState(pos, Blocks.SPRUCE_LOG.getDefaultState(), 3);

            if (leavesRadius < 0) break;
        }

        return true;
    }



}
