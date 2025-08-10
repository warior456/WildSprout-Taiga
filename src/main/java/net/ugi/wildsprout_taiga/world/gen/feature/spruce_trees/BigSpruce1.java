package net.ugi.wildsprout_taiga.world.gen.feature.spruce_trees;

import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.ugi.wildsprout_taiga.tags.ModTags;

public class BigSpruce1 {

    public static boolean generate(FeatureContext<DefaultFeatureConfig> context, BlockPos center){
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();

        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()+1));
        DoublePerlinNoiseSampler leaveNoise = DoublePerlinNoiseSampler.create(chunkRandom, -4, new double[]{1});


        int height = 20 + random.nextInt(10);
        int logheight = height - 2 - random.nextInt(2);
        int leavesStartHeight = 4 + random.nextInt(3);
        double leavesRadiusStart = (double) logheight /5 + 1 ;//+ random.nextInt(2);
        double leavesRadius = leavesRadiusStart;

        for (int i = 0; i < height+2; i++) {
            BlockPos pos = new BlockPos(center.getX(),center.getY() + i,center.getZ());
            if (i >= leavesStartHeight) {
                leavesRadius = leavesRadiusStart - (double)(i-leavesStartHeight)* (double)(leavesRadiusStart-1)/(logheight-leavesStartHeight+1)+1;
                if (i == leavesStartHeight) {
                    leavesRadius = Math.clamp(leavesRadius-2,2,100);

                    for (BlockPos pos2 : BlockPos.iterate(pos.add(-(int)Math.round(leavesRadius*2), 0, -(int)Math.round(leavesRadius*2)), pos.add((int)Math.round(leavesRadius*2), 0, (int)Math.round(leavesRadius*2)))) {
                        double distance = pos.getSquaredDistance(pos2);
                        if (distance <= leavesRadius * leavesRadius) {
                            if (!structureWorldAccess.getBlockState(pos2).isIn(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID)) continue;

                            double r = random.nextDouble();
                            if (r > 0.95) continue;
                            structureWorldAccess.setBlockState(pos2, Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE,1), 3);
                        }
                    }
                }
                else {
                    for (BlockPos pos2 : BlockPos.iterate(pos.add(-(int)Math.round(leavesRadius*2), 0, -(int)Math.round(leavesRadius*2)), pos.add((int)Math.round(leavesRadius*2), 0, (int)Math.round(leavesRadius*2)))) {
                        double distance = (pos2.getX()-0.5 - pos.getX())*(pos2.getX()-0.5 - pos.getX()) + (pos2.getY() - pos.getY())*(pos2.getY() - pos.getY()) + (pos2.getZ()-0.5 - pos.getZ())*(pos2.getZ()-0.5 - pos.getZ());

                        if (distance <= leavesRadius * leavesRadius + leaveNoise.sample(pos2.getX(), pos2.getY(), pos2.getZ())* leavesRadius) {
                            if (!structureWorldAccess.getBlockState(pos2).isIn(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID)) continue;

                            double r = random.nextDouble();
                            if (r > 0.9) continue;
                            structureWorldAccess.setBlockState(pos2, Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE,1), 3);
                        }
                    }
                }
            }
            if(i <= logheight) {
                structureWorldAccess.setBlockState(pos, Blocks.SPRUCE_LOG.getDefaultState(), 3);
                structureWorldAccess.setBlockState(pos.add(1,0,0), Blocks.SPRUCE_LOG.getDefaultState(), 3);
                structureWorldAccess.setBlockState(pos.add(1,0,1), Blocks.SPRUCE_LOG.getDefaultState(), 3);
                structureWorldAccess.setBlockState(pos.add(0,0,1), Blocks.SPRUCE_LOG.getDefaultState(), 3);
            }
            else {
                structureWorldAccess.setBlockState(pos, Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE,1), 3);
                structureWorldAccess.setBlockState(pos.add(1,0,0), Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE,1), 3);
                structureWorldAccess.setBlockState(pos.add(1,0,1), Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE,1), 3);
                structureWorldAccess.setBlockState(pos.add(0,0,1), Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE,1), 3);
            }

            if (leavesRadius < 0) break;
        }

        return false;
    }
}
