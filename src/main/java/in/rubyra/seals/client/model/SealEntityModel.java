package in.rubyra.seals.client.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;

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

        this.body.addCuboid(-4, off, -5, 8, 6, 10);
        this.body.setPivot(0, 0, 0);

        this.nose.addCuboid(-2, off + 1, -1, 4, 2, 1);
        this.nose.setPivot(0, 2, -5);


        this.frontFlipperLeft.addCuboid(0, 0, -1, 1, 2, 2);
        this.frontFlipperLeft.setPivot(-5, off + 4, 0);

        this.frontFlipperRight.addCuboid(0, 0, 0, 1, 2, 2);
        this.frontFlipperRight.setPivot(4, off + 4, -1);

        this.backFlipperLeft.addCuboid(-1, off + 3, 0, 1, 2, 3);
        this.backFlipperLeft.setPivot(0, 1, 5);
        this.backFlipperLeft.yaw = -5F / (180F / (float)Math.PI);

        this.backFlipperRight.addCuboid(0, off + 3, 0, 1, 2, 3);
        this.backFlipperRight.setPivot(0, 1, 5);
        this.backFlipperRight.yaw = 5F / (180F / (float)Math.PI);


        this.hatBrim.addCuboid(-3, off - 1, -4, 6, 1, 6);
        this.hatBrim.setPivot(0, 0, 0);

        this.hatTop.addCuboid(-2, off - 5, -3, 4, 4, 4);
        this.hatTop.setPivot(0, 0, 0);
    }

    public void render(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {
        this.setAngles(limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);
        this.body.render(scale);
        this.nose.render(scale);

        this.frontFlipperLeft.render(scale);
        this.frontFlipperRight.render(scale);
        this.backFlipperLeft.render(scale);
        this.backFlipperRight.render(scale);

        this.hatBrim.render(scale);
        this.hatTop.render(scale);
    }

    public void setAngles(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {
        frontFlipperLeft.roll  =  0.3F - MathHelper.cos(limbAngle * 5F) * 0.4F;
        frontFlipperRight.roll = -0.3F + MathHelper.cos(limbAngle * 5F) * 0.4F;

        backFlipperLeft.yaw = -0.18F + MathHelper.cos(limbAngle * 4F) * 0.15F;
        backFlipperRight.yaw = 0.18F - MathHelper.cos(limbAngle * 4F) * 0.15F;
    }

    public void setDapper(boolean dapper) {
        hatBrim.hidden = !dapper;
        hatTop.hidden = !dapper;
    }
}
