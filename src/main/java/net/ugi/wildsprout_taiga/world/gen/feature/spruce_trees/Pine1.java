package net.ugi.wildsprout_taiga.world.gen.feature.spruce_trees;

import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.ugi.wildsprout_taiga.tags.ModTags;

public class Pine1 {

    public static boolean generate(FeatureContext<DefaultFeatureConfig> context, BlockPos center){
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();


        int height = 10 + random.nextInt(7);
        int logheight = height - 2 - random.nextInt(2);
        int leavesStartHeight = height/2 + random.nextInt(2);
        int leavesRadiusMax = (height)/5;// 4 + random.nextInt(2);
        double leavesRadius = leavesRadiusMax;

        int leaveMiddle = (height + leavesStartHeight)/2 +2;
        int halfLeaveHeight = (height - leavesStartHeight)/2 + 5;

//        System.out.println("height: " + height);
//        System.out.println("logheight: " + logheight);
//        System.out.println("leavesStartHeight: " + leavesStartHeight);
//        System.out.println("leavesRadiusMax: " + leavesRadiusMax);
//        System.out.printf("leaveMiddle: " + leaveMiddle);
//        System.out.println("halfLeaveHeight: " + halfLeaveHeight);



        for (int i = 0; i < height*1.35; i++) {
            BlockPos pos = new BlockPos(center.getX(),center.getY() + i,center.getZ());

            if (i > logheight){
                structureWorldAccess.setBlockState(pos, Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE,1), 3);
            }

            if (i >= leavesStartHeight-1) {

                leavesRadius = Math.pow((leavesRadiusMax+1) * (1- (double) (i - leaveMiddle) /halfLeaveHeight) * Math.cos(Math.asin((double) (i - leaveMiddle) /halfLeaveHeight)), 2.0/3);
//                System.out.println("i:" + i + " , radius:" + leavesRadius);
                leavesRadius = Math.round(leavesRadius);
                if (i%2 == 0){
                    leavesRadius = leavesRadius/2;
                }


                if(i == leavesStartHeight -1)
                    leavesRadius = 1;

                for (BlockPos pos2 : BlockPos.iterate(pos.add(-(int)Math.round(leavesRadius), 0, -(int)Math.round(leavesRadius)), pos.add((int)Math.round(leavesRadius), 0, (int)Math.round(leavesRadius)))) {
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
