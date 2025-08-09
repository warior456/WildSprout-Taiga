package net.ugi.wildsprout_taiga.world.gen.feature.spruce_trees;

import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.ugi.wildsprout_taiga.tags.ModTags;

public class Spruce3 {

    public static boolean generate(FeatureContext<DefaultFeatureConfig> context, BlockPos center){
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();

        structureWorldAccess.setBlockState(center, Blocks.DIAMOND_BLOCK.getDefaultState(), 2);



        int logheight = 10 + random.nextInt(4);
        int height = logheight + 3 + random.nextInt(3);
        int leavesStartHeight = 2 + random.nextInt(2);
        int leavesRadiusStart = (height-leavesStartHeight)/3;// 4 + random.nextInt(2);
       double leavesRadius = leavesRadiusStart;



        for (int i = 0; i < height; i++) {
            BlockPos pos = new BlockPos(center.getX(),center.getY() + i,center.getZ());

            if (i > logheight){
                structureWorldAccess.setBlockState(pos, Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE,1), 3);
                continue;
            }

            if (i >= leavesStartHeight) {

                leavesRadius = leavesRadiusStart - (double)(i-leavesStartHeight)* (double)(leavesRadiusStart-1)/(height-leavesStartHeight+1)-1;
                if (i%2 == 0 || (i%2 == 1 && i == leavesStartHeight)){
                    leavesRadius = leavesRadius/2;
                }
                leavesRadius = (int)Math.ceil(leavesRadius);
                for (BlockPos pos2 : BlockPos.iterate(pos.add(-(int)Math.round(leavesRadius*2), 0, -(int)Math.round(leavesRadius*2)), pos.add((int)Math.round(leavesRadius*2), 0, (int)Math.round(leavesRadius*2)))) {
                    double distance = pos.getSquaredDistance(pos2);
                    if (distance <= leavesRadius * leavesRadius /*+ leaveNoise.sample(pos2.getX(), pos2.getY(), pos2.getZ())* leavesRadius*/) {
                        if (!structureWorldAccess.getBlockState(pos2).isIn(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID)) continue;

                        double r = random.nextDouble();
                        if (r > 0.9) continue;
                        structureWorldAccess.setBlockState(pos2, Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE,1), 3);
                    }
                }


            }

            if (i <= logheight){
                structureWorldAccess.setBlockState(pos, Blocks.SPRUCE_LOG.getDefaultState(), 3);
            }
        }

    
        return true;
    }
}
