package in.rubyra.seals.client.render;

import in.rubyra.seals.client.model.SealEntityModel;
import in.rubyra.seals.entity.behavior.SealEntity;
import net.minecraft.client.render.entity.CreeperEntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class SealEntityRenderer extends LivingEntityRenderer {
    public SealEntityRenderer(SealEntityModel entityModel) {
        super(entityModel, 0.45F);
    }

    //protected void applyScale(LivingEntity livingEntity, float f) {
    //    SealEntity sealEntity = (SealEntity)livingEntity;
    //    float s = sealEntity.getScale(f);
    //    GL11.glScalef(s, s, s);
    //}

    public void render(LivingEntity livingEntity, double d, double e, double f, float g, float h) {
        SealEntity sealEntity = (SealEntity)livingEntity;
        // rotate whole body when looking around
        sealEntity.bodyYaw = sealEntity.yaw;

        SealEntityModel sealModel = (SealEntityModel)model;
        boolean isDapper = sealEntity.isDapper();
        sealModel.setDapper(isDapper);

        //if (isDapper)
        //    renderNameTag(sealEntity, "DAPPER SEAL", d, e-1, f, 64);
        //else
        //    renderNameTag(sealEntity, "LITTEL SEAL", d, e-1, f, 64);

        super.render(sealEntity, d, e, f, g, h);
    }
}