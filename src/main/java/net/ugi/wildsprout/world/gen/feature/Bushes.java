package net.ugi.wildsprout.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.*;
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

import java.util.ArrayList;
import java.util.List;

public class Bushes extends Feature<DefaultFeatureConfig> {

    public Bushes(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    static List<Double> radiusOptions = List.of(0.9D, 1.2D, 1.3D);
    public void generateBush(StructureWorldAccess structureWorldAccess, Random random, BlockPos pos, DoublePerlinNoiseSampler noise){
        if (!(structureWorldAccess.getBlockState(pos.down()).isIn(ModTags.Blocks.VALID_PLAINS_GENERATE_BLOCK))) return;

        int bushSelector = random.nextBetween(0, radiusOptions.size() - 1);
        if(bushSelector ==0){
            setBlockState(structureWorldAccess, pos, Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
        }else{
            setBlockState(structureWorldAccess, pos, Blocks.OAK_LOG.getDefaultState());
        }
        double radius = radiusOptions.get(bushSelector);


        // Iterate a cube around the center
        for (BlockPos pos2 : BlockPos.iterate(pos.add(-(int)Math.round(radius*2), -(int)Math.round(radius*2), -(int)Math.round(radius*2)), pos.add((int)Math.round(radius*2), (int)Math.round(radius*2), (int)Math.round(radius*2)))) {
            double distance = pos.getSquaredDistance(pos2);

            // Carve a rough sphere
            if (distance <= radius * radius + noise.sample(pos2.getX(), pos2.getY(), pos2.getZ())*0.75+0.75) {
                if (structureWorldAccess.getBlockState(pos2).isIn(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID)){
                    BlockState block = Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true);
                    structureWorldAccess.setBlockState(pos2, block,2);
                }
            }
        }
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos center = context.getOrigin();
        Random random = context.getRandom();

        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()));
        DoublePerlinNoiseSampler noise = DoublePerlinNoiseSampler.create(chunkRandom, -2, new double[]{2});

        List<BlockPos> bushPlacements = getBushPlacements(random, structureWorldAccess, center);

        for (BlockPos pos : bushPlacements) {
            generateBush(structureWorldAccess, random, pos, noise);


        }

        return true;
    };

    private List<BlockPos> getBushPlacements(Random rand, StructureWorldAccess structureWorldAccess, BlockPos pos) {

        List<BlockPos> placements = new ArrayList<BlockPos>();
        for (int i = 0; i < rand.nextBetween(3, 7); i++) {
            int x = pos.getX() + rand.nextBetween(-10, 10);
            int z = pos.getZ() + rand.nextBetween(-10, 10);
            int y = pos.getY() + rand.nextBetween(-10, 10);
            y = structureWorldAccess.getChunk(new BlockPos(x,y,z)).getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get((32+x%16)%16, (32+z%16)%16);
            placements.add(new BlockPos(x,y,z));
        }

        return placements;
    }
}
