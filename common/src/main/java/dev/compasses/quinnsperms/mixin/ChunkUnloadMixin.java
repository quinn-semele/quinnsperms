package dev.compasses.quinnsperms.mixin;

import dev.compasses.quinnsperms.CommonMain;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkMap.class)
public class ChunkUnloadMixin {
    @Shadow @Final
    ServerLevel level;

    @Inject(
            method = "lambda$scheduleUnload$12",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/chunk/LevelChunk;setLoaded(Z)V",
                    shift = At.Shift.AFTER
            )
    )
    private void quinnsperms$unloadClientChunk(ChunkHolder chunkHolder, long chunkPos, CallbackInfo ci) {
        CommonMain.removeChunk(level.dimension(), chunkPos);
    }
}
