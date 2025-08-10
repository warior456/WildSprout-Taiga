package net.ugi.wildsprout_taiga.world.gen.feature.spruce_trees;

import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.ugi.wildsprout_taiga.tags.ModTags;

import java.util.LinkedHashSet;
import java.util.Set;

public class BigSpruce2 {

    private static BlockPos[] checkDuplicate(BlockPos[] blockPosArray){
        Set<BlockPos> blockSet = new LinkedHashSet<>();
        for (BlockPos pos : blockPosArray) {
            if (pos != null) {
                blockSet.add(pos);
            }
        }

        return blockSet.toArray(new BlockPos[0]);
    }

    public static Direction.Axis getAxisFromVector(Vec3d vec) {
        double absX = Math.abs(vec.x);
        double absY = Math.abs(vec.y);
        double absZ = Math.abs(vec.z);

        if (absX >= absY && absX >= absZ) {
            return Direction.Axis.X;
        } else if (absY >= absX && absY >= absZ) {
            return Direction.Axis.Y;
        } else {
            return Direction.Axis.Z;
        }
    }



    private static void leaves(StructureWorldAccess world, Random random, BlockPos pos, double radius) {
        for (BlockPos pos2 : BlockPos.iterate(pos.add(-(int)Math.round(radius), -(int)Math.round(radius), -(int)Math.round(radius)), pos.add((int)Math.round(radius), (int)Math.round(radius), (int)Math.round(radius)))) {
            if (random.nextDouble() > 0.7) continue;
            double distance = pos.getSquaredDistance(pos2);
            if (distance <= radius * radius) {
                if (!world.getBlockState(pos2).isIn(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID)) continue;
                world.setBlockState(pos2, Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE,1), 3);
            }
        }
    }

    private static void branch(StructureWorldAccess world, Random random, BlockPos pos, int size, int deg) {
        Vec3d direction = Vec3d.fromPolar(0, deg).multiply(size);//.add(0,random.nextBetween(-1,2),0);

        BlockPos[] blockPosArray = new BlockPos[size*5];
        for (int i = 0; i < size*5; i++) {
            blockPosArray [i] = pos.add((int) Math.round(direction.getX() * i / (size * 5)+0.5), (int) Math.round(direction.getY() * i / (size * 5)), (int) Math.round(direction.getZ() * i / (size * 5)+0.5));
        }

        blockPosArray = checkDuplicate(blockPosArray);
        int arraySize = blockPosArray.length;
        int posInArray = 0;
        for (BlockPos branchPos : blockPosArray) {
            leaves(world, random, branchPos, 1);
            if (posInArray+3 > arraySize){
                world.setBlockState(branchPos, Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE,1), 3);
            }else {
                world.setBlockState(branchPos, Blocks.STRIPPED_SPRUCE_LOG.getDefaultState().with(PillarBlock.AXIS, getAxisFromVector(direction)), 3);
            }
            posInArray++;

        }

    }



    public static boolean generate(FeatureContext<DefaultFeatureConfig> context, BlockPos center){
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();


        int logheight = 20 + random.nextInt(8);
        int height = logheight + 3 + random.nextInt(3);
        int branchStartHeight = 3 + random.nextInt(3);
        int branchSizeStart = 5 + random.nextInt(2);

        for (int i = 0; i < height; i++) {
            BlockPos pos = new BlockPos(center.getX(),center.getY() + i,center.getZ());

            if (i > height - 2 - random.nextInt(3)) {
                structureWorldAccess.setBlockState(pos, Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE,1), 3);
                structureWorldAccess.setBlockState(pos.add(1,0,0), Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE,1), 3);
                structureWorldAccess.setBlockState(pos.add(1,0,1), Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE,1), 3);
                structureWorldAccess.setBlockState(pos.add(0,0,1), Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE,1), 3);
                continue;
            }

            if (i >= branchStartHeight) {
                if ((i) % 2 == 0 || i >= logheight){
                    double branchSize = branchSizeStart - (double)(i-branchStartHeight)* (double)(branchSizeStart-2)/(logheight-branchStartHeight);
                    if (branchSize < 2){
                        int deg = random.nextInt(360);
                        branch(structureWorldAccess, random, pos, (int) Math.round(branchSize), deg);
                        continue;
                    }
                    int amountOfBranches = (int)Math.round(branchSize/1.5)+1;
                    int randomDegOffset = random.nextInt(360);
                    for (int k = 0; k < amountOfBranches; k++) {
                        int deg = randomDegOffset + k * 360/ amountOfBranches + random.nextInt(120/amountOfBranches);
                        branch(structureWorldAccess, random, pos, (int) Math.round(branchSize), deg);
                    }
                }
            }




            if (i < logheight) {
                structureWorldAccess.setBlockState(pos, Blocks.SPRUCE_LOG.getDefaultState(), 3);
                structureWorldAccess.setBlockState(pos.add(1,0,0), Blocks.SPRUCE_LOG.getDefaultState(), 3);
                structureWorldAccess.setBlockState(pos.add(1,0,1), Blocks.SPRUCE_LOG.getDefaultState(), 3);
                structureWorldAccess.setBlockState(pos.add(0,0,1), Blocks.SPRUCE_LOG.getDefaultState(), 3);
            }

        }






        return true;
    }



}
