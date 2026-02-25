package in.rubyra.seals.mixin;

import in.rubyra.seals.SealsMod;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "onCollision", at = @At("HEAD"), cancellable = true)
    public void onCollision(Entity otherEntity, CallbackInfo ci) {
        if (SealsMod.SEALS_CONFIG.enablePushableEntityBugFix && !otherEntity.isPushable()) {
            ci.cancel();
        }
    }
}
