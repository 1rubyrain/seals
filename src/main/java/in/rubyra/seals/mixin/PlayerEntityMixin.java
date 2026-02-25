package in.rubyra.seals.mixin;

import in.rubyra.seals.entity.behavior.SealEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin extends LivingEntity {
    public PlayerEntityMixin(World world) {
        super(world);
    }

    @Unique
    private double pendingVelocity = 0;

    // returns new velocity (or 0 if no seal)
    @Unique
    private double checkSealCollision() {

        var EntList = world.getEntities(this, boundingBox.expand(0.275, 0.5, 0.275));
        if (EntList == null)
            return 0;

        for (Object ent : EntList) {
            if (ent == null)
                continue;

            if (ent instanceof SealEntity seal) {
                if (seal.dead)
                    continue;

                return velocityY * -0.999;
            }
        }

        return 0;
    }


    // this crap code is because a player can pass over a seal in a single tick without intersecting hitboxes
    // (similar to bljs in sm64)
    @Inject(method = "onLanding", at = @At("HEAD"), cancellable = true)
    public void onLanding(float fallDistance, CallbackInfo ci) {

        double newVelocity = checkSealCollision();
        if (newVelocity == 0)
            return;

        ci.cancel();
        pendingVelocity = newVelocity;
    }
    @Inject(method = "tickMovement", at = @At("HEAD"))
    public void tickMovement(CallbackInfo ci) {

        if (pendingVelocity == 0)
        {
            // if we arent falling dont bother checking
            if (velocityY > -0.25)
                return;

            pendingVelocity = checkSealCollision();
            if (pendingVelocity == 0)
                return;
        }

        fallDistance = 0;
        world.addParticle("explode", x, y - 1, z, 0, 0, 0);

        if (!isSneaking())
            velocityY = pendingVelocity;

        pendingVelocity = 0;
    }
}
