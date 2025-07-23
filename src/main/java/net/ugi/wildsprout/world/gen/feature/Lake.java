package net.ugi.wildsprout.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SmallDripleafBlock;
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
import net.ugi.wildsprout.tags.ModTags;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.state.property.Properties.WATERLOGGED;

public class Lake extends Feature<DefaultFeatureConfig> {

    public Lake(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    public void generateMossyRock(StructureWorldAccess structureWorldAccess, Random random, BlockPos pos) {

        pos = getPosInWater(structureWorldAccess, pos);

        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()));
        DoublePerlinNoiseSampler noise = DoublePerlinNoiseSampler.create(chunkRandom, -2, new double[]{1});

        double radius = 1 + random.nextDouble()*0.5;



        // Iterate a cube around the pos
        for (BlockPos pos2 : BlockPos.iterate(pos.add(-(int)Math.round(radius*2), -(int)Math.round(radius*2), -(int)Math.round(radius*2)), pos.add((int)Math.round(radius*2), (int)Math.round(radius*2), (int)Math.round(radius*2)))) {
            double distance = pos.getSquaredDistance(pos2);

            // Carve a rough sphere
            if (distance <= radius * radius + noise.sample(pos2.getX(), pos2.getY(), pos2.getZ())) {
                BlockState block = Blocks.MOSSY_COBBLESTONE.getDefaultState();
                structureWorldAccess.setBlockState(pos2, block,2);
            }
        }

        for (BlockPos pos2 : BlockPos.iterate(pos.add(-(int)Math.round(radius*2), -(int)Math.round(radius*2), -(int)Math.round(radius*2)), pos.add((int)Math.round(radius*2), (int)Math.round(radius*2), (int)Math.round(radius*2)))) {
            double distance = pos.getSquaredDistance(pos2);

            if (distance <= radius * radius + noise.sample(pos2.getX(), pos2.getY(), pos2.getZ())+0.7) {
                if ((structureWorldAccess.getBlockState(pos2).equals(Blocks.WATER.getDefaultState()) || structureWorldAccess.getBlockState(pos2).isIn(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID)) &&
                        (structureWorldAccess.getBlockState(pos2.down()).isIn(ModTags.Blocks.VALID_ROCK_GENERATE_SLAB_ON)) &&
                        (structureWorldAccess.getBlockState(pos2.up()).equals(Blocks.WATER.getDefaultState()) || structureWorldAccess.getBlockState(pos2.up()).isIn(ModTags.Blocks.CAN_BE_REPLACED_NON_SOLID))){
                    BlockState block = Blocks.MOSSY_COBBLESTONE_SLAB.getDefaultState().with(WATERLOGGED, structureWorldAccess.getBlockState(pos2).equals(Blocks.WATER.getDefaultState()));
                    structureWorldAccess.setBlockState(pos2, block,2);
                }
            }
        }
    }

    public void generateVegetationAroundLake(StructureWorldAccess structureWorldAccess, Random random, BlockPos pos) {
        int r = random.nextInt(17);
        if (r >= 0 && r <3) {
            if (!structureWorldAccess.getBlockState(pos.down().north()).equals(Blocks.WATER.getDefaultState()) &&
                    !structureWorldAccess.getBlockState(pos.down().east()).equals(Blocks.WATER.getDefaultState()) &&
                    !structureWorldAccess.getBlockState(pos.down().south()).equals(Blocks.WATER.getDefaultState()) &&
                    !structureWorldAccess.getBlockState(pos.down().west()).equals(Blocks.WATER.getDefaultState())
            ) return;
            int h = random.nextInt(3) + 1;
            for (int i = 0; i < h; i++) {
                this.setBlockState(structureWorldAccess,pos.up(i),Blocks.SUGAR_CANE.getDefaultState());
            }
        }
        if (r == 3){
            this.setBlockState(structureWorldAccess,pos,Blocks.SHORT_GRASS.getDefaultState());
        }

        if (r == 4){
            this.setBlockState(structureWorldAccess,pos,Blocks.MOSS_CARPET.getDefaultState());
        }

        if (r == 5){
            this.setBlockState(structureWorldAccess,pos,Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
        }

        if (r == 6){
            this.setBlockState(structureWorldAccess,pos,Blocks.FLOWERING_AZALEA_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
        }
        if (r == 7){
            this.setBlockState(structureWorldAccess,pos,Blocks.FERN.getDefaultState());
        }

    }

    public BlockPos getPosInWater(StructureWorldAccess structureWorldAccess, BlockPos pos){
        while(structureWorldAccess.getBlockState(pos.down()).equals(Blocks.WATER.getDefaultState())){
            pos = pos.down();
        };
        return pos;
    }

    public void generateVegetationInLake(StructureWorldAccess structureWorldAccess, Random random, BlockPos pos) {
        if (structureWorldAccess.getBlockState(pos.down()).equals(Blocks.MOSSY_COBBLESTONE)) return;

        int r = random.nextInt(40);
        if (r == 0) {
            pos = this.getPosInWater(structureWorldAccess, pos);
            if (!structureWorldAccess.getBlockState(pos.down()).equals(Blocks.MUD.getDefaultState())) return;
            this.setBlockState(structureWorldAccess,pos,Blocks.MOSSY_COBBLESTONE.getDefaultState());
        }

        if (r == 1){
            pos = this.getPosInWater(structureWorldAccess, pos);
            if (!structureWorldAccess.getBlockState(pos.down()).equals(Blocks.MUD.getDefaultState())) return;
            this.setBlockState(structureWorldAccess,pos,Blocks.SMALL_DRIPLEAF.getDefaultState().with(SmallDripleafBlock.HALF, DoubleBlockHalf.LOWER).with(WATERLOGGED, structureWorldAccess.getBlockState(pos).equals(Blocks.WATER.getDefaultState())));
            this.setBlockState(structureWorldAccess,pos.up(),Blocks.SMALL_DRIPLEAF.getDefaultState().with(SmallDripleafBlock.HALF, DoubleBlockHalf.UPPER).with(WATERLOGGED, structureWorldAccess.getBlockState(pos.up()).equals(Blocks.WATER.getDefaultState())));
        }

        if (r == 2){
            pos = this.getPosInWater(structureWorldAccess, pos);
            if (!structureWorldAccess.getBlockState(pos.down()).equals(Blocks.MUD.getDefaultState())) return;
            int h = random.nextInt(3);
            for (int i = 0; i < h; i++) {
                this.setBlockState(structureWorldAccess,pos.up(i),Blocks.BIG_DRIPLEAF_STEM.getDefaultState().with(WATERLOGGED, structureWorldAccess.getBlockState(pos.up(i)).equals(Blocks.WATER.getDefaultState())));
            }
            this.setBlockState(structureWorldAccess,pos.up(h),Blocks.BIG_DRIPLEAF.getDefaultState().with(WATERLOGGED, structureWorldAccess.getBlockState(pos.up(h)).equals(Blocks.WATER.getDefaultState())));
        }
        if (r >= 3 && r < 5){
            this.setBlockState(structureWorldAccess,pos,Blocks.LILY_PAD.getDefaultState());
        }

    }

    public List<BlockPos> getLayer(int radius, BlockPos center, DoublePerlinNoiseSampler noise){
        List<BlockPos> layer  = new ArrayList<>();
        for (BlockPos pos : BlockPos.iterate(center.add(-(int)Math.round(radius*1.5), 0, -(int)Math.round(radius*1.5)), center.add((int)Math.round(radius*1.5), 0, (int)Math.round(radius*1.5)))) {
            double distance = center.getSquaredDistance(pos);
            // Carve a rough sphere
            if (distance <= radius * radius + noise.sample(pos.getX(), pos.getY(), pos.getZ())*radius*14) {
                layer.add(new BlockPos(pos.getX(), pos.getY(), pos.getZ())); // to fix mutable blockpos
            }
        }
        return layer;
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        Random random = context.getRandom();
        BlockPos center = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();

        int j = structureWorldAccess.getChunk(new BlockPos(center.getX(),center.getY(),center.getZ())).getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get((32+center.getX()%16)%16, (32+center.getZ()%16)%16);

        center = new BlockPos(center.getX(),j,center.getZ());

        if (!(structureWorldAccess.getBlockState(center.down()).isIn(ModTags.Blocks.VALID_PLAINS_GENERATE_BLOCK))) return false;


        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()));
        DoublePerlinNoiseSampler noise = DoublePerlinNoiseSampler.create(chunkRandom, -3, new double[]{1});

        int waterradius = 6 + random.nextInt(4);
        int dirtradius = waterradius + 3;
        int airradius = dirtradius + 1;

        List<BlockPos> firstAirLayer = getLayer(airradius, center, noise); //1 layer above water

        //check if area is flat enough
        //make heihgtmap of the area
        int[] heightmap = new int[firstAirLayer.size()];
        final int[] i = {0};
        firstAirLayer.forEach((pos) -> {
            heightmap[i[0]] = structureWorldAccess.getChunk(new BlockPos(pos.getX(),pos.getY(),pos.getZ())).getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get((32+pos.getX()%16)%16, (32+pos.getZ()%16)%16);
            if (structureWorldAccess.getBlockState(new BlockPos(pos.getX(),heightmap[i[0]]-1,pos.getZ())).equals(Blocks.WATER.getDefaultState()))  heightmap[i[0]] = -65;
            i[0]++;
        });

        //scan for min/max value
        int lowest = 320;
        int highest = 0;
        for (int a = 0; a < heightmap.length; a++) {
            if (heightmap[a] == 0) continue;
            if (heightmap[a] < lowest) lowest = heightmap[a];
        }
        for (int a = 0; a < heightmap.length; a++) {
            if (heightmap[a] > highest) highest = heightmap[a];
        }

       //System.out.println("pos " + pos.getY() + "highest " + highest + " lowest " + lowest);

        //cancel if area not flat enough
        if (highest > center.getY()+2) return false;
        if (lowest < center.getY()-1) return false;

        //-----------------------------------------------------------

        airradius = dirtradius + 2;
        List<BlockPos> secondAirLayer = getLayer(airradius, center, noise); // 2 to 4 layers above water
        List<BlockPos> dirtLayer = getLayer(dirtradius, center, noise); // same layer as water and down
        List<BlockPos> firstWaterLayer = getLayer(waterradius, center, noise); // top water layer
        List<BlockPos> secondWaterLayer = getLayer(waterradius/2, center, noise); // bottom water layer

        secondAirLayer.forEach((pos) -> { // to add a nice edge to the lake, not just 1 block when using dirtlayer
            this.setBlockState(structureWorldAccess,pos.down(),Blocks.GRASS_BLOCK.getDefaultState());
            this.setBlockState(structureWorldAccess,pos.down(2),Blocks.DIRT.getDefaultState());
            this.setBlockState(structureWorldAccess,pos.down(3),Blocks.DIRT.getDefaultState());
            this.setBlockState(structureWorldAccess,pos.down(4),Blocks.DIRT.getDefaultState());
        });

        firstWaterLayer.forEach((pos) -> {
            this.setBlockState(structureWorldAccess,pos.down(),Blocks.WATER.getDefaultState());
            this.setBlockState(structureWorldAccess,pos.down(2),Blocks.MUD.getDefaultState());
        });

        secondWaterLayer.forEach((pos) -> {
            this.setBlockState(structureWorldAccess,pos.down(2),Blocks.WATER.getDefaultState());
            this.setBlockState(structureWorldAccess,pos.down(3),Blocks.MUD.getDefaultState());
        });

        firstAirLayer.forEach((pos) -> {
            this.setBlockState(structureWorldAccess,pos,Blocks.AIR.getDefaultState());
        });

        secondAirLayer.forEach((pos) -> {
            this.setBlockState(structureWorldAccess,pos.up(1),Blocks.AIR.getDefaultState());
            this.setBlockState(structureWorldAccess,pos.up(2),Blocks.AIR.getDefaultState());
            this.setBlockState(structureWorldAccess,pos.up(3),Blocks.AIR.getDefaultState());

        });

        List<BlockPos> shore = dirtLayer;
        shore.removeAll(firstWaterLayer);

        shore.forEach((pos) -> {
            int r = random.nextInt(15);
            if (r == 0) {
                this.setBlockState(structureWorldAccess,pos.down(),Blocks.MOSSY_COBBLESTONE.getDefaultState());
            }
            if (r == 1)
                this.setBlockState(structureWorldAccess,pos.down(),Blocks.MOSS_BLOCK.getDefaultState());

            if (r >= 3 && r < 7)
                this.setBlockState(structureWorldAccess,pos.down(),Blocks.ROOTED_DIRT.getDefaultState());

            if (r >= 7 && r < 9)
                this.setBlockState(structureWorldAccess,pos.down(),Blocks.GRASS_BLOCK.getDefaultState());

            if (r >= 9 && r <= 15)
                this.setBlockState(structureWorldAccess,pos.down(),Blocks.COARSE_DIRT.getDefaultState());

            if (r != 0)
                this.generateVegetationAroundLake(structureWorldAccess,random,pos);
        });

        int rockCount = random.nextInt(2);

        for (int a = 0; a < rockCount; a++) {
            int index = random.nextInt(firstWaterLayer.size());
            BlockPos pos = firstWaterLayer.get(index);
            this.generateMossyRock(structureWorldAccess,random,pos);
        }

        firstWaterLayer.forEach((pos) -> {
            this.generateVegetationInLake(structureWorldAccess,random,pos);
        });

        List<BlockPos> grassCloseToLake = secondAirLayer;
        shore.removeAll(firstWaterLayer);

        int bushCount = random.nextInt(4);

        for (int a = 0; a < bushCount; a++) {
            int index = random.nextInt(grassCloseToLake.size());
            BlockPos pos = grassCloseToLake.get(index);
            j = structureWorldAccess.getChunk(new BlockPos(pos.getX(),pos.getY(),pos.getZ())).getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get((32+pos.getX()%16)%16, (32+pos.getZ()%16)%16);
            pos = new BlockPos(pos.getX(),j,pos.getZ());
            Bushes bushGenerator = new Bushes(DefaultFeatureConfig.CODEC);
            bushGenerator.generateBush(structureWorldAccess,random,pos,noise);
        }

        return true;
    }
}
