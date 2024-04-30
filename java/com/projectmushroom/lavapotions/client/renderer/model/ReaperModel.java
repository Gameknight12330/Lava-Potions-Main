package com.projectmushroom.lavapotions.client.renderer.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.entity.Reaper;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ReaperModel<T extends Reaper> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "reapermodel"), "main");
	private final ModelPart reaper;

	public ReaperModel(ModelPart root) {
		this.reaper = root.getChild("reaper");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition reaper = partdefinition.addOrReplaceChild("reaper", CubeListBuilder.create().texOffs(212, 234).addBox(-10.0F, -85.0F, 7.25F, 20.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition Scythe = reaper.addOrReplaceChild("Scythe", CubeListBuilder.create(), PartPose.offsetAndRotation(-11.0F, -44.0F, 15.0F, -0.3019F, 0.0F, 0.0F));

		PartDefinition Handle = Scythe.addOrReplaceChild("Handle", CubeListBuilder.create().texOffs(0, 81).addBox(-29.0F, -39.0F, 11.0F, 3.0F, 17.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(40, 103).addBox(-29.0F, -66.0F, 12.0F, 3.0F, 31.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(157, 141).addBox(-29.0F, -86.0F, 11.0F, 3.0F, 24.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(148, 64).addBox(-29.0F, -106.0F, 10.0F, 3.0F, 24.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(22, 146).addBox(-29.0F, -126.0F, 11.0F, 3.0F, 24.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(19.0F, 50.0F, -9.0F));

		PartDefinition Blade = Scythe.addOrReplaceChild("Blade", CubeListBuilder.create().texOffs(135, 144).addBox(-29.0F, -124.0F, 14.0F, 3.0F, 16.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(107, 144).addBox(-29.0F, -123.0F, 22.0F, 3.0F, 15.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(82, 145).addBox(-29.0F, -121.0F, 33.0F, 3.0F, 12.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 146).addBox(-29.0F, -118.0F, 41.0F, 3.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(72, 0).addBox(-29.0F, -115.0F, 49.0F, 3.0F, 8.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(88, 44).addBox(-29.0F, -112.0F, 56.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-29.0F, -108.0F, 62.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(19.0F, 50.0F, -9.0F));

		PartDefinition body = reaper.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -65.0F, -8.0F, 28.0F, 65.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition sleeves = body.addOrReplaceChild("sleeves", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition R = sleeves.addOrReplaceChild("R", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 5.0F));

		PartDefinition cube_r1 = R.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(88, 0).addBox(-5.5F, 0.0F, -9.0F, 11.0F, 28.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.5F, -65.0F, -13.0F, 1.2217F, 0.0F, 0.0F));

		PartDefinition L = sleeves.addOrReplaceChild("L", CubeListBuilder.create().texOffs(64, 101).addBox(14.0F, -65.0F, -13.0F, 11.0F, 28.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));

		PartDefinition arms = body.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition L2 = arms.addOrReplaceChild("L2", CubeListBuilder.create().texOffs(122, 105).addBox(18.3F, -63.8F, -3.0F, 6.0F, 33.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition R2 = arms.addOrReplaceChild("R2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r2 = R2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(4, 107).addBox(-3.3F, 0.2F, -4.0F, 6.0F, 33.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.0F, -64.0F, -6.0F, 1.2217F, 0.0F, 0.0F));

		PartDefinition head = reaper.addOrReplaceChild("head", CubeListBuilder.create().texOffs(178, 160).addBox(-10.0F, -85.0F, -10.0F, 20.0F, 20.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hood = head.addOrReplaceChild("hood", CubeListBuilder.create().texOffs(142, 0).addBox(-10.0F, -85.0F, -12.0F, 20.0F, 20.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(152, 215).addBox(-12.0F, -85.0F, -10.0F, 2.0F, 20.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(72, 215).addBox(10.0F, -85.0F, -10.0F, 2.0F, 20.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(0, 179).addBox(-10.0F, -87.0F, -10.0F, 20.0F, 2.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(212, 0).addBox(-10.0F, -85.0F, 11.0F, 20.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(216, 18).addBox(-9.0F, -83.0F, 12.0F, 18.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(224, 47).addBox(-7.0F, -81.0F, 13.0F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(232, 58).addBox(-5.0F, -79.0F, 13.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(240, 74).addBox(-3.0F, -77.0F, 12.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		reaper.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}