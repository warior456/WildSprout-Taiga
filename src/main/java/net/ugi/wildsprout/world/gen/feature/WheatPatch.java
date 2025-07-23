package net.ugi.wildsprout.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
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

public class WheatPatch extends Feature<DefaultFeatureConfig> {

    public WheatPatch(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        Random random = context.getRandom();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();

        int featureSize = 5;

        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()));
        DoublePerlinNoiseSampler wheatPatchesNoise = DoublePerlinNoiseSampler.create(chunkRandom, -4, new double[]{1});
        DoublePerlinNoiseSampler mainNoise = DoublePerlinNoiseSampler.create(chunkRandom, -9, new double[]{1,1});

        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();

        for (int i = -(featureSize/2);i< (featureSize+1)/2;i++){
            for (int k = -(featureSize/2);k< (featureSize+1)/2;k++){
                int j = structureWorldAccess.getChunk(new BlockPos(x + i,y,z + k)).getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get((32+i+x%16)%16, (32+k+z%16)%16);
                BlockPos pos = new BlockPos(x+i,j,z+k);

                if (!(structureWorldAccess.getBlockState(pos.down()).isIn(ModTags.Blocks.VALID_PLAINS_GENERATE_BLOCK))) continue;
                if (!(structureWorldAccess.getBlockState(pos).isIn(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID))) continue;

                double mainSample = mainNoise.sample(pos.getX(), pos.getY(), pos.getZ());
                if (mainSample > 0.1 || mainSample < -0.1) continue;

                double noiseSample = wheatPatchesNoise.sample(pos.getX(), pos.getY(), pos.getZ());
                if (random.nextDouble() < noiseSample - 0.22){
                    this.setBlockState(structureWorldAccess, pos, Blocks.WHEAT.getDefaultState().with(CropBlock.AGE, random.nextBetween(6,7)));
                    this.setBlockState(structureWorldAccess, pos.down(), Blocks.FARMLAND.getDefaultState());
                }
            }
        }





        return true;

    }
}
