package com.projectmushroom.lavapotions.client;

import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.effect.LavaEffects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionExpiryEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LavaPotions.MOD_ID)
public class SoulFlameStartEvent extends Event
{
	int dmgcount = 0;
	int deathcount = 0;
	
	@SubscribeEvent
	public void onSoulFlameStart(PotionAddedEvent event)
	{
		if (event.getPotionEffect().getEffect().equals(LavaEffects.SOUL_FLAME.get()))
		{
			dmgcount = 0;
			deathcount = 0;
		}
	}
	
	@SubscribeEvent
	public void onSoulFlameEnd(PotionRemoveEvent event)
	{
		if (event.getPotionEffect().getEffect().equals(LavaEffects.SOUL_FLAME.get()))
		{
			dmgcount = 0;
			deathcount = 0;
		}
	}
	
	@SubscribeEvent
	public void onSoulFlameExpire(PotionExpiryEvent event)
	{
		if (event.getPotionEffect().getEffect().equals(LavaEffects.SOUL_FLAME.get()))
		{
			dmgcount = 0;
			deathcount = 0;
		}
	}
	
    @SubscribeEvent
    public void onSoulFlameActive(PlayerTickEvent event)
    {
    	if (event.player.hasEffect(LavaEffects.SOUL_FLAME.get()))
    	{
    		LivingEntity entity = event.player;
			dmgcount += 1;
			deathcount += 1;
			if (entity.getHealth() > 1) 
			{
				System.out.println("Entity health > 1");
				if (dmgcount >= 10)
				{
					entity.hurt(new DamageSource("Soul Flame"), 1);
					dmgcount = 0;
				}
			}
			if (deathcount >= 1200)
			{
				entity.hurt(new DamageSource("Soul Flame"), 1000000);
		        dmgcount = 0;
		        deathcount = 0;
			}
		}
	} 
}
