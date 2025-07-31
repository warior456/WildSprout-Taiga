package net.ugi.wildsprout_taiga.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.ugi.wildsprout_taiga.tags.ModTags;

import java.util.*;

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

        // populate heighmaps
        ChunkPos centerchunk = structureWorldAccess.getChunk(blockPos).getPos();
        for (int i = -1; i < 2; i++){
            for(int k = -1; k < 2; k++){
                //structureWorldAccess.getChunk(centerchunk.x + i, centerchunk.z + k, ChunkStatus.FEATURES, true).getHeightmap(Heightmap.Type.WORLD_SURFACE);
                Heightmap.populateHeightmaps(structureWorldAccess.getChunk(centerchunk.x + i, centerchunk.z + k), EnumSet.of(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES));

            }
        }




        BlockPos startPos = blockPos.add(random.nextBetween(-featureSize, featureSize), 0, random.nextBetween(-featureSize, featureSize));
        startPos =  new BlockPos(startPos.getX(), structureWorldAccess.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, startPos.getX(), startPos.getZ()), startPos.getZ());

        BlockPos endPos = blockPos.add(random.nextBetween(-featureSize, featureSize), 0, random.nextBetween(-featureSize, featureSize));
        endPos =  new BlockPos(endPos.getX(), structureWorldAccess.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, endPos.getX(), endPos.getZ()), endPos.getZ());



        direction = new Vec3d(
                endPos.getX() - startPos.getX(),
                endPos.getY() - startPos.getY(),
                endPos.getZ() - startPos.getZ()
        );
        if(Math.abs(direction.getY()) > 4 || direction.length()<4|| Math.abs(Math.abs(direction.getX()) - Math.abs(direction.getZ())) < 2){
            return false;
        }
        if(!structureWorldAccess.getBlockState(startPos.down()).isIn(ModTags.Blocks.VALID_TAIGA_GENERATE_BLOCK) || !structureWorldAccess.getBlockState(endPos.down()).isIn(ModTags.Blocks.VALID_TAIGA_GENERATE_BLOCK)){
            return false;
        }


        Direction.Axis axis = getAxisFromVector(direction);
        BlockPos[] BlockPosArray = getBlockposArray(startPos);
        List<BlockPos> placeList = new ArrayList<>();
        for (BlockPos pos : BlockPosArray) {
            if (structureWorldAccess.getBlockState(pos).isIn(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID)) {
                placeList.add(pos);
            } else {
                return false;
            }
        }
        if(placeList.size()<4){
            return false;
        }

        placeList.remove(1);

        for (BlockPos pos : placeList){
            /*if (structureWorldAccess.getBlockState(pos).isIn(ModTags.Blocks.CAN_BE_REPLACED_ALL) ) {continue}*/
            structureWorldAccess.setBlockState(pos, Blocks.SPRUCE_LOG.getDefaultState().with(PillarBlock.AXIS, axis), 0);
            //top decoration
            if(structureWorldAccess.getBlockState(pos.up()).isIn(ModTags.Blocks.CAN_BE_REPLACED_ALL)) {



                if(random.nextDouble()<0.7) {//decorate with moss
                    if(structureWorldAccess.getBlockState(pos.up()).isIn(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID)){
                        structureWorldAccess.setBlockState(pos.up(), Blocks.MOSS_CARPET.getDefaultState(), 2);
                    }

                    if(structureWorldAccess.getBlockState(pos.down()).isIn(ModTags.Blocks.VALID_TAIGA_GENERATE_BLOCK)) {
                        if (random.nextDouble() < 0.15) {
                            structureWorldAccess.setBlockState(pos, Blocks.MOSS_BLOCK.getDefaultState(), 2);
                        }
                    }
                }

                if (random.nextDouble() < 0.15) {//decorate with mushrooms
                    setMushRoomOn(pos, structureWorldAccess, random);
                }


            }
            if(structureWorldAccess.getBlockState(pos.down()).isIn(ModTags.Blocks.VALID_TAIGA_GENERATE_BLOCK)) {
                if( random.nextDouble() < 0.7) {//decorate around with mushrooms
                    if (random.nextDouble() < 0.5) {
                        if (structureWorldAccess.getBlockState(pos.down().north()).isIn(ModTags.Blocks.CAN_BE_REPLACED_SOLID)) {
                            structureWorldAccess.setBlockState(pos.down().north(), Blocks.PODZOL.getDefaultState(), 2);
                            if (random.nextDouble() < 0.3) {
                                setMushRoomOn(pos.down().north(), structureWorldAccess, random);
                            }
                        }
                    }
                    if (random.nextDouble() < 0.5) {
                        if(structureWorldAccess.getBlockState(pos.down().south()).isIn(ModTags.Blocks.CAN_BE_REPLACED_SOLID)) {
                            structureWorldAccess.setBlockState(pos.down().south(), Blocks.PODZOL.getDefaultState(), 2);
                            if( random.nextDouble() < 0.3) {
                                setMushRoomOn(pos.down().south(), structureWorldAccess, random);
                            }
                        }
                    }
                    if (random.nextDouble() < 0.5) {
                        if(structureWorldAccess.getBlockState(pos.down().east()).isIn(ModTags.Blocks.CAN_BE_REPLACED_SOLID)) {
                            structureWorldAccess.setBlockState(pos.down().east(), Blocks.PODZOL.getDefaultState(), 2);
                            if( random.nextDouble() < 0.3) {
                                setMushRoomOn(pos.down().east(), structureWorldAccess, random);
                            }
                        }
                    }
                    if (random.nextDouble() < 0.5) {
                        if (structureWorldAccess.getBlockState(pos.down().west()).isIn(ModTags.Blocks.CAN_BE_REPLACED_SOLID)) {
                            structureWorldAccess.setBlockState(pos.down().west(), Blocks.PODZOL.getDefaultState(), 2);
                            if( random.nextDouble() < 0.3) {
                                setMushRoomOn(pos.down().west(), structureWorldAccess, random);
                            }
                        }
                    }

                }
            }



        }
        structureWorldAccess.setBlockState(startPos, Blocks.SPRUCE_LOG.getDefaultState(), 0);
        return true;

    }

    public void setMushRoomOn(BlockPos pos, StructureWorldAccess world, Random random) {
        if(!world.getBlockState(pos.up()).isIn(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID)) return;
        if (random.nextBoolean()) {
            world.setBlockState(pos.up(), Blocks.BROWN_MUSHROOM.getDefaultState(), 2);
        } else {
            world.setBlockState(pos.up(), Blocks.RED_MUSHROOM.getDefaultState(), 2);
        }
    }


    public  BlockPos[] getBlockposArray(BlockPos startPos) {
        BlockPos[] blockPosArray = new BlockPos[this.steps];

        for(int i = 0 ; i < this.steps; i++) {
            double t = i/(double)(this.steps-1);// -1 so tah the last one is t = 1
            Vec3d vec = getDirectionPos(t, startPos);
            int x = (int)Math.round(vec.x);
            int y = (int)Math.round(vec.y);
            int z = (int)Math.round(vec.z);
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