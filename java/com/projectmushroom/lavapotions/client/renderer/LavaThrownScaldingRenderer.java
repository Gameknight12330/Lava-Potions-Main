package com.projectmushroom.lavapotions.client.renderer;

import com.projectmushroom.lavapotions.entity.LavaThrownScalding;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;

public class LavaThrownScaldingRenderer extends ThrownItemRenderer<LavaThrownScalding>{
	
	private static final ResourceLocation TEXTURE = new ResourceLocation("lavapotions:textures/entity/scalding_splash.png");

	public LavaThrownScaldingRenderer(EntityRendererProvider.Context manager) {
        super(manager);
    }
	
	public ResourceLocation getTextureLocation(LavaThrownScalding entity) {
	   return TEXTURE;
	}
}
