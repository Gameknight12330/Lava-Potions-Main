package com.projectmushroom.lavapotions.client.renderer;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LavaAreaEffectCloudRenderer<T extends Entity> extends EntityRenderer<T> {
   
	public LavaAreaEffectCloudRenderer(EntityRendererProvider.Context p_174326_) {
	   super(p_174326_);
	}

   @SuppressWarnings("deprecation")
   public ResourceLocation getTextureLocation(T texture) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}