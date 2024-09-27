package dev.compasses.quinnsperms.mixin;

import dev.compasses.quinnsperms.CommonMain;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sereneseasons.season.SeasonHooks;

@Mixin(SeasonHooks.class)
public class SeasonsHookMixin {
    @Inject(
            method = "warmEnoughToRainSeasonal(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void quinnsperms$preventSnowIfClaimed(LevelReader reader, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        Boolean result = CommonMain.checkIfSeasonsSnowDisabled(cir.getReturnValueZ(), reader, pos);

        if (result != null) {
            cir.setReturnValue(result);
        }
    }
}
