package net.ugi.wildsprout.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpreadableBlock.class)
public class SnowyGrassMixin {
    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    private static void onCanSurvive(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        //to fix grassblock dying (becoming dirt) when more than 1 snow layer is on top of it
        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(Blocks.SNOW) && blockState.get(SnowBlock.LAYERS) < 8 /*&& WildSproutPlains.CONFIG.SnowyPlainsEnabled*/) {
            cir.setReturnValue(true);
        }
    }
}