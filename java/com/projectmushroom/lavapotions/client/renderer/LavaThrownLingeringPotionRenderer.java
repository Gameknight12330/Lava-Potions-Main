package com.projectmushroom.lavapotions.client.renderer;

import com.projectmushroom.lavapotions.entity.LavaThrownLingeringPotion;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;

public class LavaThrownLingeringPotionRenderer extends ThrownItemRenderer<LavaThrownLingeringPotion>{
	
	private static final ResourceLocation TEXTURE = new ResourceLocation("lavapotions:textures/entity/flame_healing_lingering.png");

	public LavaThrownLingeringPotionRenderer(EntityRendererProvider.Context manager) {
        super(manager);
    }
	
	public ResourceLocation getTextureLocation(LavaThrownLingeringPotion entity) {
	   return TEXTURE;
	}
}
