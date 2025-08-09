package net.ugi.wildsprout_taiga.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
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

public class Moss extends Feature<DefaultFeatureConfig> {

    public Moss(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();
        int featureSize = 7;

        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()+1));
        DoublePerlinNoiseSampler mossNoise = DoublePerlinNoiseSampler.create(chunkRandom, -5, new double[]{1});

        int x = blockPos.getX();
        int y = blockPos.getY() - 1;
        int z = blockPos.getZ();

        for (int i = -(featureSize/2);i< (featureSize+1)/2;i++){
            for (int k = -(featureSize/2);k< (featureSize+1)/2;k++){
                int j = structureWorldAccess.getChunk(new BlockPos(x + i,y,z + k)).getHeightmap(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).get((32+i+x%16)%16, (32+k+z%16)%16);
                BlockPos pos = new BlockPos(x+i,j -1,z+k);

                if (!(structureWorldAccess.getBlockState(pos).isIn(ModTags.Blocks.CAN_BE_REPLACED_SOLID))) continue;

                double noiseSample = mossNoise.sample(pos.getX(), pos.getY(), pos.getZ());
                if (!(random.nextDouble() < noiseSample*10 -2)){
                    this.setBlockState(structureWorldAccess, pos, Blocks.MOSS_BLOCK.getDefaultState());

                    if (!(structureWorldAccess.getBlockState(pos.up()).isIn(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID))) continue;

                    int r = random.nextInt(200);
                    // removes buggy floating ferns
                    if (r < 81 && structureWorldAccess.getBlockState(pos.up(2)).getBlock().equals(Blocks.LARGE_FERN)){
                        this.setBlockState(structureWorldAccess, pos.up(2), Blocks.AIR.getDefaultState());
                    }

                    if (r < 50) {
                        this.setBlockState(structureWorldAccess, pos.up(), Blocks.MOSS_CARPET.getDefaultState());
                    } else if (r < 70) {
                        this.setBlockState(structureWorldAccess, pos.up(), Blocks.FERN.getDefaultState());
                    } else if (r < 72) {
                        this.setBlockState(structureWorldAccess, pos.up(), Blocks.LARGE_FERN.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.LOWER));
                        this.setBlockState(structureWorldAccess, pos.up(2), Blocks.LARGE_FERN.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                    }
                    else if (r < 80) {
                        this.setBlockState(structureWorldAccess, pos.up(), Blocks.SHORT_GRASS.getDefaultState());
                    }
                    else if (r < 81) {
                        this.setBlockState(structureWorldAccess, pos.up(), Blocks.SWEET_BERRY_BUSH.getDefaultState().with(SweetBerryBushBlock.AGE,3));
                    }

                }
            }
        }

        return true;

    }
}