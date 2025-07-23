package net.ugi.wildsprout.world.gen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.structure.*;
import net.ugi.wildsprout.WildSproutTaiga;
import net.ugi.wildsprout.world.gen.structure.RiverGenerator;

public class ModStructurePieceTypes {
    public static StructurePieceType RIVER_SPRING = register(RiverGenerator.Spring::new, "river_spring");
    public static StructurePieceType RIVER_RIVER = register(RiverGenerator.River::new, "river_river");
    public static StructurePieceType RIVER_LAKE = register(RiverGenerator.Lake::new, "river_lake");

    private static StructurePieceType register(StructurePieceType type, String id) {
        return (StructurePieceType) Registry.register(Registries.STRUCTURE_PIECE, WildSproutTaiga.identifier(id), type);
    }

    private static StructurePieceType register(StructurePieceType.Simple type, String id) {
        return register((StructurePieceType)type, id);
    }

    public static void init() {
    }
}
