package com.projectmushroom.lavapotions.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.client.renderer.model.ReaperModel;
import com.projectmushroom.lavapotions.entity.Reaper;

import net.minecraft.client.model.GuardianModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ReaperRenderer<Type extends Reaper> extends MobRenderer<Type, ReaperModel<Type>> 
{
	
	private static final ResourceLocation REAPER_BEAM_LOCATION = new ResourceLocation(LavaPotions.MOD_ID, "textures/entity/reaper_beam.png");
	private static final RenderType BEAM_RENDER_TYPE = RenderType.entityCutoutNoCull(REAPER_BEAM_LOCATION);

	   protected ReaperRenderer(EntityRendererProvider.Context p_174161_, float p_174162_, ModelLayerLocation p_174163_) {
	      super(p_174161_, new ReaperModel(p_174161_.bakeLayer(p_174163_)), p_174162_);
	   }

	   public boolean shouldRender(Type reaper, Frustum frustum, double x, double y, double z) {
	      if (super.shouldRender(reaper, frustum, x, y, z)) {
	         return true;
	      } else {
	         if (reaper.hasActiveAttackTarget()) {
	            LivingEntity livingentity = reaper.getActiveAttackTarget();
	            if (livingentity != null) {
	               Vec3 vec3 = this.getPosition(livingentity, (double)livingentity.getBbHeight() * 0.5D, 1.0F);
	               Vec3 vec31 = this.getPosition(reaper, (double)reaper.getEyeHeight(), 1.0F);
	               return frustum.isVisible(new AABB(vec31.x, vec31.y, vec31.z, vec3.x, vec3.y, vec3.z));
	            }
	         }

	         return false;
	      }
	   }

	   private Vec3 getPosition(LivingEntity livingEntity, double yOffset, float partialTicks) {
	      double d0 = Mth.lerp((double)partialTicks, livingEntity.xOld, livingEntity.getX());
	      double d1 = Mth.lerp((double)partialTicks, livingEntity.yOld, livingEntity.getY()) + yOffset;
	      double d2 = Mth.lerp((double)partialTicks, livingEntity.zOld, livingEntity.getZ());
	      return new Vec3(d0, d1, d2);
	   }

	   public void render(Type reaper, float entityYaw, float partialTicks, PoseStack pose, MultiBufferSource buffersource, int p_114834_) {
	      super.render(reaper, entityYaw, partialTicks, pose, buffersource, p_114834_);
	      LivingEntity livingentity = reaper.getActiveAttackTarget();
	      if (livingentity != null) {
	         float f = reaper.getAttackAnimationScale(partialTicks);
	         float f1 = (float)reaper.level.getGameTime() + partialTicks;
	         float f2 = f1 * 0.5F % 1.0F;
	         float f3 = reaper.getEyeHeight();
	         pose.pushPose();
	         pose.translate(0.0D, (double)f3, 0.0D);
	         Vec3 vec3 = this.getPosition(livingentity, (double)livingentity.getBbHeight() * 0.5D, partialTicks);
	         Vec3 vec31 = this.getPosition(reaper, (double)f3, partialTicks);
	         Vec3 vec32 = vec3.subtract(vec31);
	         float f4 = (float)(vec32.length() + 1.0D);
	         vec32 = vec32.normalize();
	         float f5 = (float)Math.acos(vec32.y);
	         float f6 = (float)Math.atan2(vec32.z, vec32.x);
	         pose.mulPose(Vector3f.YP.rotationDegrees((((float)Math.PI / 2F) - f6) * (180F / (float)Math.PI)));
	         pose.mulPose(Vector3f.XP.rotationDegrees(f5 * (180F / (float)Math.PI)));
	         int i = 1;
	         float f7 = f1 * 0.05F * -1.5F;
	         float f8 = f * f;
	         int j = 255;
	         int k = 0;
	         int l = 0;
	         float f9 = 0.2F;
	         float f10 = 0.282F;
	         float f11 = Mth.cos(f7 + 2.3561945F) * 0.282F;
	         float f12 = Mth.sin(f7 + 2.3561945F) * 0.282F;
	         float f13 = Mth.cos(f7 + ((float)Math.PI / 4F)) * 0.282F;
	         float f14 = Mth.sin(f7 + ((float)Math.PI / 4F)) * 0.282F;
	         float f15 = Mth.cos(f7 + 3.926991F) * 0.282F;
	         float f16 = Mth.sin(f7 + 3.926991F) * 0.282F;
	         float f17 = Mth.cos(f7 + 5.4977875F) * 0.282F;
	         float f18 = Mth.sin(f7 + 5.4977875F) * 0.282F;
	         float f19 = Mth.cos(f7 + (float)Math.PI) * 0.2F;
	         float f20 = Mth.sin(f7 + (float)Math.PI) * 0.2F;
	         float f21 = Mth.cos(f7 + 0.0F) * 0.2F;
	         float f22 = Mth.sin(f7 + 0.0F) * 0.2F;
	         float f23 = Mth.cos(f7 + ((float)Math.PI / 2F)) * 0.2F;
	         float f24 = Mth.sin(f7 + ((float)Math.PI / 2F)) * 0.2F;
	         float f25 = Mth.cos(f7 + ((float)Math.PI * 1.5F)) * 0.2F;
	         float f26 = Mth.sin(f7 + ((float)Math.PI * 1.5F)) * 0.2F;
	         float f27 = 0.0F;
	         float f28 = 0.4999F;
	         float f29 = -1.0F + f2;
	         float f30 = f4 * 2.5F + f29;
	         VertexConsumer vertexconsumer = buffersource.getBuffer(BEAM_RENDER_TYPE);
	         PoseStack.Pose posestack$pose = pose.last();
	         Matrix4f matrix4f = posestack$pose.pose();
	         Matrix3f matrix3f = posestack$pose.normal();
	         vertex(vertexconsumer, matrix4f, matrix3f, f19, f4, f20, j, k, l, 0.4999F, f30);
	         vertex(vertexconsumer, matrix4f, matrix3f, f19, 0.0F, f20, j, k, l, 0.4999F, f29);
	         vertex(vertexconsumer, matrix4f, matrix3f, f21, 0.0F, f22, j, k, l, 0.0F, f29);
	         vertex(vertexconsumer, matrix4f, matrix3f, f21, f4, f22, j, k, l, 0.0F, f30);
	         vertex(vertexconsumer, matrix4f, matrix3f, f23, f4, f24, j, k, l, 0.4999F, f30);
	         vertex(vertexconsumer, matrix4f, matrix3f, f23, 0.0F, f24, j, k, l, 0.4999F, f29);
	         vertex(vertexconsumer, matrix4f, matrix3f, f25, 0.0F, f26, j, k, l, 0.0F, f29);
	         vertex(vertexconsumer, matrix4f, matrix3f, f25, f4, f26, j, k, l, 0.0F, f30);
	         float f31 = 0.0F;
	         if (reaper.tickCount % 2 == 0) {
	            f31 = 0.5F;
	         }

	         vertex(vertexconsumer, matrix4f, matrix3f, f11, f4, f12, j, k, l, 0.5F, f31 + 0.5F);
	         vertex(vertexconsumer, matrix4f, matrix3f, f13, f4, f14, j, k, l, 1.0F, f31 + 0.5F);
	         vertex(vertexconsumer, matrix4f, matrix3f, f17, f4, f18, j, k, l, 1.0F, f31);
	         vertex(vertexconsumer, matrix4f, matrix3f, f15, f4, f16, j, k, l, 0.5F, f31);
	         pose.popPose();
	      }

	   }

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, float x, float y, float z, int red, int green, int blue, float texU, float texV) {
	    vertexConsumer.vertex(matrix4f, x, y, z).color(red, green, blue, 255).uv(texU, texV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
	}

	private static final ResourceLocation TEXTURE = new ResourceLocation(LavaPotions.MOD_ID, 
			"textures/entity/reaper.png");
	
	public ReaperRenderer(EntityRendererProvider.Context context) 
	{
		super(context, new ReaperModel<>(context.bakeLayer(ReaperModel.LAYER_LOCATION)), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(Type entity) 
	{
		return TEXTURE;
	}

}
