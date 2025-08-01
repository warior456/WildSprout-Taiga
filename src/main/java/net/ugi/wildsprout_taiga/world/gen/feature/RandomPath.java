package net.ugi.wildsprout_taiga.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.ugi.wildsprout_taiga.tags.ModTags;


public class RandomPath extends Feature<DefaultFeatureConfig> {

    public RandomPath(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    private Vec3d pos0;
    private Vec3d pos1;
    private Vec3d pos2;
    private Vec3d pos3;
    private int steps;

    public Vec3i getCurvePos(double t, StructureWorldAccess structureWorldAccess){

        double x_d = (1-t)*((1-t)*((1-t)*this.pos0.x + t*this.pos1.x) + t*((1-t)*this.pos1.x + t*this.pos2.x)) + t*((1-t)*((1-t)*this.pos1.x + t*this.pos2.x) + t*((1-t)*this.pos2.x + t*this.pos3.x));
        double z_d = (1-t)*((1-t)*((1-t)*this.pos0.z + t*this.pos1.z) + t*((1-t)*this.pos1.z + t*this.pos2.z)) + t*((1-t)*((1-t)*this.pos1.z + t*this.pos2.z) + t*((1-t)*this.pos2.z + t*this.pos3.z));
        int x = (int)Math.round(x_d);
        int z = (int)Math.round(z_d);

        int j = structureWorldAccess.getChunk(new BlockPos(x,0,z)).getHeightmap(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).get((32+x%16)%16, (32+z%16)%16);

        return new Vec3i(x,j-1,z);

    }

    public  BlockPos[] getCurveBlockposArray(StructureWorldAccess structureWorldAccess) {
        BlockPos[] blockPosArray = new BlockPos[this.steps];

        for(int i = 0 ; i < this.steps; i++) {
            double t = i/(double)(this.steps-1);// -1 so tah the last one is t = 1
            Vec3i vec = getCurvePos(t, structureWorldAccess);

            blockPosArray[i] = new BlockPos(vec.getX(),vec.getY(),vec.getZ());
        }

        return blockPosArray;
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos center = context.getOrigin();
        Random random = context.getRandom();

        if (!(structureWorldAccess.getBlockState(center.down()).isIn(ModTags.Blocks.VALID_TAIGA_GENERATE_BLOCK))) return false;

        if (random.nextBoolean()) {
            this.pos0 = center.toCenterPos().add(3+random.nextInt(13), 0, 3+random.nextInt(13));
            this.pos3 = center.toCenterPos().add(-3-random.nextInt(14), 0, -3-random.nextInt(13));
        }
        else{
            this.pos0 = center.toCenterPos().add(-3-random.nextInt(13), 0, 3+random.nextInt(13));
            this.pos3 = center.toCenterPos().add(3+random.nextInt(13), 0, -3-random.nextInt(13));
        }

        this.pos1 = center.toCenterPos().add(random.nextInt(16)-7, 0, random.nextInt(16)-7);
        this.pos2 = center.toCenterPos().add(random.nextInt(16)-7, 0, random.nextInt(16)-7);
        this.steps = 100;

        BlockPos[] path = this.getCurveBlockposArray(structureWorldAccess);
        int height = path[0].getY();
        boolean isValidPath = true;
        //check if path is "walkable" max 1 block hight difference
        for(int i = 1 ; i < path.length; i++) {
            if (Math.abs(height - path[i].getY()) > 1){
                isValidPath = false;
                break;
            }

            if (structureWorldAccess.getBlockState(path[i]).equals(Blocks.WATER.getDefaultState())) {
                isValidPath = false;
                break;
            }
            height = path[i].getY();

        }
        if (!isValidPath) return false;

        for(int i = 0 ; i < path.length; i++) {
            int r = random.nextInt(100);
            BlockPos pos = path[i];
            if (r == 0)
                this.setBlockState(structureWorldAccess,pos, Blocks.TUFF.getDefaultState());
            if (r >= 1 && r < 20)
                this.setBlockState(structureWorldAccess,pos, Blocks.DIRT_PATH.getDefaultState());
            if (r >= 20 && r < 50)
                this.setBlockState(structureWorldAccess,pos, Blocks.COARSE_DIRT.getDefaultState());
            if (r >= 50 && r < 60)
                this.setBlockState(structureWorldAccess,pos, Blocks.ROOTED_DIRT.getDefaultState());

        }

        return true;
    }
}
