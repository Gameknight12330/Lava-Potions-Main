package com.projectmushroom.lavapotions.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FieryRegen extends MobEffect {

	public FieryRegen(MobEffectCategory category, int amplifier) {
		super(category, amplifier);
	}
	
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity.getHealth() < entity.getMaxHealth()) 
		{
            entity.heal(0.5F);
          }
	}
	
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}

}