package in.rubyra.seals.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class SealEntityModel extends EntityModel {
    public ModelPart body = new ModelPart(0, 0);
    public ModelPart nose = new ModelPart(26, 7);

    public ModelPart frontFlipperLeft = new ModelPart(2, 12);
    public ModelPart frontFlipperRight = new ModelPart(21, 12);
    public ModelPart backFlipperLeft = new ModelPart(28, 11);
    public ModelPart backFlipperRight = new ModelPart(28, 11);

    public ModelPart hatBrim = new ModelPart(0, 16);
    public ModelPart hatTop = new ModelPart(24, 16);

    public SealEntityModel() {
        float off = 18;

        body.addCuboid(-4, off, -5, 8, 6, 10);
        body.setPivot(0, 0, 0);

        nose.addCuboid(-2, off + 1, -1, 4, 2, 1);
        nose.setPivot(0, 2, -5);


        frontFlipperLeft.addCuboid(0, 0, -1, 1, 2, 2);
        frontFlipperLeft.setPivot(-5, off + 4, 0);

        frontFlipperRight.addCuboid(0, 0, 0, 1, 2, 2);
        frontFlipperRight.setPivot(4, off + 4, -1);

        backFlipperLeft.addCuboid(-1, off + 3, 0, 1, 2, 3);
        backFlipperLeft.setPivot(0, 1, 5);
        backFlipperLeft.yaw = -5F / (180F / (float)Math.PI);

        backFlipperRight.addCuboid(0, off + 3, 0, 1, 2, 3);
        backFlipperRight.setPivot(0, 1, 5);
        backFlipperRight.yaw = 5F / (180F / (float)Math.PI);


        hatBrim.addCuboid(-3, off - 1, -4, 6, 1, 6);
        hatBrim.setPivot(0, 0, 0);

        hatTop.addCuboid(-2, off - 5, -3, 4, 4, 4);
        hatTop.setPivot(0, 0, 0);
    }

    public void render(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {
        setAngles(limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);
        body.render(scale);
        nose.render(scale);

        frontFlipperLeft.render(scale);
        frontFlipperRight.render(scale);
        backFlipperLeft.render(scale);
        backFlipperRight.render(scale);

        hatBrim.render(scale);
        hatTop.render(scale);
    }

    public void setAngles(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {
        frontFlipperLeft.roll  =  0.3F - MathHelper.cos(limbAngle * 5F) * 0.4F;
        frontFlipperRight.roll = -0.3F + MathHelper.cos(limbAngle * 5F) * 0.4F;

        backFlipperLeft.yaw = -0.18F + MathHelper.cos(limbAngle * 4F) * 0.15F;
        backFlipperRight.yaw = 0.18F - MathHelper.cos(limbAngle * 4F) * 0.15F;
    }

    public void setDapper(boolean dapper) {
        hatBrim.hidden = hatTop.hidden = !dapper;
    }
}
