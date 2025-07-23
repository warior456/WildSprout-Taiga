package net.ugi.wildsprout.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class PumpkinPatch extends Feature<RandomPatchFeatureConfig> {
    public PumpkinPatch(Codec<RandomPatchFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<RandomPatchFeatureConfig> context) {
        RandomPatchFeatureConfig randomPatchFeatureConfig = context.getConfig();
        Random random = context.getRandom();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        int i = 0;
        BlockPos.Mutable mutablePos1 = new BlockPos.Mutable();
        BlockPos.Mutable mutablePos2 = new BlockPos.Mutable();
        BlockPos.Mutable mutablePos3 = new BlockPos.Mutable();
        BlockPos.Mutable mutablePos4 = new BlockPos.Mutable();
        int j = randomPatchFeatureConfig.xzSpread() + 1;
        int k = randomPatchFeatureConfig.ySpread() + 1;

        for (int l = 0; l < randomPatchFeatureConfig.tries(); l++) {
            mutablePos1.set(blockPos, random.nextInt(j) - random.nextInt(j), random.nextInt(k) - random.nextInt(k), random.nextInt(j) - random.nextInt(j));
            if (randomPatchFeatureConfig.feature().value().generateUnregistered(structureWorldAccess, context.getGenerator(), random, mutablePos1)) {
                i++;
            }
        }
        for (int l = 0; l < randomPatchFeatureConfig.tries(); l++) {
            //leaves
            for (int m = 0; m < 4; m++) {
                mutablePos2.set(blockPos, random.nextInt(j+2) - random.nextInt(j+2), random.nextInt(k+1) - random.nextInt(k+1), random.nextInt(j+2) - random.nextInt(j+2));
                if((structureWorldAccess.getBlockState(mutablePos2).getBlock() == Blocks.AIR) && (structureWorldAccess.getBlockState(mutablePos2.down()).getBlock() == Blocks.GRASS_BLOCK ||
                                                                                                    structureWorldAccess.getBlockState(mutablePos2.down()).getBlock() == Blocks.PUMPKIN) ) {
                    structureWorldAccess.setBlockState(mutablePos2, Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true), 2);
                }
            }
        }
        for (int l = 0; l < randomPatchFeatureConfig.tries(); l++) {
            for (int m = 0; m < 3; m++) {
                //ferns
                mutablePos3.set(blockPos, random.nextInt(j+2) - random.nextInt(j+2), random.nextInt(k+1) - random.nextInt(k+1), random.nextInt(j+2) - random.nextInt(j+2));
                if((structureWorldAccess.getBlockState(mutablePos3).getBlock() == Blocks.AIR) && (structureWorldAccess.getBlockState(mutablePos3.down()).getBlock() == Blocks.GRASS_BLOCK) ) {
                    structureWorldAccess.setBlockState(mutablePos3, Blocks.FERN.getDefaultState(), 2);
                }

                //big ferns
                mutablePos4.set(blockPos, random.nextInt(j+2) - random.nextInt(j+2), random.nextInt(k+1) - random.nextInt(k+1), random.nextInt(j+2) - random.nextInt(j+2));
                if((structureWorldAccess.getBlockState(mutablePos4).getBlock() == Blocks.AIR) && (structureWorldAccess.getBlockState(mutablePos4.up()).getBlock() == Blocks.AIR) && (structureWorldAccess.getBlockState(mutablePos4.down()).getBlock() == Blocks.GRASS_BLOCK) ) {
                    structureWorldAccess.setBlockState(mutablePos4,Blocks.LARGE_FERN.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.LOWER),2);
                    structureWorldAccess.setBlockState(mutablePos4.up(),Blocks.LARGE_FERN.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER),2);
                }
            }



        }

        return i > 0;
    }
}
