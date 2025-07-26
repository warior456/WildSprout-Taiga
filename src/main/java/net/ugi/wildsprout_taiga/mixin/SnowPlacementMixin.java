package net.ugi.wildsprout_taiga.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import net.ugi.wildsprout_taiga.tags.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnowBlock.class)
public class SnowPlacementMixin {
    @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
    private void onCanPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        //this mixin allows snow to be placed on ice, packed ice, and blue ice in worldgen, needed for the fluffy snow feature
        BlockState blockState = world.getBlockState(pos.down());
        if (blockState.isIn(ModTags.Blocks.OVERRIDE_SNOW_LAYER_CANNOT_SURVIVE_ON) /*&& WildSproutPlains.CONFIG.SnowOnIceEnabled && WildSproutPlains.CONFIG.SnowyPlainsEnabled*/) {
            cir.setReturnValue(true);
        }
    }
}