package com.projectmushroom.lavapotions.client;

import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.effect.LavaEffects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LavaPotions.MOD_ID)
public class FieryRegenStartEvent extends Event
{
    @SubscribeEvent
    public void onEntityHit(LivingHurtEvent event)
     {
    	if (event.getEntityLiving().hasEffect(LavaEffects.FIERY_REGEN.get()))
    	{
    		if (event.getSource().getDirectEntity() instanceof LivingEntity)
    		{
    			event.getSource().getDirectEntity().hurt(DamageSource.GENERIC, (float) (event.getAmount() * 0.5));
    		}
    	}
    }
}