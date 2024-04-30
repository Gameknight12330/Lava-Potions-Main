package com.projectmushroom.lavapotions.client.renderer;

import com.projectmushroom.lavapotions.entity.LavaThrownPotion;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;

public class LavaThrownPotionRenderer extends ThrownItemRenderer<LavaThrownPotion>{
	
	private static final ResourceLocation TEXTURE = new ResourceLocation("lavapotions:textures/entity/flame_healing_splash.png");

	public LavaThrownPotionRenderer(EntityRendererProvider.Context manager) {
        super(manager);
    }
	
	public ResourceLocation getTextureLocation(LavaThrownPotion entity) {
	   return TEXTURE;
	}
}
