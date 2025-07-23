package net.ugi.wildsprout.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.ugi.wildsprout.world.gen.ModFeatures;

import java.util.Optional;

public class SmallRiver extends Feature<DefaultFeatureConfig> {

    public static final ConfiguredFeature<?, ?> SMALL_RIVER_CONFIGURED = new ConfiguredFeature<>(
            ModFeatures.SMALL_RIVER,
            new DefaultFeatureConfig()
    );
    public static boolean generateAt(StructureWorldAccess world, ChunkGenerator chunkGenerator, BlockPos pos) {
        SmallRiver feature = new SmallRiver(Codec.unit(DefaultFeatureConfig.INSTANCE));
        FeatureContext<DefaultFeatureConfig> context = new FeatureContext<>(
                Optional.ofNullable(SMALL_RIVER_CONFIGURED),
                world,
                chunkGenerator,
                world.getRandom(),
                pos,
                new DefaultFeatureConfig()
        );
        return feature.generate(context);
    }

    public SmallRiver(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos startPos = context.getOrigin();
        Random random = context.getRandom();

        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()));
        DoublePerlinNoiseSampler noise = DoublePerlinNoiseSampler.create(chunkRandom, -2, new double[]{2});

        int height = structureWorldAccess.getChunk(new BlockPos(startPos.getX(),startPos.getY(),startPos.getZ())).getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get((32+startPos.getX()%16)%16, (32+startPos.getZ()%16)%16);
        startPos = new BlockPos(startPos.getX(),height,startPos.getZ());

        if (!(structureWorldAccess.getBlockState(startPos.down()).equals(Blocks.GRASS_BLOCK.getDefaultState()))) return false;

        ServerWorld serverWorld = ((ServerWorldAccess)structureWorldAccess).toServerWorld();

        for (int i = 0; i <= 30; i++) {
            this.setBlockState(structureWorldAccess, startPos.up(20+i), Blocks.GOLD_BLOCK.getDefaultState());

        }

//        for (int a = 0; a< 30; a++){
//        int x = startPos.getX();
//        int y = startPos.getY();
//        int z = startPos.getZ();
//
//        this.setBlockState(structureWorldAccess, startPos, Blocks.DIAMOND_BLOCK.getDefaultState());
//            try {
//                this.setBlockState(serverWorld, new BlockPos(x+3200,y,z+3200), Blocks.DIAMOND_BLOCK.getDefaultState());
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        boolean[][] heightmap = new boolean[15][15];
//        for (int i = 0; i < 15; i++) {
//            for (int k = 0; k < 15; k++) {
//                heightmap[i][k] = structureWorldAccess.getChunk(new BlockPos(x+i-7,y,z+k-7)).getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get((32+i-7+x%16)%16, (32+k-7+z%16)%16) < height;
//            }
//        }
//
//        double[][] distance = new double[15][15];
//        for (int i = 0; i < 15; i++) {
//            for (int k = 0; k < 15; k++) {
//                if (!heightmap[i][k]){
//                    distance[i][k] = 15;
//                    continue;
//                }
//
//                distance[i][k] = Math.sqrt((i-7)*(i-7)+(k-7)*(k-7));
//
//            }
//        }
//
//        double shortestDistance = 15;
//        BlockPos newPos = new BlockPos(startPos.getX(),startPos.getY(),startPos.getZ());
//        for (int i = 0; i < 15; i++) {
//            for (int k = 0; k < 15; k++) {
//                if (distance[i][k] < shortestDistance) {
//                    shortestDistance = distance[i][k];
//                    newPos = new BlockPos(startPos.getX()+i-7,startPos.getY(),startPos.getZ()+k-7);
//
//                }
//            }
//        }
//
//        height = structureWorldAccess.getChunk(newPos).getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get((32+newPos.getX()%16)%16, (32+newPos.getZ()%16)%16);
//        newPos = new BlockPos(newPos.getX(),height,newPos.getZ());
//
//        this.setBlockState(structureWorldAccess, newPos, Blocks.GOLD_BLOCK.getDefaultState());
//
//        startPos = newPos;
//
//
//    }
        //structureWorldAccess.toServerWorld().setChunkForced((startPos.getX()+48)/16, (startPos.getZ()+48)/16,true);
        //WorldChunk chunk = structureWorldAccess.getChunkManager().getWorldChunk((startPos.getX()+48)/16, (startPos.getZ()+48)/16);
        //chunk.setBlockState(startPos.add(48,48,48), Blocks.EMERALD_BLOCK.getDefaultState(), false);
        //generateAt(structureWorldAccess,context.getGenerator(),startPos.east(16));
        return true;
    };
}
