package net.ugi.wildsprout.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
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
import net.ugi.wildsprout.tags.ModTags;

public class DirtPatch extends Feature<DefaultFeatureConfig> {

    public DirtPatch(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();
        int featureSize = 5;

        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()+1));
        DoublePerlinNoiseSampler dirtPatchesNoise = DoublePerlinNoiseSampler.create(chunkRandom, -5, new double[]{1});

        int x = blockPos.getX();
        int y = blockPos.getY() - 1;
        int z = blockPos.getZ();

        for (int i = -(featureSize/2);i< (featureSize+1)/2;i++){
            for (int k = -(featureSize/2);k< (featureSize+1)/2;k++){
                int j = structureWorldAccess.getChunk(new BlockPos(x + i,y,z + k)).getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get((32+i+x%16)%16, (32+k+z%16)%16);
                BlockPos pos = new BlockPos(x+i,j -1,z+k);

                if (!(structureWorldAccess.getBlockState(pos).isIn(ModTags.Blocks.CAN_BE_REPLACED_SOLID))) continue;

                double noiseSample = dirtPatchesNoise.sample(pos.getX(), pos.getY(), pos.getZ());
                if (random.nextDouble() < noiseSample*10 -5){
                    this.setBlockState(structureWorldAccess, pos, Blocks.COARSE_DIRT.getDefaultState());
                }
            }
        }





        return true;

    }
}