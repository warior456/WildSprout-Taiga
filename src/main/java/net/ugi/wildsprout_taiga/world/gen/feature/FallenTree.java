package net.ugi.wildsprout_taiga.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.ugi.wildsprout_taiga.tags.ModTags;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class FallenTree extends Feature<DefaultFeatureConfig> {

    public FallenTree(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }
    private int steps;
    private Vec3d direction;

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        this.steps = 50;

        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();
        int featureSize = 7;




        BlockPos startPos = blockPos.add(random.nextBetween(-featureSize, featureSize), 0, random.nextBetween(-featureSize, featureSize));
        startPos =  new BlockPos(startPos.getX(), structureWorldAccess.getTopY(Heightmap.Type.WORLD_SURFACE_WG, startPos.getX(), startPos.getZ()), startPos.getZ());

        BlockPos endPos = blockPos.add(random.nextBetween(-featureSize, featureSize), 0, random.nextBetween(-featureSize, featureSize));
        endPos =  new BlockPos(endPos.getX(), structureWorldAccess.getTopY(Heightmap.Type.WORLD_SURFACE_WG, endPos.getX(), endPos.getZ()), endPos.getZ());



        direction = new Vec3d(
                endPos.getX() - startPos.getX(),
                endPos.getY() - startPos.getY(),
                endPos.getZ() - startPos.getZ()
        );
        if(direction.getY() > 4 || direction.length()<3|| Math.abs(Math.abs(direction.getX()) - Math.abs(direction.getZ())) < 2){
            return false;
        }
        if(!structureWorldAccess.getBlockState(startPos.down()).isIn(ModTags.Blocks.VALID_TAIGA_GENERATE_BLOCK) || !structureWorldAccess.getBlockState(endPos.down()).isIn(ModTags.Blocks.VALID_TAIGA_GENERATE_BLOCK)){
            return false;
        }


        Direction.Axis axis = getAxisFromVector(direction);
        BlockPos[] BlockPosArray = getBlockposArray(startPos);
        List<BlockPos> placeList = new ArrayList<>();
        for (BlockPos pos : BlockPosArray) {
            if (structureWorldAccess.getBlockState(pos).isIn(ModTags.Blocks.CAN_BE_REPLACED_ALL)) {
                placeList.add(pos);
            } else {
                return false;
            }
        }

        for (BlockPos pos : placeList){
            if (structureWorldAccess.getBlockState(pos).isIn(ModTags.Blocks.CAN_BE_REPLACED_ALL)) {
                structureWorldAccess.setBlockState(pos, Blocks.OAK_LOG.getDefaultState().with(PillarBlock.AXIS, axis), 2);

                //top decoration
                if(structureWorldAccess.getBlockState(pos.up()).isIn(ModTags.Blocks.CAN_BE_REPLACED_ALL)) {

                    if (random.nextDouble() < 1) {//decorate with mushrooms //todo decorate around with mushrooms
                        if (random.nextBoolean()) {
                            structureWorldAccess.setBlockState(pos.up(), Blocks.BROWN_MUSHROOM.getDefaultState(), 2);//somehow doesn't work with high light levels
                        } else {
                            structureWorldAccess.setBlockState(pos.up(), Blocks.RED_MUSHROOM.getDefaultState(), 2);
                        }
                    }

/*                    if(random.nextDouble()<0.65) {//decorate with moss
                        if(!(structureWorldAccess.getBlockState(pos.up()).getBlock() == Blocks.BROWN_MUSHROOM || structureWorldAccess.getBlockState(pos.up()).getBlock() == Blocks.RED_MUSHROOM)){
                            continue;
                        }
                        structureWorldAccess.setBlockState(pos.up(), Blocks.MOSS_CARPET.getDefaultState(), 2);
                        if (random.nextDouble() < 0.2) {
                            structureWorldAccess.setBlockState(pos, Blocks.MOSS_BLOCK.getDefaultState(), 2);
                        }
                    }*/



                }
            }
        }
        return true;

    }

    public  BlockPos[] getBlockposArray(BlockPos startPos) {
        BlockPos[] blockPosArray = new BlockPos[this.steps];

        for(int i = 0 ; i < this.steps; i++) {
            double t = i/(double)(this.steps-1);// -1 so tah the last one is t = 1
            Vec3d vec = getDirectionPos(t, startPos);
            int x = vec.x < 0 ? (int)(vec.x) - 1 : (int)(vec.x);
            int y = vec.y < 0 ? (int)(vec.y) - 1 : (int)(vec.y) ;
            int z = vec.z < 0 ? (int)(vec.z) - 1 : (int)(vec.z);
            blockPosArray[i] = new BlockPos(x,y,z);
        }
        return checkDuplicate(blockPosArray);
    }
    public Vec3d getDirectionPos(double t, BlockPos startPos){
        //go in steps for the length of the direction vector

        double x = direction.getX() * t;
        double y = direction.getY() * t;
        double z = direction.getZ() * t;

        return new Vec3d(startPos.getX() + x, startPos.getY() + y , startPos.getZ() + z);

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

    private BlockPos[] checkDuplicate(BlockPos[] blockPosArray){
        Set<BlockPos> blockSet = new LinkedHashSet<>();
        for (BlockPos pos : blockPosArray) {
            if (pos != null) {
                blockSet.add(pos);
            }
        }

        return blockSet.toArray(new BlockPos[0]);
    }
}