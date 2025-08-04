package net.ugi.wildsprout_taiga.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.ugi.wildsprout_taiga.tags.ModTags;
import net.ugi.wildsprout_taiga.world.gen.feature.spruce_trees.Spruce1;
import net.ugi.wildsprout_taiga.world.gen.feature.spruce_trees.Spruce2;

import java.util.ArrayList;
import java.util.List;

public class Trees extends Feature<DefaultFeatureConfig> {

    public Trees(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {

        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos center = context.getOrigin();

        int j = structureWorldAccess.getChunk(new BlockPos(center.getX(),center.getY(),center.getZ())).getHeightmap(Heightmap.Type.MOTION_BLOCKING).get((32+center.getX()%16)%16, (32+center.getZ()%16)%16);

        center = new BlockPos(center.getX(),j,center.getZ());

        if (structureWorldAccess.getBlockState(center.down(2)).getBlock().equals(Blocks.MOSS_BLOCK) && structureWorldAccess.getBlockState(center.down()).getBlock().equals(Blocks.MOSS_CARPET)){
            center = center.down(1);
        }

        Random random = context.getRandom();

        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()+1));
        DoublePerlinNoiseSampler mossNoise = DoublePerlinNoiseSampler.create(chunkRandom, -5, new double[]{1});

        double noiseSample = mossNoise.sample(center.getX(), center.getY(), center.getZ());
        if ((0.5 < noiseSample*10 -2) && !(structureWorldAccess.getBiome(center) == BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA || structureWorldAccess.getBiome(center) == BiomeKeys.OLD_GROWTH_PINE_TAIGA)) return false;

        if(!structureWorldAccess.getBlockState(center.down()).isIn(ModTags.Blocks.VALID_TAIGA_GENERATE_BLOCK)) return false;


        if (random.nextBoolean()) {
            return Spruce1.generate(context, center);
        } else {
            return Spruce2.generate(context,center);
        }
    }
}

