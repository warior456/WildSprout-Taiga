package net.ugi.wildsprout.world.gen.structure;

import com.mojang.serialization.MapCodec;
import net.minecraft.structure.*;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;
import net.ugi.wildsprout.world.gen.ModStructureTypes;

import java.util.Optional;

public class RiverStructure extends Structure {
    public static final MapCodec<RiverStructure> CODEC = createCodec(RiverStructure::new);

    public RiverStructure(Structure.Config config) {
        super(config);
    }

    public Optional<Structure.StructurePosition> getStructurePosition(Structure.Context context) {
        return Optional.of(new Structure.StructurePosition(context.chunkPos().getStartPos(), (collector) -> addPieces(collector, context)));
    }

    private static void addPieces(StructurePiecesCollector collector, Structure.Context context) {
        int i = 0;

        RiverGenerator.Start start;
        RiverGenerator.init();

        ChunkRandom random = context.random();
        int x_offset = random.nextInt(16);
        int z_offset = random.nextInt(16);

        start = new RiverGenerator.Start(context.random(), context.chunkPos().getOffsetX(x_offset), context.chunkPos().getOffsetZ(z_offset));
        collector.addPiece(start);

        int y_coord = context.chunkGenerator().getHeight(context.chunkPos().x*16+x_offset,context.chunkPos().z*16+z_offset, Heightmap.Type.WORLD_SURFACE_WG,context.world(),context.noiseConfig());
        collector.shiftInto(y_coord, y_coord, context.random(), 0);










//        do {
//            collector.clear();
//            RiverGenerator.init();
//            start = new RiverGenerator.Start(context.random(), context.chunkPos().getOffsetX(2), context.chunkPos().getOffsetZ(2));
//            collector.addPiece(start);
//            //start.fillOpenings(start, collector, context.random());
//            List<StructurePiece> list = start.pieces;
//
//            while(!list.isEmpty()) {
//                int j = context.random().nextInt(list.size());
//                StructurePiece structurePiece = (StructurePiece)list.remove(j);
//                structurePiece.fillOpenings(start, collector, context.random());
//            }
//            int y_coord = context.chunkGenerator().getHeight(context.chunkPos().x*16,context.chunkPos().z*16, Heightmap.Type.WORLD_SURFACE_WG,context.world(),context.noiseConfig());
//            collector.shiftInto(y_coord, y_coord, context.random(), 0);
//        } while(collector.isEmpty() /*|| start.lake == null*/);

    }

    public StructureType<?> getType() {
        return ModStructureTypes.RIVER;
    }
}
