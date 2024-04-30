package com.projectmushroom.lavapotions.client;

import com.projectmushroom.lavapotions.LavaPotions;

import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LavaPotions.MOD_ID)
public class ReaperFalseDamageEvent extends Event
{
	@SubscribeEvent
	public static void onFalseExplosion(LivingAttackEvent event)
	{
		System.out.println(event.getSource().toString());
		if (event.getSource().toString().equals("DamageSource (dont)"))
		{
			event.setCanceled(true);
		}
	}
}
