package net.ugi.wildsprout_taiga.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;

public class ChunkHelper {

    public static int getTopMotionBlockingNoLeavesYAt(StructureWorldAccess world, BlockPos pos) {
        Chunk chunk = world.getChunk(pos);
        int iteratingStartY =  getHighestNonEmptySectionYOffset(chunk) +16 -1;
        for (int y = iteratingStartY; y >= chunk.getBottomY(); y--) {
            BlockPos blockPos = new BlockPos(pos.getX(), y, pos.getZ());
            BlockState state = world.getBlockState(blockPos);
            if ((state.blocksMovement() || !state.getFluidState().isEmpty()) && !(state.getBlock() instanceof LeavesBlock)) {
                return blockPos.getY() + 1;
            }
        }
        return -64;
    }

    private static int getHighestNonEmptySectionYOffset(Chunk chunk) {
        int i = chunk.getHighestNonEmptySection();
        return i == -1 ? chunk.getBottomY() : ChunkSectionPos.getBlockCoord(chunk.sectionIndexToCoord(i));
    }
}
