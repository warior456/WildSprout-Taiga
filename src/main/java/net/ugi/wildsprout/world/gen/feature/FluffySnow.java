package net.ugi.wildsprout.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.ugi.wildsprout.WildSproutTaiga;
import net.ugi.wildsprout.tags.ModTags;

import static net.minecraft.block.SnowBlock.LAYERS;


public class FluffySnow extends Feature<DefaultFeatureConfig> {

    public FluffySnow(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    protected boolean canPlaceAt(StructureWorldAccess world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        if (blockState.isIn(ModTags.Blocks.OVERRIDE_SNOW_LAYER_CANNOT_SURVIVE_ON) && WildSproutTaiga.CONFIG.SnowOnIceEnabled && WildSproutTaiga.CONFIG.SnowyPlainsEnabled) {
            // Allow snow to be placed on ice, packed ice, and blue ice in worldgen
            // see mixin SnowPlacementMixin (this is so that the snow doesn't break after it's placed)
            return true;
        }
        if (blockState.isIn(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON)) {
            return false;
        } else if (blockState.isIn(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON)) {
            return true;
        } else {
            return Block.isFaceFullSquare(blockState.getCollisionShape(world, pos.down()), Direction.UP) || blockState.isOf(Blocks.SNOW) && (Integer)blockState.get(LAYERS) == 8;
        }
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        Random random = context.getRandom();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();

        int featureSize = 5;

        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()));
        DoublePerlinNoiseSampler snowNoise = DoublePerlinNoiseSampler.create(chunkRandom, -4, new double[]{1});

        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();

        for (int i = -(featureSize/2);i< (featureSize+1)/2;i++){
            for (int k = -(featureSize/2);k< (featureSize+1)/2;k++){
                int j = structureWorldAccess.getChunk(new BlockPos(x + i,y,z + k)).getHeightmap(Heightmap.Type.MOTION_BLOCKING).get((32+i+x%16)%16, (32+k+z%16)%16);
                BlockPos pos = new BlockPos(x+i,j,z+k);

                if ((structureWorldAccess.getBlockState(pos).equals(Blocks.SNOW.getDefaultState()) || structureWorldAccess.getBlockState(pos).equals(Blocks.AIR.getDefaultState()))){
                    if (canPlaceAt(structureWorldAccess,pos)){

                        int snowlayers = (int)Math.round(Math.clamp(snowNoise.sample(pos.getX(), pos.getY(), pos.getZ())+0.5,0,2)*3) + random.nextInt(2)+1;
                        this.setBlockState(structureWorldAccess, pos, Blocks.SNOW.getDefaultState().with(LAYERS, snowlayers));
                    }
                }

                if (structureWorldAccess.getBlockState(pos.down()).isIn(BlockTags.LEAVES)){
                    int j2 = structureWorldAccess.getChunk(new BlockPos(x + i,y,z + k)).getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get((32+i+x%16)%16, (32+k+z%16)%16);
                    int difference = j - j2;
                    for (int j3 = 0; j3 <= difference; j3++) {

                        // Place snow layers below the leaves
                        BlockPos posBelow = new BlockPos(x + i, j - j3, z + k);
                        if ((structureWorldAccess.getBlockState(posBelow).equals(Blocks.SNOW.getDefaultState()) || structureWorldAccess.getBlockState(posBelow).equals(Blocks.AIR.getDefaultState()))){
                            if (canPlaceAt(structureWorldAccess,posBelow)){

                                int snowlayers = (int)Math.round(Math.clamp(snowNoise.sample(posBelow.getX(), posBelow.getY(), posBelow.getZ())+0.5,0,2)*3) + random.nextInt(2)+1;
                                this.setBlockState(structureWorldAccess, posBelow, Blocks.SNOW.getDefaultState().with(LAYERS, snowlayers));
                            }
                        }
                    }

                }

            }
        }

        return true;

    }
}
