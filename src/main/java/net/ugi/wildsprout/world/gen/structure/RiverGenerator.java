package net.ugi.wildsprout.world.gen.structure;

import net.minecraft.block.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.*;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.ugi.wildsprout.world.gen.ModStructurePieceTypes;

import java.util.List;

public class RiverGenerator {

    private static List<RiverGenerator.PieceData> possiblePieces;
    static Class<? extends RiverGenerator.Piece> activePieceType;

    public static void init() {
        activePieceType = null;
    }

//    private static RiverGenerator.Piece createPiece(Class<? extends RiverGenerator.Piece> pieceType, StructurePiecesHolder holder, Random random, int x, int y, int z, @Nullable Direction orientation, int chainLength) {
//        RiverGenerator.Piece piece = null;
//        if (pieceType == RiverGenerator.Spring.class) {
//            piece = RiverGenerator.Spring.create(holder, random, x, y, z, orientation, chainLength);
//        } else if (pieceType == RiverGenerator.River.class) {
//            piece = RiverGenerator.River.create(holder, random, x, y, z, orientation, chainLength);
//        }
//
//        return piece;
//    }
//
//    private static RiverGenerator.Piece pickPiece(RiverGenerator.Start start, StructurePiecesHolder holder, Random random, int x, int y, int z, Direction orientation, int chainLength) {
//        if (activePieceType != null) {
//            RiverGenerator.Piece piece = createPiece(activePieceType, holder, random, x, y, z, orientation, chainLength);
//            activePieceType = null;
//            if (piece != null) {
//                return piece;
//            }
//        }
//
//        return null;
//    }
//
//    static StructurePiece pieceGenerator(RiverGenerator.Start start, StructurePiecesHolder holder, Random random, int x, int y, int z, @Nullable Direction orientation, int chainLength) {
//        if (chainLength > 50) {
//            return null;
//        } else if (Math.abs(x - start.getBoundingBox().getMinX()) <= 112 && Math.abs(z - start.getBoundingBox().getMinZ()) <= 112) {
//            StructurePiece structurePiece = pickPiece(start, holder, random, x, y, z, orientation, chainLength + 1);
//            if (structurePiece != null) {
//                holder.addPiece(structurePiece);
//                start.pieces.add(structurePiece);
//            }
//
//            return structurePiece;
//        } else {
//            return null;
//        }
//    }

    static class PieceData {
        public final Class<? extends RiverGenerator.Piece> pieceType;
        public final int weight;
        public int generatedCount;
        public final int limit;

        public PieceData(Class<? extends RiverGenerator.Piece> pieceType, int weight, int limit) {
            this.pieceType = pieceType;
            this.weight = weight;
            this.limit = limit;
        }

        public boolean canGenerate(int chainLength) {
            return this.limit == 0 || this.generatedCount < this.limit;
        }

        public boolean canGenerate() {
            return this.limit == 0 || this.generatedCount < this.limit;
        }
    }

    abstract static class Piece extends StructurePiece {

        protected Piece(StructurePieceType structurePieceType, int length, BlockBox blockBox) {
            super(structurePieceType, length, blockBox);
        }

        public Piece(StructurePieceType structurePieceType, NbtCompound nbtCompound) {
            super(structurePieceType, nbtCompound);
        }

        protected void writeNbt(StructureContext context, NbtCompound nbt) {
        }

//        @Nullable
//        protected StructurePiece fillForwardOpening(RiverGenerator.Start start, StructurePiecesHolder holder, Random random, int leftRightOffset, int heightOffset) {
//            Direction direction = this.getFacing();
//            if (direction != null) {
//                switch (direction) {
//                    case NORTH -> {
//                        return RiverGenerator.pieceGenerator(start, holder, random, this.boundingBox.getMinX() + leftRightOffset, this.boundingBox.getMinY() + heightOffset, this.boundingBox.getMinZ() - 1, direction, this.getChainLength());
//                    }
//                    case SOUTH -> {
//                        return RiverGenerator.pieceGenerator(start, holder, random, this.boundingBox.getMinX() + leftRightOffset, this.boundingBox.getMinY() + heightOffset, this.boundingBox.getMaxZ() + 1, direction, this.getChainLength());
//                    }
//                    case WEST -> {
//                        return RiverGenerator.pieceGenerator(start, holder, random, this.boundingBox.getMinX() - 1, this.boundingBox.getMinY() + heightOffset, this.boundingBox.getMinZ() + leftRightOffset, direction, this.getChainLength());
//                    }
//                    case EAST -> {
//                        return RiverGenerator.pieceGenerator(start, holder, random, this.boundingBox.getMaxX() + 1, this.boundingBox.getMinY() + heightOffset, this.boundingBox.getMinZ() + leftRightOffset, direction, this.getChainLength());
//                    }
//                }
//            }
//
//            return null;
//        }
//
//        @Nullable
//        protected StructurePiece fillNWOpening(RiverGenerator.Start start, StructurePiecesHolder holder, Random random, int heightOffset, int leftRightOffset) {
//            Direction direction = this.getFacing();
//            if (direction != null) {
//                switch (direction) {
//                    case NORTH -> {
//                        return RiverGenerator.pieceGenerator(start, holder, random, this.boundingBox.getMinX() - 1, this.boundingBox.getMinY() + heightOffset, this.boundingBox.getMinZ() + leftRightOffset, Direction.WEST, this.getChainLength());
//                    }
//                    case SOUTH -> {
//                        return RiverGenerator.pieceGenerator(start, holder, random, this.boundingBox.getMinX() - 1, this.boundingBox.getMinY() + heightOffset, this.boundingBox.getMinZ() + leftRightOffset, Direction.WEST, this.getChainLength());
//                    }
//                    case WEST -> {
//                        return RiverGenerator.pieceGenerator(start, holder, random, this.boundingBox.getMinX() + leftRightOffset, this.boundingBox.getMinY() + heightOffset, this.boundingBox.getMinZ() - 1, Direction.NORTH, this.getChainLength());
//                    }
//                    case EAST -> {
//                        return RiverGenerator.pieceGenerator(start, holder, random, this.boundingBox.getMinX() + leftRightOffset, this.boundingBox.getMinY() + heightOffset, this.boundingBox.getMinZ() - 1, Direction.NORTH, this.getChainLength());
//                    }
//                }
//            }
//
//            return null;
//        }
//
//        @Nullable
//        protected StructurePiece fillSEOpening(RiverGenerator.Start start, StructurePiecesHolder holder, Random random, int heightOffset, int leftRightOffset) {
//            Direction direction = this.getFacing();
//            if (direction != null) {
//                switch (direction) {
//                    case NORTH -> {
//                        return RiverGenerator.pieceGenerator(start, holder, random, this.boundingBox.getMaxX() + 1, this.boundingBox.getMinY() + heightOffset, this.boundingBox.getMinZ() + leftRightOffset, Direction.EAST, this.getChainLength());
//                    }
//                    case SOUTH -> {
//                        return RiverGenerator.pieceGenerator(start, holder, random, this.boundingBox.getMaxX() + 1, this.boundingBox.getMinY() + heightOffset, this.boundingBox.getMinZ() + leftRightOffset, Direction.EAST, this.getChainLength());
//                    }
//                    case WEST -> {
//                        return RiverGenerator.pieceGenerator(start, holder, random, this.boundingBox.getMinX() + leftRightOffset, this.boundingBox.getMinY() + heightOffset, this.boundingBox.getMaxZ() + 1, Direction.SOUTH, this.getChainLength());
//                    }
//                    case EAST -> {
//                        return RiverGenerator.pieceGenerator(start, holder, random, this.boundingBox.getMinX() + leftRightOffset, this.boundingBox.getMinY() + heightOffset, this.boundingBox.getMaxZ() + 1, Direction.SOUTH, this.getChainLength());
//                    }
//                }
//            }
//
//            return null;
//        }
//
        protected static boolean isInBounds(BlockBox boundingBox) {
            return boundingBox != null && boundingBox.getMinY() > 10;
        }
    }

    public static class Spring extends RiverGenerator.Piece {

        public Spring(StructurePieceType structurePieceType, int chainLength, int x, int z, Direction orientation) {
            super(structurePieceType, chainLength, createBox(x, 64, z, orientation, 5, 11, 5));
            this.setOrientation(orientation);
        }

        public Spring(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
            super(ModStructurePieceTypes.RIVER_SPRING, chainLength, boundingBox);
            this.setOrientation(orientation);
        }

        public Spring(NbtCompound nbt) {
            super(ModStructurePieceTypes.RIVER_SPRING, nbt);
        }

        protected void writeNbt(StructureContext context, NbtCompound nbt) {
            super.writeNbt(context, nbt);
        }

        public static RiverGenerator.Spring create(StructurePiecesHolder holder, Random random, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -4, -1, 0, 14, 11, 15, orientation);
            if (!isInBounds(blockBox) || holder.getIntersecting(blockBox) != null) {
                blockBox = BlockBox.rotated(x, y, z, -4, -1, 0, 14, 6, 15, orientation);
                if (!isInBounds(blockBox) || holder.getIntersecting(blockBox) != null) {
                    return null;
                }
            }

            return new RiverGenerator.Spring(chainLength, random, blockBox, orientation);
        }

        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {

            BlockPos startPos = chunkBox.getCenter();

            int height = world.getChunk(new BlockPos(startPos.getX(),startPos.getY(),startPos.getZ())).getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get((32+startPos.getX()%16)%16, (32+startPos.getZ()%16)%16);
            startPos = new BlockPos(startPos.getX(),height,startPos.getZ());
            world.setBlockState(startPos,Blocks.DIAMOND_BLOCK.getDefaultState(),2);

//            RiverGenerator.Start start = new RiverGenerator.Start(ModStructurePieceTypes.RIVER_RIVER,random, chunkPos.getOffsetX(16), chunkPos.getOffsetZ(16));
//
//            start.generate(world, structureAccessor, chunkGenerator, random, chunkBox, chunkPos,pivot);

        }
    }

    public static class River extends RiverGenerator.Piece {

        public River(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
            super(ModStructurePieceTypes.RIVER_RIVER, chainLength, boundingBox);
            this.setOrientation(orientation);
        }

        public River(NbtCompound nbt) {
            super(ModStructurePieceTypes.RIVER_RIVER, nbt);
        }

        protected void writeNbt(StructureContext context, NbtCompound nbt) {
            super.writeNbt(context, nbt);
        }

        public static RiverGenerator.River create(StructurePiecesHolder holder, Random random, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -4, -1, 0, 14, 11, 15, orientation);
            if (!isInBounds(blockBox) || holder.getIntersecting(blockBox) != null) {
                blockBox = BlockBox.rotated(x, y, z, -4, -1, 0, 14, 6, 15, orientation);
                if (!isInBounds(blockBox) || holder.getIntersecting(blockBox) != null) {
                    return null;
                }
            }

            return new RiverGenerator.River(chainLength, random, blockBox, orientation);
        }

        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {
            this.addBlock(world, Blocks.GOLD_BLOCK.getDefaultState(), 0, 0, 0, chunkBox);

        }
    }

    public static class Lake extends RiverGenerator.Piece {

        public Lake(int chainLength, Random random, BlockBox boundingBox, Direction orientation) {
            super(ModStructurePieceTypes.RIVER_LAKE, chainLength, boundingBox);
            this.setOrientation(orientation);
        }

        public Lake(NbtCompound nbt) {
            super(ModStructurePieceTypes.RIVER_LAKE, nbt);
        }

        protected void writeNbt(StructureContext context, NbtCompound nbt) {
            super.writeNbt(context, nbt);
        }

        public static RiverGenerator.Lake create(StructurePiecesHolder holder, Random random, int x, int y, int z, Direction orientation, int chainLength) {
            BlockBox blockBox = BlockBox.rotated(x, y, z, -4, -1, 0, 14, 11, 15, orientation);
            if (!isInBounds(blockBox) || holder.getIntersecting(blockBox) != null) {
                blockBox = BlockBox.rotated(x, y, z, -4, -1, 0, 14, 6, 15, orientation);
                if (!isInBounds(blockBox) || holder.getIntersecting(blockBox) != null) {
                    return null;
                }
            }

            return new RiverGenerator.Lake(chainLength, random, blockBox, orientation);
        }

        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {
            this.addBlock(world, Blocks.EMERALD_BLOCK.getDefaultState(), 0, 0, 0, chunkBox);



        }
    }

    public static class Start extends RiverGenerator.Spring {
//        public RiverGenerator.PieceData lastPiece;
//        @Nullable
//        public RiverGenerator.Lake lake;
//        public final List<StructurePiece> pieces = Lists.newArrayList();

        public Start(Random random, int i, int j) {
            super(ModStructurePieceTypes.RIVER_LAKE, 0, i, j, getRandomHorizontalDirection(random));
        }
        public Start(StructurePieceType type,Random random, int i, int j) {
            super(type, 0, i, j, getRandomHorizontalDirection(random));
        }
    }
}
