package com.projectmushroom.lavapotions.client;

import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.effect.LavaEffects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LavaPotions.MOD_ID)
public class HighwayStartEvent extends Event
{
	
	@SubscribeEvent
	public void onHighwayJump(LivingJumpEvent event)
	{
		LivingEntity entity = event.getEntityLiving();
		if (entity.hasEffect(LavaEffects.HIGHWAY.get()))
		{
			entity.setDeltaMovement(entity.getSpeed(), (entity.getSpeed() * 100), entity.getSpeed());
			if (entity.hasEffect(LavaEffects.INVINC.get()))
			{
				entity.removeEffect(LavaEffects.INVINC.get());
			}
		}
	}
	
	@SubscribeEvent
	public void onHighwayHitGround(LivingHurtEvent event)

	{

		if (event.getEntityLiving().hasEffect(LavaEffects.HIGHWAY.get()))

		{

			if (event.getAmount() >= 20)

			{

				if (event.getSource().equals(DamageSource.FALL))

				{

					LivingEntity entity = event.getEntityLiving();

					Level level = entity.getLevel();

					entity.removeEffect(LavaEffects.HIGHWAY.get());

					entity.hurt(new DamageSource("Highway"), 10000);

					makeExplosion(level, entity);

					event.setCanceled(true);

				}

			}

		}

	}

	

	public void makeExplosion (Level level, LivingEntity entity)

	{

	    for (int x = 0; x < 100; x += 1)

	    {

	    	for (int i = 0; i < 100; i += 1)

	    	{

	    		level.explode(entity, (entity.getX() + Math.random()*(2*i+1)-i), (entity.getY() 

	                + Math.random()*(2*i+1)-i), (entity.getZ() + Math.random()*(2*i+1)-i), 5, Explosion.BlockInteraction.DESTROY);

	            System.out.println("Explosion " + i);

	    	}

	    }

	}
}