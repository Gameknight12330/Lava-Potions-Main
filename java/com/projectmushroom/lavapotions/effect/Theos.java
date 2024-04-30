package com.projectmushroom.lavapotions.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class Theos extends MobEffect {

	public Theos(MobEffectCategory category, int amplifier) {
		super(category, amplifier);
	}
	
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		super.applyEffectTick(entity, amplifier);
	}
	
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}

}